package com.caido.appointments.rest;

import com.caido.appointments.entity.ScrisoareMedicala;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.caido.appointments.Util.Exceptions.RootExceptionHandler;
import com.caido.appointments.config.SecurityConstants;
import com.caido.appointments.entity.DTO.ScrisoareMedicalaDTO;
import com.caido.appointments.services.ScrisoareMedicalaService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@RequestMapping("/api")
public class ScrisoareMedicalaController {
    private final ScrisoareMedicalaService service;
    private final ScrisoareMedicalaModelAssembler assembler;
    
    public ScrisoareMedicalaController(ScrisoareMedicalaService service, ScrisoareMedicalaModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }
    
    @GetMapping("/scrisoareMedicala/{id}")
    EntityModel<ScrisoareMedicalaDTO> get(@PathVariable Integer id, HttpServletRequest hsr) {
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException ex) {
//            Logger.getLogger(ScrisoareMedicalaController.class.getName()).log(Level.SEVERE, null, ex);
//        }
        ScrisoareMedicalaDTO c = service.get(id, hsr.getHeader(SecurityConstants.JWT_HEADER));
        if(c==null) {
            throw new RootExceptionHandler("Scrisoarea medicala cu id-ul "+id+" nu a fost gasita in baza de date");
        }
        return assembler.toModel(c);
    }

}

@Component
class ScrisoareMedicalaModelAssembler implements RepresentationModelAssembler<ScrisoareMedicalaDTO, EntityModel<ScrisoareMedicalaDTO>> {
  @Override
  public EntityModel<ScrisoareMedicalaDTO> toModel(ScrisoareMedicalaDTO c) {
    return EntityModel.of(c, 
        linkTo(methodOn(ScrisoareMedicalaController.class).get(c.getId(), null)).withSelfRel());
  }
}
