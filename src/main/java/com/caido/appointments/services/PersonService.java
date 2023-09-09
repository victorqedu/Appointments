package com.caido.appointments.services;

import com.caido.appointments.Util.Exceptions.RootExceptionHandler;
import static com.caido.appointments.Util.Functions.empty;
import com.caido.appointments.Util.JWT;
import com.caido.appointments.Util.MailService;
import com.caido.appointments.config.SecurityConstants;
import com.caido.appointments.entity.Config;
import com.caido.appointments.entity.Person;
import com.caido.appointments.repositories.ConfigRepository;
import com.caido.appointments.repositories.PersonRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Optional;
import javax.crypto.SecretKey;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PersonService {
    private PersonRepository personRepository;
    private AppointmentsService appointmentsService;
    private MailService mailService;
    private ConfigRepository configRepository;
    private final PasswordEncoder passwordEncoder;

    public PersonService(PersonRepository pr, PasswordEncoder passwordEncoder, AppointmentsService appointmentsService, MailService mailService, ConfigRepository configRepository) {
        this.personRepository = pr;
        this.passwordEncoder = passwordEncoder;
        this.appointmentsService = appointmentsService;
        this.mailService = mailService;
        this.configRepository = configRepository;
    }

    public PersonRepository getPersonRepository() {
        return personRepository;
    }

    public Person createAccount(Person person) throws Exception {
        System.out.println("Start createAccount for cnp "+person.getCnp()+" name "+person.getName());
        if(person.getId()!=null) {
            throw new RuntimeException("Nu puteti specifica un id de cont la crearea conturilor noi: '"+person.getId()+"'");
        }
        person.check();
        Person personFoundByEmail = personRepository.findByEmail(person.getAuthEmail());
        System.out.println("personfoundByEmail "+personFoundByEmail+" search email is "+person.getAuthEmail());
        if(personFoundByEmail!=null) {
            throw new RuntimeException("Contul cu emailul "+person.getAuthEmail()+" exista deja in baza de date, daca ati uitat parola accesati butonul 'Am uitat parola'");
        } else {
            String hashPwd = passwordEncoder.encode(person.getOnlinePasswordReal());
            person.setOnlinePassword(hashPwd);
            System.out.println("createAccount for cnp "+person.getCnp());
            if(empty(person.getCnp())) {
                System.out.println("createAccount empty cnp ");
                person.setCnp("0000000000000");
            }
            if(!person.getCnp().equals("0000000000000")) {
                Person personFoundByCnp = personRepository.findByCNP(person.getCnp());
                if(personFoundByCnp!=null) {
                    if(!empty(personFoundByCnp.getAuthEmail())) {
                        throw new RuntimeException("Contul cu cnp-ul "+person.getCnp()+" exista deja in baza de date, daca ati uitat parola accesati butonul 'Am uitat parola'");
                    } else {
                        personFoundByCnp.setAuthEmail(person.getAuthEmail());
                        personFoundByCnp.setOnlinePassword(person.getOnlinePasswordReal());
                        person = personFoundByCnp;
                    }
                }
            }
            SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));
            String jwt = Jwts.builder().setIssuer(SecurityConstants.JWT_SUBJECT).setSubject("JWT Token")
                    .claim("username", person.getAuthEmail())
                    .setIssuedAt(new Date())
                    .setExpiration(new Date((new Date()).getTime() + 1000*60*20))
                    .signWith(key).compact();
            person.setAuthEmailConfirmedLink(jwt);

            Person p = personRepository.save(person);
            sendEmailConfirmareEmail(person);
            return p;
            
        }
    }
    
    public Person confirmEmail(String jwtEmailToken) throws Exception {
        String exp = JWT.getClaimByNameFromToken(jwtEmailToken, "exp");
        Integer expInt = Integer.valueOf(exp);
        long nowTimestamp = System.currentTimeMillis() / 1000;
        System.out.println("expInt is "+expInt+" timestamp "+nowTimestamp);
        if(nowTimestamp>expInt) {
            throw new RuntimeException("Nu puteti confirma emailul folosind acest link intrucat linkul este expirat, linkurile de confirmare email sunt valabile timp de 20 de minute");
        }
        
        Person person = personRepository.findByauthEmailConfirmedLink(jwtEmailToken);
        if(person==null) {
            throw new RuntimeException("Link invalid, nu exista persoana cu acest link de confirmare email");
        }
        if(person.getAuthEmailConfirmed()==1) {
            return person;
        } else {
        person.setAuthEmailConfirmed(1);
        return personRepository.save(person);            
        }
    }

    public Person reSendEmailConfirmareEmail(Person person, String jwtConnectedUser) throws Exception {
        String idUserConectat = JWT.getClaimByNameFromToken(jwtConnectedUser, "id");
        if(!idUserConectat.equals(person.getId().toString())) {
            throw new RuntimeException("Tentativa de spargere cont a fost identificata, datele au fost inregistrate");
                    //+ ", persoane cu id "+idUserConectat+" nu poate modifica datele persoanei cu id "+person.getId());
        }
        Person pi = personRepository.findById(person.getId()).orElseThrow(() -> new RootExceptionHandler("Persoana cu id-ul "+person.getId()+" nu se gaseste in baza de date"));
        if (pi.getAuthEmailConfirmed()==1) {
            throw new RuntimeException("Emailul acestui cont este deja confirmat");
        }
        SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));
        String jwt = Jwts.builder().setIssuer(SecurityConstants.JWT_SUBJECT).setSubject("JWT Token")
                .claim("username", person.getAuthEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + 1000*60*20))
                .signWith(key).compact();
        pi.setAuthEmailConfirmedLink(jwt);
        sendEmailConfirmareEmail(pi);
        return personRepository.save(pi);
    }
    
    public Person sendEmailConfirmareEmail(Person person) throws Exception {
        String link = configRepository.getMailProgramariOnlineFrontend().getValoare()+"/confirmEmail/"+person.getAuthEmailConfirmedLink();
        System.out.println("LInk confirm is "+link);

        Config c;
        c = configRepository.getMailProgramariOnlineSubiectConfirmareCont();
        String subiect= c.getValoare();
        c = configRepository.getMailProgramariOnlineContinutConfirmareCont();
        String continut = c.getValoare()
                .replace("\n", "")
                .replace("\r", "")
                .replace("<NUME>", person.getName())
                .replace("<PRENUME>", person.getSurname())
                .replace("<LINK>", link);
        System.out.println("Continutul este acum "+continut);
        mailService.send(configRepository.getMailProgramariOnlineFrom().getValoare(), person.getAuthEmail(), subiect, continut);        
        return person;
    }

    @Transactional
    public Person updateAccount(Person person, String jwtToken) throws Exception  {
        System.out.println("start updteaccount: jwtToken = "+jwtToken);
        if(person.getId()==null) {
            throw new RuntimeException("La actualizarea conturilor trebuie sa mentionati id-ul contului actualizat");
        } else {
            String idUserConectat = JWT.getClaimByNameFromToken(jwtToken, "id");
            if(!idUserConectat.equals(person.getId().toString())) {
                throw new RuntimeException("Tentativa de spargere cont a fost identificata, datele au fost inregistrate");
                        //+ ", persoane cu id "+idUserConectat+" nu poate modifica datele persoanei cu id "+person.getId());
            } else {
                
                Person personFoundByEmail = personRepository.findByEmail(person.getAuthEmail(), Integer.valueOf(idUserConectat));
                System.out.println("personfoundByEmail "+personFoundByEmail+" search email is "+person.getAuthEmail());
                if(personFoundByEmail!=null) {
                    throw new RuntimeException("Contul cu emailul "+person.getAuthEmail()+" exista deja in baza de date, daca ati uitat parola accesati butonul 'Am uitat parola'");
                }
                    
                System.out.println("Encoding the real password: "+person.getOnlinePasswordReal());
                String hashPwd = passwordEncoder.encode(person.getOnlinePasswordReal());
                person.setOnlinePassword(hashPwd);
                person.check();
                if(empty(person.getCnp())) {
                    person.setCnp("0000000000000");
                }
                Person pi = personRepository.findById(person.getId()).orElseThrow(() -> new RootExceptionHandler("Persoana cu id-ul "+person.getId()+" nu se gaseste in baza de date"));
                if(!pi.getAuthEmail().equals(person.getAuthEmail())) {
                    person.setAuthEmailConfirmed(0);
                }
                if(pi.getCnp().equals("0000000000000") && !person.getCnp().equals("0000000000000")) { // the old CNP is anonymized and the new one is not anonymized
                    Person pc = personRepository.findByCNP(person.getCnp());
                    if(pc!=null) { // a person with this cnp is already in the database, I'm reusing this id
                        if(pc.getAuthEmail()!=null) {
                            throw new RuntimeException("Ati incercat sa schimbati CNP-ul contului. Noul CNP are deja un cont online creat, va rugam sa folositi credentialele contului existent.");
                        } else { // I found a person with that CNP in the database and this person has no online account
                            pc.setAuthEmail(person.getAuthEmail());
                            pc.setOnlinePassword(person.getOnlinePasswordReal());
                            pc.setPhone(person.getPhone());
                            person.setCnp("0000000000000");
                            person.setOnlinePassword(null);
                            person.setAuthEmail(null);
                            personRepository.save(pc);
                            appointmentsService.updateAppointments(pc.getId(), person.getId());
                            //person.setId(pc.getId());
                        }
                    } else { // a person with this CNP is not in the database, I will add a new record
                        // nothing to do here for the moment
                    }                    
                } else if(!pi.getCnp().equals("0000000000000") && !person.getCnp().equals("0000000000000") && !pi.getCnp().equals(person.getCnp())) {
                    throw new RuntimeException("CNP-ul nu poate fi schimbat");
                }
                System.out.println("Persoana de salvat "+person);
                return personRepository.save(person);                
            }
        }
    }

    public Person findByEmail(String email) {
        return personRepository.findByEmail(email);
    }

    public Optional<Person> findById(Integer id, String jwtConnectedUser) throws Exception{
        String idUserConectat = JWT.getClaimByNameFromToken(jwtConnectedUser, "id");
        if(!idUserConectat.equals(id+"")) {
            throw new RuntimeException("Tentativa de extragere date cu carcater personal a fost identificata, datele au fost inregistrate");
        }
        return personRepository.findById(id);
    }
}
