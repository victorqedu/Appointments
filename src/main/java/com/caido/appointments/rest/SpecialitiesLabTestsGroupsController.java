package com.caido.appointments.rest;

import com.caido.appointments.entity.LabTestsGroups;
import com.caido.appointments.entity.SpecialitiesLabTestsGroups;
import com.caido.appointments.repositories.SpecialitiesLabTestsGroupsRepository;
import java.util.List;
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
public class SpecialitiesLabTestsGroupsController {
    private final SpecialitiesLabTestsGroupsRepository repository;
    private final SpecialitiesLabTestsGroupsModelAssembler assembler;
    
    public SpecialitiesLabTestsGroupsController(SpecialitiesLabTestsGroupsRepository repository, SpecialitiesLabTestsGroupsModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }
    
    @GetMapping("/specialitiesLabTestsGroups/{idSpeciality}")
    CollectionModel<EntityModel<LabTestsGroups>> findLabTestsGroupsBySpeciality(@PathVariable Integer idSpeciality) {
        List<EntityModel<LabTestsGroups>> c = repository.findLabTestsGroupsBySpeciality(idSpeciality).stream() 
            .map(assembler::toModel) 
            .collect(Collectors.toList());
        return CollectionModel.of(c, linkTo(methodOn(SpecialitiesLabTestsGroupsController.class).findLabTestsGroupsBySpeciality(idSpeciality)).withSelfRel());
    }
   
}
@Component
class SpecialitiesLabTestsGroupsModelAssembler implements RepresentationModelAssembler<LabTestsGroups, EntityModel<LabTestsGroups>> {
  @Override
  public EntityModel<LabTestsGroups> toModel(LabTestsGroups c) {
    return EntityModel.of(c, 
        linkTo(methodOn(SpecialitiesLabTestsGroupsController.class).findLabTestsGroupsBySpeciality(c.getId())).withSelfRel());
  }
}
