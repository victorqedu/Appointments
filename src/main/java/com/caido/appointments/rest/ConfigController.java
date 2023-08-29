package com.caido.appointments.rest;

import com.caido.appointments.entity.Config;
import com.caido.appointments.repositories.ConfigRepository;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class ConfigController {
    private final ConfigRepository repository;
    private final ConfigModelAssembler assembler;
    
    public ConfigController(ConfigRepository repository, ConfigModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/termsAndConditions")
    EntityModel<Config> getTermsAndConditions() {
        Config c = repository.getTermsAndConditions();
        return assembler.toModel(c);
    }

    @GetMapping("/policyOfConfidentiality")
    EntityModel<Config> getPolicyOfConfidentiality() {
        Config c = repository.getPolicyOfConfidentiality();
        return assembler.toModel(c);
    }

}

@Component
class ConfigModelAssembler implements RepresentationModelAssembler<Config, EntityModel<Config>> {
  @Override
  public EntityModel<Config> toModel(Config c) {
    return EntityModel.of(c, 
        linkTo(methodOn(ConfigController.class).getTermsAndConditions()).withSelfRel(),
        linkTo(methodOn(ConfigController.class).getPolicyOfConfidentiality()).withSelfRel());
  }
}
