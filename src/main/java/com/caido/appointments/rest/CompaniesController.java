package com.caido.appointments.rest;

import com.caido.appointments.entity.Companies;
import com.caido.appointments.repositories.CompaniesRepository;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CompaniesController {
    private final CompaniesRepository repository;
    private final CompaniesModelAssembler assembler;
    
    public CompaniesController(CompaniesRepository repository, CompaniesModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/localcompany")
    EntityModel<Companies> getLocalCompany() {
        Companies c = repository.getLocalOrgUnit();
        return assembler.toModel(c);
    }

}

@Component
class CompaniesModelAssembler implements RepresentationModelAssembler<Companies, EntityModel<Companies>> {
  @Override
  public EntityModel<Companies> toModel(Companies c) {
    return EntityModel.of(c, 
        linkTo(methodOn(CompaniesController.class).getLocalCompany()).withSelfRel());
  }
}
