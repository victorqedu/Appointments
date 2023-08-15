package com.caido.appointments.rest;

import com.caido.appointments.config.SecurityConstants;
import com.caido.appointments.entity.Person;
import com.caido.appointments.services.PersonService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
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
    private final PersonService service;
    private final PersonModelAssembler assembler;
    private final PasswordEncoder passwordEncoder;

    public PersonController(PersonService service, PersonModelAssembler assembler, PasswordEncoder passwordEncoder) {
        this.service = service;
        this.assembler = assembler;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping(value = "/register", consumes = "application/json", produces = "application/json")
    EntityModel<Person> register(@RequestBody Person person) {
        Person p = service.createAccount(person);
        return assembler.toModel(p);
    }

    @PutMapping(value = "/update", consumes = "application/json", produces = "application/json")
    EntityModel<Person> update(@RequestBody Person person, HttpServletRequest hsr) {
        Person p = service.updateAccount(person, hsr.getHeader(SecurityConstants.JWT_HEADER));
        return assembler.toModel(p);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String pwd = authentication.getCredentials().toString();
        Person person = service.findByEmail(username);
        if (person != null) {
            if (passwordEncoder.matches(pwd, person.getOnlinePassword())) {
                return new UsernamePasswordAuthenticationToken(username, pwd, null);
            } else {
                throw new BadCredentialsException("Invalid password!");
            }
        } else {
            throw new BadCredentialsException("Utilizatorul cu emailul "+username+" nu exista.");
        }
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
