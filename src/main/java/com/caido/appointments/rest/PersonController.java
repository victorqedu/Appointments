package com.caido.appointments.rest;

import com.caido.appointments.Util.Exceptions.RootExceptionHandler;
import com.caido.appointments.Util.JWT;
import com.caido.appointments.Util.MailService;
import com.caido.appointments.config.SecurityConstants;
import com.caido.appointments.entity.Person;
import com.caido.appointments.services.PersonService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    EntityModel<Person> register(@RequestBody Person person) throws Exception {
//        Thread.sleep(5000);
        System.out.println("Register person "+person);
        Person p = personService.createAccount(person);
        p.setOnlinePassword(null);
        return assembler.toModel(p);
    }

    @PutMapping(value = "/updateAccount", consumes = "application/json", produces = "application/json")
    EntityModel<Person> updateAccount(@RequestBody Person person, HttpServletRequest hsr) throws Exception  {
//        Thread.sleep(5000);
        Person p = personService.updateAccount(person, hsr.getHeader(SecurityConstants.JWT_HEADER));
        p.setOnlinePassword(null);
        return assembler.toModel(p);
    }

    @GetMapping(value = "/login", consumes = "*/*", produces = "application/json")
    public EntityModel<Person> login(HttpServletRequest request, HttpServletResponse response) throws Exception  {
        String jwt = response.getHeader("Authorization");
        if (null != jwt && !jwt.startsWith("Basic")) {
            Integer personId = Integer.valueOf(JWT.getClaimByNameFromToken(jwt, "id"));
            Person p = personService.findById(personId, jwt).orElseThrow(() -> new RootExceptionHandler("Persoana cu id-ul "+personId+" nu a fost gasită in baza de date"));
            p.setOnlinePassword(null);
            return assembler.toModel(p);
        } else {
          throw new RootExceptionHandler("Nu puteti accesa aceasta resursa, autentificare invalida");
        }        
    }
    
    @PostMapping(value = "/reSendEmailConfirmEmail", consumes = "application/json", produces = "application/json")
    public EntityModel<Person> reSendEmailConfirmEmail(@RequestBody Person person, HttpServletRequest hsr) throws Exception {
        Thread.sleep(5000);
        Person p = personService.reSendEmailConfirmareEmail(person, hsr.getHeader(SecurityConstants.JWT_HEADER));
        p.setOnlinePassword(null);
        return assembler.toModel(p);        
    }

    @GetMapping(value = "/confirmEmail/{jwtEmailToken}", consumes = "*/*", produces = "application/json")
    public EntityModel<Person> confirmEmail(@PathVariable("jwtEmailToken") String jwtEmailToken) throws Exception {
        Person p = personService.confirmEmail(jwtEmailToken);
        p.setOnlinePassword(null);
        return assembler.toModel(p);        
    }

    @GetMapping(value = "/findById/{id}", consumes = "*/*", produces = "application/json")
    public EntityModel<Person> findById(@PathVariable("id") Integer id, HttpServletRequest hsr ) throws Exception {
        Person p = personService.findById(id, hsr.getHeader(SecurityConstants.JWT_HEADER)).orElseThrow(() -> new RootExceptionHandler("Persoana cu id-ul "+id+" nu a fost gasită in baza de date"));;
        p.setOnlinePassword(null);
        return assembler.toModel(p);        
    }

}

@Component
class PersonModelAssembler implements RepresentationModelAssembler<Person, EntityModel<Person>> {
  @Override
  public EntityModel<Person> toModel(Person c) {
      try {
          return EntityModel.of(c,
                  linkTo(methodOn(PersonController.class).register(c)).withSelfRel()
          );
      } catch (Exception ex) {
          Logger.getLogger(PersonModelAssembler.class.getName()).log(Level.SEVERE, null, ex);
      }
      return null;
  }
}
