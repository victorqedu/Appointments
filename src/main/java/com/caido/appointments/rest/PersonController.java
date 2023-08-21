package com.caido.appointments.rest;

import com.caido.appointments.config.SecurityConstants;
import com.caido.appointments.entity.Person;
import com.caido.appointments.services.PersonService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api")
public class PersonController implements AuthenticationProvider  {
    private final PersonService personService;
    private final PersonModelAssembler assembler;
    private final PasswordEncoder passwordEncoder;

    public PersonController(PersonService service, PersonModelAssembler assembler, PasswordEncoder passwordEncoder) {
        this.personService = service;
        this.assembler = assembler;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping(value = "/register", consumes = "application/json", produces = "application/json")
    EntityModel<Person> register(@RequestBody Person person) {
        System.out.println("Register person "+person);
        Person p = personService.createAccount(person);
        return assembler.toModel(p);            
    }

    @PutMapping(value = "/updateAccount", consumes = "application/json", produces = "application/json")
    EntityModel<Person> updateAccount(@RequestBody Person person, HttpServletRequest hsr) {
        Person p = personService.updateAccount(person, hsr.getHeader(SecurityConstants.JWT_HEADER));
        return assembler.toModel(p);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println("Start authenticate");
        String username = authentication.getName();
        String pwd = authentication.getCredentials().toString();
        Person person = personService.findByEmail(username);
        if (person != null) {
            if (passwordEncoder.matches(pwd, person.getOnlinePassword())) {
                return new UsernamePasswordAuthenticationToken(username, pwd, getGrantedAuthorities());
            } else {
                throw new BadCredentialsException("Invalid password!");
            }
        } else {
            throw new BadCredentialsException("Utilizatorul cu emailul "+username+" nu exista.");
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

@Component
class PersonModelAssembler implements RepresentationModelAssembler<Person, EntityModel<Person>> {
  @Override
  public EntityModel<Person> toModel(Person c) {
    return EntityModel.of(c, 
        linkTo(methodOn(PersonController.class).register(c)).withSelfRel()
    );
  }
}
