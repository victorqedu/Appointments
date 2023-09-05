package com.caido.appointments.config;

import com.caido.appointments.Util.Exceptions.InvalidCredentialsException;
import com.caido.appointments.entity.Person;
import com.caido.appointments.services.PersonService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CaidoAuthenticationProvider implements AuthenticationProvider  {
    private final PersonService personService;
    private final PasswordEncoder passwordEncoder;
    
    public CaidoAuthenticationProvider(PersonService service, PasswordEncoder passwordEncoder) {
        this.personService = service;
        this.passwordEncoder = passwordEncoder;
    }
    
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println("Start authenticate");
        String username = authentication.getName();
        String pwd = authentication.getCredentials().toString();
        System.out.println("Start authenticate username = "+username+" pwd "+pwd);
        Person person = personService.findByEmail(username);
        if (passwordEncoder.matches(pwd, person.getOnlinePasswordReal())) {
            System.out.println("Authenticated successfully");
            return new UsernamePasswordAuthenticationToken(username, pwd, getGrantedAuthorities());
        } else {
            System.out.println("Invalid password");
            throw new InvalidCredentialsException("Parola invalida!");
        }
    }
    
    private List<GrantedAuthority> getGrantedAuthorities() {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("GENERIC"));
        return grantedAuthorities;
    }
    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }

}
