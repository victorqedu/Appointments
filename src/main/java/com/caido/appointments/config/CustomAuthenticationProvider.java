package com.caido.appointments.config;

import com.caido.appointments.entity.Person;
import com.caido.appointments.Util.Exceptions.InvalidCredentialsException;
import com.caido.appointments.services.PersonService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * I no longer use this class, I needed it to implement custom error messages over Basic Auth when I got error 401 but I used instead a custom filter before authentication, I also don't think this is possible using this approach
 * Class could be deleted but I keep it to remind myself what I tried
 */
//@Component
public class CustomAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
    private final PersonService personService;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public CustomAuthenticationProvider(UserDetailsService userDetailsService, PersonService personService, org.springframework.security.crypto.password.PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.personService = personService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * This function only executes when the username and password are validated by the authorize function, if the user is already authorized this function will not run.
     * @param userDetails
     * @param authentication
     * @throws AuthenticationException 
     */
    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails,UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        System.out.println("Start additionalAuthenticationChecks");
        String username = authentication.getName();
        String pwd = authentication.getCredentials().toString();
        
        Person person = personService.findByEmail(username);
        if (person != null) {
            if (passwordEncoder.matches(pwd, person.getOnlinePassword())) {
                System.out.println("additionalAuthenticationChecks: Authenticated successfully");
            } else {
                System.out.println("additionalAuthenticationChecks: Invalid password");
                throw new InvalidCredentialsException("Parola invalida!");
            }
        } else {
            System.out.println("Utilizatorul cu emailul "+username+" nu exista.");
            throw new BadCredentialsException("additionalAuthenticationChecks: Utilizatorul cu emailul "+username+" nu exista.");
        }

    }

    @Override
    protected UserDetails retrieveUser(String username,
                                        UsernamePasswordAuthenticationToken authentication)
            throws AuthenticationException {
        return userDetailsService.loadUserByUsername(username);
    }

}
