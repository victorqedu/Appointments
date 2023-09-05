package com.caido.appointments.rest;

import com.caido.appointments.config.SecurityConstants;
import com.caido.appointments.entity.Consultatii;
import com.caido.appointments.entity.DTO.ConsultatiiDTO;
import com.caido.appointments.entity.Person;
import com.caido.appointments.services.ConsultatiiService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;
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
public class ConsultatiiController {
    private final ConsultatiiService service;
    private final ConsultatiiModelAssembler assembler;

    public ConsultatiiController(ConsultatiiService service, ConsultatiiModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }
    @GetMapping("/consultatii/{idPerson}/{recordsPerPage}/{pageNumber}")
    CollectionModel<EntityModel<ConsultatiiDTO>> getPatientConsultations(
            @PathVariable("idPerson") Person idPerson, 
            @PathVariable("recordsPerPage") Integer recordsPerPage,
            @PathVariable("pageNumber") Integer pageNumber, HttpServletRequest hsr) {
        List<EntityModel<ConsultatiiDTO>> c = service.getPatientConsultations(idPerson, recordsPerPage, pageNumber, hsr.getHeader(SecurityConstants.JWT_HEADER)).stream() 
            .map(assembler::toModel) 
            .collect(Collectors.toList());
        return CollectionModel.of(c, linkTo(methodOn(ConsultatiiController.class)
                .getPatientConsultations(idPerson, recordsPerPage, pageNumber, hsr)).withSelfRel());
    }
    
    @GetMapping("/countPatientConsultations/{idPerson}")
    Integer countPatientConsultations(@PathVariable("idPerson") Person idPerson, HttpServletRequest hsr){ 
        return service.countPatientConsultations(idPerson, hsr.getHeader(SecurityConstants.JWT_HEADER));
    }
}

@Component
class ConsultatiiModelAssembler implements RepresentationModelAssembler<ConsultatiiDTO, EntityModel<ConsultatiiDTO>> {
  @Override
  public EntityModel<ConsultatiiDTO> toModel(ConsultatiiDTO c) {
    return EntityModel.of(c, 
        linkTo(methodOn(ConsultatiiController.class).getPatientConsultations(null, null, null, null)).withSelfRel()
    );
  }
}
