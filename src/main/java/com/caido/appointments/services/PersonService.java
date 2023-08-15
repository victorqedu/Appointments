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
        if(person.getId()!=null) {
            throw new RuntimeException("Nu puteti specifica un id de cont la crearea conturilor noi");
        } 

        if(personRepository.findByEmail(person.getEmail())!=null) {
            throw new RuntimeException("Contul cu emailul "+person.getEmail()+" exista deja in baza de date, daca ati uitat parola accesati butonul 'Am uitat parola'");
        } else {
            String hashPwd = passwordEncoder.encode(person.getOnlinePassword());
            person.setOnlinePassword(hashPwd);
            if(empty(person.getCnp())) {
                person.setCnp("0000000000000");
            }
            return personRepository.save(person);
        }
    }

    public Person updateAccount(Person person, String jwtToken) {
        if(person.getId()==null) {
            throw new RuntimeException("La actualizarea conturilor trebuie sa mentionati iod-ul contului actualizat");
        } else {
            String idUserConectat = JWT.getClaimByNameFromToken(jwtToken, "id");
            if(!idUserConectat.equals(person.getId().toString())) {
                throw new RuntimeException("Tentativa de spargere cont a fost identificata, datele au fost inregistrate, persoane cu id "+idUserConectat+" nu poate modifica datele persoanei cu id "+person.getId());
            } else {
                personRepository.findById(person.getId()).orElseThrow(() -> new RootExceptionHandler("Persoana cu id-ul "+person.getId()+" nu a fost gasita in baza de date"));
                Person pe = personRepository.findByEmail(person.getEmail());
                if(pe.getId().equals(person.getId())) {
                    throw new RuntimeException("Mai exista un cont asociat cu emailul "+person.getEmail()+" in baza de date, nu puteti avea mai multe conturi cu acelasi email, accesati 'Am uitat parola' pentru a recupera contul asociat cu acest email.");
                } else {
                    String hashPwd = passwordEncoder.encode(person.getOnlinePassword());
                    person.setOnlinePassword(hashPwd);
                    if(empty(person.getCnp())) {
                        person.setCnp("0000000000000");
                    }
                    return personRepository.save(person);                
                }
            }
        }
    }

    public Person findByEmail(String email) {
        return personRepository.findByEmail(email);
    }
}
