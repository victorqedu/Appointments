package com.caido.appointments.rest;

import com.caido.appointments.Util.Exceptions.RootExceptionHandler;
import com.caido.appointments.entity.Specialities;
import com.caido.appointments.repositories.SpecialitiesRepository;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
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
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException ex) {
//            Logger.getLogger(SpecialitiesController.class.getName()).log(Level.SEVERE, null, ex);
//        }
        List<EntityModel<Specialities>> c = repository.findAll().stream() 
            .map(assembler::toModel) 
            .collect(Collectors.toList());
        return CollectionModel.of(c, linkTo(methodOn(SpecialitiesController.class).getAll()).withSelfRel());
    }
    
    @GetMapping("/specialities/{id}")
    EntityModel<Specialities> getOne(@PathVariable Integer id) {
        System.out.println("Start getOne");
        Specialities c = repository.findById(id).orElseThrow(() -> new RootExceptionHandler("Specialitatea cu id-ul "+id+" nu a fost gasita in baza de date"));
        return assembler.toModel(c);
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
