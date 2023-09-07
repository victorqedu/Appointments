package com.caido.appointments.rest;

import com.caido.appointments.entity.Appointments;
import com.caido.appointments.entity.DTO.RequestAppointmentDTO;
import com.caido.appointments.services.AppointmentsService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.caido.appointments.config.SecurityConstants;
import com.caido.appointments.entity.DTO.AppointmentDTO;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api")
public class AppointmentsController {
    private final AppointmentsService appointmentsService;
    private final AppointmentsModelAssembler assembler;
    
    public AppointmentsController(AppointmentsService appointmentsService, AppointmentsModelAssembler assembler) {
        this.appointmentsService = appointmentsService;
        this.assembler = assembler;
    }
    
    @PostMapping("/appointments")
    CollectionModel<EntityModel<Appointments>> calculateAppointments(@RequestBody RequestAppointmentDTO request) {
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException ex) {
//            Logger.getLogger(AppointmentsController.class.getName()).log(Level.SEVERE, null, ex);
//        }
        List<Appointments> appointlemntsList = appointmentsService.calculateAppointments(request);
        appointlemntsList.stream().map(a -> a).forEach(System.out::println);
        List<EntityModel<Appointments>> c = appointlemntsList.stream()
            .map(assembler::toModel) 
            .collect(Collectors.toList());
        System.out.println("c "+c);
        return CollectionModel.of(c, linkTo(methodOn(AppointmentsController.class).calculateAppointments(request)).withSelfRel());
    }
    
//    @GetMapping("/appointments/{id}")
//    EntityModel<Appointments> getOne(@PathVariable Integer id) {
//        System.out.println("Start getOne");
//        Appointments c = appointmentsService.getAppointmentsRepository().findById(id).orElseThrow(() -> new RootExceptionHandler("Programarea cu id-ul "+id+" nu a fost gasita in baza de date"));
//        return assembler.toModel(c);
//    }

    @GetMapping("/cancelAppointment/{idAppointment}")
    void cancelAppointment(@PathVariable Integer idAppointment, HttpServletRequest hsr) {
        appointmentsService.cancelAppointment(idAppointment, hsr.getHeader(SecurityConstants.JWT_HEADER));
    }

    @GetMapping("/appointments/{limit}/{offset}")
    List<Appointments> getConnectedUserAppointments(@PathVariable Integer limit, @PathVariable Integer offset, HttpServletRequest hsr) {
        List<Appointments> appointlemntsList = appointmentsService.getConnectedUserAppointments(hsr.getHeader(SecurityConstants.JWT_HEADER), limit, offset);
        return appointlemntsList;
    }

    @GetMapping("/countConnectedUserAppointments")
    Integer countConnectedUserAppointments(HttpServletRequest hsr){ 
        return appointmentsService.countConnectedUserAppointments(hsr.getHeader(SecurityConstants.JWT_HEADER));
    }
    
    @PostMapping(value = "/saveAppointment", consumes = "application/json", produces = "application/json")
    EntityModel<Appointments> saveAppointment(@RequestBody Appointments appointment, HttpServletRequest hsr) {
        Appointments a = appointmentsService.saveAppointment(appointment, hsr.getHeader(SecurityConstants.JWT_HEADER));
        return assembler.toModel(a);
    }
}

@Component
class AppointmentsModelAssembler implements RepresentationModelAssembler<Appointments, EntityModel<Appointments>> {
    
  @Override
  public EntityModel<Appointments> toModel(Appointments c) {
    return EntityModel.of(c, 
        //linkTo(methodOn(AppointmentsController.class).getOne(c.getId())).withSelfRel(),
        linkTo(methodOn(AppointmentsController.class).calculateAppointments(null)).withSelfRel(),
        linkTo(methodOn(AppointmentsController.class).saveAppointment(null, null)).withSelfRel()
    );
  }
}
