package com.caido.appointments.rest;

import com.caido.appointments.Util.Exceptions.RootExceptionHandler;
import com.caido.appointments.entity.Appointments;
import com.caido.appointments.entity.Physicians;
import com.caido.appointments.entity.Specialities;
import com.caido.appointments.repositories.PhysiciansRepository;
import com.caido.appointments.repositories.SpecialitiesRepository;
import com.caido.appointments.services.AppointmentsService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
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
public class AppointmentsController {
    private final AppointmentsService appointmentsService;
    private final AppointmentsModelAssembler assembler;
    private final SpecialitiesRepository specialitiesRepository;
    private final PhysiciansRepository physiciansRepository;
    

    public AppointmentsController(AppointmentsService appointmentsService, AppointmentsModelAssembler assembler, SpecialitiesRepository specialitiesRepository, PhysiciansRepository physiciansRepository) {
        this.appointmentsService = appointmentsService;
        this.assembler = assembler;
        this.specialitiesRepository = specialitiesRepository;
        this.physiciansRepository = physiciansRepository;
    }

    @GetMapping("/appointments/{localDateStartIV}/{localDateStopIV}/{idSpeciality}/{idLabTestsGroups}/{idPhysician}")
    CollectionModel<EntityModel<Appointments>> calculateAppointments(
            @PathVariable("localDateStartIV") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate localDateStartIV, 
            @PathVariable("localDateStopIV") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate localDateStopIV, 
            @PathVariable("idSpeciality") Integer idSpeciality,
            @PathVariable("idLabTestsGroups") Integer idLabTestsGroups,
            @PathVariable("idPhysician") Integer idPhysician
            ) {
        Specialities s = new Specialities();
        s.setId(idSpeciality);
        Specialities speciality = specialitiesRepository.findById(idSpeciality).orElseThrow(() -> new RootExceptionHandler("Specialitatea cu id-ul "+idSpeciality+" nu a fost gasita in baza de date"));
        Physicians physician = physiciansRepository.findById(idPhysician).orElseThrow(() -> new RootExceptionHandler("Medicul cu id-ul "+idPhysician+" nu a fost gasit in baza de date"));
        LocalDate start = localDateStartIV;
        LocalDate stop = localDateStopIV;
        List<Appointments> appointlemntsList = appointmentsService.calculateAppointments(start, stop, speciality, idLabTestsGroups, physician);
        appointlemntsList.stream().map(a -> a).forEach(System.out::println);
        List<EntityModel<Appointments>> c = appointlemntsList.stream()
            .map(assembler::toModel) 
            .collect(Collectors.toList());
        System.out.println("c "+c);
        return CollectionModel.of(c, linkTo(methodOn(AppointmentsController.class).calculateAppointments(localDateStartIV, localDateStopIV, idSpeciality, idLabTestsGroups, idPhysician)).withSelfRel());
    }

    @GetMapping("/appointments/{id}")
    EntityModel<Appointments> getOne(@PathVariable Integer id) {
        System.out.println("Start getOne");
        Appointments c = appointmentsService.getAppointmentsRepository().findById(id).orElseThrow(() -> new RootExceptionHandler("Programarea cu id-ul "+id+" nu a fost gasita in baza de date"));
        return assembler.toModel(c);
    }
}

@Component
class AppointmentsModelAssembler implements RepresentationModelAssembler<Appointments, EntityModel<Appointments>> {
    
  @Override
  public EntityModel<Appointments> toModel(Appointments c) {
    return EntityModel.of(c, 
        linkTo(methodOn(AppointmentsController.class).getOne(c.getId())).withSelfRel()
    );
  }
}
