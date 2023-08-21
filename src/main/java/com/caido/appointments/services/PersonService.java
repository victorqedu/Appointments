package com.caido.appointments.services;

import com.caido.appointments.Util.Exceptions.RootExceptionHandler;
import static com.caido.appointments.Util.Functions.empty;
import com.caido.appointments.Util.JWT;
import com.caido.appointments.entity.Person;
import com.caido.appointments.repositories.PersonRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PersonService {
    PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;

    public PersonService(PersonRepository pr, PasswordEncoder passwordEncoder) {
        this.personRepository = pr;
        this.passwordEncoder = passwordEncoder;
    }

    public PersonRepository getPersonRepository() {
        return personRepository;
    }

    public Person createAccount(Person person) {
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
            String hashPwd = passwordEncoder.encode(person.getOnlinePassword());
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
                        personFoundByCnp.setOnlinePassword(person.getOnlinePassword());
                        person = personFoundByCnp;
                    }
                }
            }
            return personRepository.save(person);
        }
    }

    public Person updateAccount(Person person, String jwtToken) {
        if(person.getId()==null) {
            throw new RuntimeException("La actualizarea conturilor trebuie sa mentionati id-ul contului actualizat");
        } else {
            String idUserConectat = JWT.getClaimByNameFromToken(jwtToken, "id");
            if(!idUserConectat.equals(person.getId().toString())) {
                throw new RuntimeException("Tentativa de spargere cont a fost identificata, datele au fost inregistrate");
                        //+ ", persoane cu id "+idUserConectat+" nu poate modifica datele persoanei cu id "+person.getId());
            } else {
                String hashPwd = passwordEncoder.encode(person.getOnlinePassword());
                person.setOnlinePassword(hashPwd);
                person.check();
                if(empty(person.getCnp())) {
                    person.setCnp("0000000000000");
                }
                Person pi = personRepository.findById(person.getId()).orElseThrow(() -> new RootExceptionHandler("Persoana cu id-ul "+person.getId()+" nu se gaseste in baza de date"));
                if(pi.getCnp().equals("0000000000000") && !person.getCnp().equals("0000000000000")) { // the old CNP is anonymized and the new one is not anonymized
                    Person pc = personRepository.findByCNP(person.getCnp());
                    if(pc!=null) { // a person with this cnp is already in the database, I'm reusing this id
                        if(pc.getAuthEmail()!=null) {
                            throw new RuntimeException("Ati incercat sa schimbati CNP-ul contului. Noul CNP are deja un cont online creat, va rugam sa folositi credentialele contului existent.");
                        } else {
                            person.setId(pc.getId());
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
}
