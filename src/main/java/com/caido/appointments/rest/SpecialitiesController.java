package com.caido.appointments.rest;

import com.caido.appointments.entity.Specialities;
import com.caido.appointments.repositories.SpecialitiesRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SpecialitiesController {
    private final SpecialitiesRepository repository;
    private final SpecialitiesModelAssembler assembler;
    
    public SpecialitiesController(SpecialitiesRepository repository, SpecialitiesModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/specialities")
    CollectionModel<EntityModel<Specialities>> getAll() {
        List<EntityModel<Specialities>> c = repository.findAll().stream() 
            .map(assembler::toModel) 
            .collect(Collectors.toList());
        return CollectionModel.of(c, linkTo(methodOn(SpecialitiesController.class).getAll()).withSelfRel());
    }
    
    @GetMapping("/specialities/{id}")
    EntityModel<Specialities> getOne(@PathVariable Integer id) {
        System.out.println("Start getOne");
        Specialities c = repository.findById(id).orElseThrow(() -> new SpecialitiesNotFoundException(id));
        return assembler.toModel(c);
    }

}

class SpecialitiesNotFoundException extends RuntimeException {
    SpecialitiesNotFoundException(Integer id) {
        super("Could not find speciality with id " + id);
    }
}

@ControllerAdvice
class SpecialitiesNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(SpecialitiesNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String specialitiesNotFoundHandler(SpecialitiesNotFoundException ex) {
        return ex.getMessage();
    }
}

@Component
class SpecialitiesModelAssembler implements RepresentationModelAssembler<Specialities, EntityModel<Specialities>> {
  @Override
  public EntityModel<Specialities> toModel(Specialities c) {
    return EntityModel.of(c, 
        linkTo(methodOn(SpecialitiesController.class).getOne(c.getId())).withSelfRel(),
        linkTo(methodOn(SpecialitiesController.class).getAll()).withRel("specialities"));
  }
}
