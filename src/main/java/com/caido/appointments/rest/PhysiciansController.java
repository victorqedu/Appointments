package com.caido.appointments.rest;

import com.caido.appointments.entity.DTO.SimplePhysicianDTO;
import com.caido.appointments.entity.Specialities;
import com.caido.appointments.repositories.PhysiciansRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
    CollectionModel<EntityModel<SimplePhysicianDTO>> getAllByDateAndSpeciality(@PathVariable("idSpeciality") Integer idSpeciality, @PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException ex) {
//            Logger.getLogger(SpecialitiesLabTestsGroupsController.class.getName()).log(Level.SEVERE, null, ex);
//        }
        Specialities s = new Specialities();
        s.setId(idSpeciality);
        List<EntityModel<SimplePhysicianDTO>> c = repository.getAllByDateAndSpeciality(idSpeciality, date).stream() 
            .map(assembler::toModel) 
            .collect(Collectors.toList());
        return CollectionModel.of(c, linkTo(methodOn(PhysiciansController.class).getAllByDateAndSpeciality(idSpeciality, date)).withSelfRel());
    }
    
//    @GetMapping("/physicians/{id}")
//    EntityModel<SimplePhysicianDTO> getOne(@PathVariable Integer id) {
//        System.out.println("Start getOne");
//        Physicians c = repository.findById(id).orElseThrow(() -> new RootExceptionHandler("Medicul cu id-ul "+id+" nu a fost gasit in baza de date"));
//        return assembler.toModel(c);
//    }
}

@Component
class PhysiciansModelAssembler implements RepresentationModelAssembler<SimplePhysicianDTO, EntityModel<SimplePhysicianDTO>> {
  @Override
  public EntityModel<SimplePhysicianDTO> toModel(SimplePhysicianDTO c) {
    return EntityModel.of(c, 
        linkTo(methodOn(PhysiciansController.class).getAllByDateAndSpeciality(null, null)).withSelfRel()
    );
  }
}
