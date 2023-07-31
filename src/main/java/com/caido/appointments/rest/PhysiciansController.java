package com.caido.appointments.rest;

import com.caido.appointments.entity.Physicians;
import com.caido.appointments.entity.Specialities;
import com.caido.appointments.repositories.PhysiciansRepository;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
@RequestMapping("/api")
public class PhysiciansController {
    private final PhysiciansRepository repository;
    private final PhysiciansModelAssembler assembler;

    public PhysiciansController(PhysiciansRepository repository, PhysiciansModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }
    @GetMapping("/physicians/{idSpeciality}/{date}")
    CollectionModel<EntityModel<Physicians>> getAllByDateAndSpeciality(@PathVariable("idSpeciality") Integer idSpeciality, @PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date) {
        Specialities s = new Specialities();
        s.setId(idSpeciality);
        List<EntityModel<Physicians>> c = repository.getAllByDateAndSpeciality(idSpeciality, date).stream() 
            .map(assembler::toModel) 
            .collect(Collectors.toList());
        return CollectionModel.of(c, linkTo(methodOn(PhysiciansController.class).getAllByDateAndSpeciality(idSpeciality, date)).withSelfRel());
    }
    
    @GetMapping("/physicians/{id}")
    EntityModel<Physicians> getOne(@PathVariable Integer id) {
        System.out.println("Start getOne");
        Physicians c = repository.findById(id).orElseThrow(() -> new PhysiciansNotFoundException(id));
        return assembler.toModel(c);
    }

}

class PhysiciansNotFoundException extends RuntimeException {
    PhysiciansNotFoundException(Integer id) {
        super("Could not find physicians with id " + id);
    }
}

@ControllerAdvice
class PhysiciansNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(PhysiciansNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String physiciansNotFoundHandler(PhysiciansNotFoundException ex) {
        return ex.getMessage();
    }
}

@Component
class PhysiciansModelAssembler implements RepresentationModelAssembler<Physicians, EntityModel<Physicians>> {
  @Override
  public EntityModel<Physicians> toModel(Physicians c) {
    return EntityModel.of(c, 
        linkTo(methodOn(PhysiciansController.class).getOne(c.getId())).withSelfRel()
    );
  }
}
