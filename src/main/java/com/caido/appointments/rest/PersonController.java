package com.caido.appointments.rest;

import com.caido.appointments.Util.Exceptions.RootExceptionHandler;
import com.caido.appointments.Util.JWT;
import com.caido.appointments.config.SecurityConstants;
import com.caido.appointments.entity.Person;
import com.caido.appointments.services.PersonService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api")
public class PersonController {
    private final PersonService personService;
    private final PersonModelAssembler assembler;

    public PersonController(PersonService service, PersonModelAssembler assembler) {
        this.personService = service;
        this.assembler = assembler;
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

    @GetMapping(value = "/login", consumes = "*/*", produces = "application/json")
    public EntityModel<Person> login(HttpServletRequest request, HttpServletResponse response) {
        String jwt = response.getHeader("Authorization");
        if (null != jwt && !jwt.startsWith("Basic")) {
            Integer personId = Integer.valueOf(JWT.getClaimByNameFromToken(jwt, "id"));
            Person p = personService.findById(personId).orElseThrow(() -> new RootExceptionHandler("Persoana cu id-ul "+personId+" nu a fost gasită in baza de date"));
            p.setOnlinePassword(null);
            return assembler.toModel(p);
        } else {
          throw new RootExceptionHandler("Nu puteti accesa aceasta resursa, autentificare invalida");
        }
        
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
