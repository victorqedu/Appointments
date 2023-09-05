package com.caido.appointments.config;

import com.caido.appointments.entity.Person;
import com.caido.appointments.services.PersonService;
import java.util.ArrayList;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * I no longer use this class, I needed it to implement custom error messages over Basic Auth when I got error 401 but I used instead a custom filter before authentication, I also don't think this is possible using this approach
 * Class could be deleted but I keep it to remind myself what I tried
 */
//@Service
public class UserService implements UserDetailsService {

    public UserService(com.caido.appointments.services.PersonService personService) {
        this.personService = personService;
    }
    private final PersonService personService;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Start loadUserByUsername");
        Person p = personService.findByEmail(username);

        if (p!=null){
            System.out.println("loadUserByUsername, found the account");
            return new User(username, p.getOnlinePasswordReal(), new ArrayList<>());
        } else{
            System.out.println("loadUserByUsername, account is not in the DB: %username");
            throw new UsernameNotFoundException(String.format("Utilizatorul [%username] nu a fost gasit"));
        }
    }
}
