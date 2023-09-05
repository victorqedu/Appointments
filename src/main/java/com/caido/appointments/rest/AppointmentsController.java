package com.caido.appointments.rest;

import com.caido.appointments.Util.Exceptions.RootExceptionHandler;
import com.caido.appointments.entity.Appointments;
import com.caido.appointments.entity.DTO.RequestAppointmentDTO;
import com.caido.appointments.services.AppointmentsService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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
    @GetMapping("/appointments/{id}")
    EntityModel<Appointments> getOne(@PathVariable Integer id) {
        System.out.println("Start getOne");
        Appointments c = appointmentsService.getAppointmentsRepository().findById(id).orElseThrow(() -> new RootExceptionHandler("Programarea cu id-ul "+id+" nu a fost gasita in baza de date"));
        return assembler.toModel(c);
    }

    @PostMapping(value = "/saveAppointment", consumes = "application/json", produces = "application/json")
    EntityModel<Appointments> saveAppointment(@RequestBody Appointments appointment) {
        Appointments a = appointmentsService.saveAppointment(appointment);
        return assembler.toModel(a);
    }
}

@Component
class AppointmentsModelAssembler implements RepresentationModelAssembler<Appointments, EntityModel<Appointments>> {
    
  @Override
  public EntityModel<Appointments> toModel(Appointments c) {
    return EntityModel.of(c, 
        linkTo(methodOn(AppointmentsController.class).getOne(c.getId())).withSelfRel(),
        linkTo(methodOn(AppointmentsController.class).calculateAppointments(null)).withSelfRel(),
        linkTo(methodOn(AppointmentsController.class).saveAppointment(null)).withSelfRel()
    );
  }
}
