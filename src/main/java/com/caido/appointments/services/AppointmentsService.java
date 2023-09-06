package com.caido.appointments.services;

import com.caido.appointments.Util.LocalDateTimeInterval;
import com.caido.appointments.entity.Appointments;
import com.caido.appointments.entity.AppointmentsTypes;
import com.caido.appointments.entity.DTO.RequestAppointmentDTO;
import com.caido.appointments.entity.LabTestsGroups;
import com.caido.appointments.entity.Physicians;
import com.caido.appointments.entity.PhysiciansWorkingSchedule;
import com.caido.appointments.entity.Specialities;
import com.caido.appointments.repositories.AppointmentsRepository;
import com.caido.appointments.repositories.LabTestsGroupsRepository;
import com.caido.appointments.repositories.PhysiciansWorkingScheduleRepository;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import org.springframework.stereotype.Service;
import com.caido.appointments.Util.JWT;
import com.caido.appointments.entity.DTO.AppointmentDTO;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


@Service
public class AppointmentsService {
    AppointmentsRepository appointmentsRepository;
    PhysiciansWorkingScheduleRepository physiciansWorkingScheduleRepository;
    LabTestsGroupsRepository labTestsGroupsRepository;
    AppointmentsTypes at = new AppointmentsTypes();
    
    public AppointmentsService(AppointmentsRepository ar, PhysiciansWorkingScheduleRepository pwsr, LabTestsGroupsRepository ltgr) {
        this.appointmentsRepository = ar;
        this.physiciansWorkingScheduleRepository = pwsr;
        this.labTestsGroupsRepository = ltgr;
        at.setId(1); // hardcoded one appointment type as I don't use this field but is NOT NULL in the database
    }
    
    public AppointmentsRepository getAppointmentsRepository() {
        return appointmentsRepository;
    }
    
    public Appointments saveAppointment(Appointments appointment) {
        // 1. Check required fields 
        if(appointment.getIdAppointmentsTypes()==null) {
            // the type of the appointment is a field I no longer use, the types are deducted from the list of services(lab_tests_groups) if necessary
            AppointmentsTypes appointmentsTypes = new AppointmentsTypes();
            appointmentsTypes.setId(1);
            appointment.setIdAppointmentsTypes(appointmentsTypes);
        }
        if(appointment.getIdPerson()==null) {
            throw new RuntimeException("Persoana este obligatorie pentru salvarea programarii");
        }
        if(appointment.getIdDepartment()==null) {
            throw new RuntimeException("Sectia este obligatorie pentru salvarea programarii, asociati sectia cu o specialitate");
        }
        if(appointment.getIdPhysicians()==null) {
            throw new RuntimeException("Medicul este obligatoriu pentru salvarea programarii");
        } 
        if(appointment.getIdPersonnel()==null) {
            throw new RuntimeException("Persoana nagajata nu a fost identificata cu succes");
        } 
        if(appointment.getIdSpeciality()==null) {
            throw new RuntimeException("Specialitatea este obligatorie pentru salvarea programarii");
        }
        if(appointment.getLabTestsGroups().isEmpty()) {
            throw new RuntimeException("Programarea trebuie sa contina cel putin un serviciu");
        } else {
            appointment.setMinuteEstimate(0);
            appointment.getLabTestsGroups().forEach(ltg -> {
                appointment.setMinuteEstimate(appointment.getMinuteEstimate()+ltg.getMinuteEstimate());
            });
        }
        if(appointment.getOraProgramare()==null) {
            throw new RuntimeException("Data si ora programarii este obligatorie");
        }
        if(appointment.getIdCity()==null) {
            appointment.setIdCity(85535); // harcoded a city, I should use the location of the department
        }
        return appointmentsRepository.save(appointment);
    }

    public List<Appointments> getConnectedUserAppointments (String jwtToken, Integer limit, Integer offset) {
        String idUserConectat = com.caido.appointments.Util.JWT.getClaimByNameFromToken(jwtToken, "id");
        Pageable pageable = PageRequest.of(offset, limit);
        return appointmentsRepository.getConnectedUserAppointments(Integer.valueOf(idUserConectat), pageable);
    }
    
    public Integer countConnectedUserAppointments(String jwtToken) {
        Integer idUserConectat = Integer.valueOf(JWT.getClaimByNameFromToken(jwtToken, "id"));
        return appointmentsRepository.countConnectedUserAppointments(idUserConectat);        
    }
    public List<Appointments> calculateAppointments (RequestAppointmentDTO request) {
        LocalDate localDateStartIV = request.getLocalDateStartIV();
        LocalDate localDateStopIV = request.getLocalDateStopIV();
        Specialities speciality = request.getIdSpeciality();
        Physicians physician = request.getIdPhysician();

        DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("yyyy-M-d");
        DateTimeFormatter formatterDateTime = DateTimeFormatter.ofPattern("yyyy-M-d HH:mm:ss");
        ArrayList<LocalDateTimeInterval> workingIntersections = new ArrayList(); // the working program of the medic in the requested period (localDateStartIV to localDateStopIV)
        ArrayList<LocalDateTimeInterval> freeIntervals = new ArrayList(); // intervals available for anew appointments
        ArrayList<LocalDateTimeInterval> appointments = new ArrayList(); // patient appointments already existing in the database

        System.out.println("ZoneId.systemDefault() "+ZoneId.systemDefault());
        if(localDateStartIV==null) {
            throw new RuntimeException("Introduceti data de start a intervalului de verificare");
        }
        if(localDateStopIV==null) {
            throw new RuntimeException("Introduceti data de stop a intervalului de verificare");
        }
        localDateStopIV=localDateStopIV.plusDays(1);

        // <-- Start of part I - calculate intervals when the medic is working in the requested period(startIV - stopIV)
        workingIntersections.clear();
        List<PhysiciansWorkingSchedule> pwsl = physiciansWorkingScheduleRepository.findPhysicianWorkingSchedule(physician.getId(), speciality.getId(), localDateStartIV, localDateStopIV);
        for(PhysiciansWorkingSchedule pws:pwsl) {
            if(pws.getValidTo()==null) {
                pws.setValidTo(LocalDate.parse("3000-01-01"));
            }
            System.out.println(pws);
            LocalDate localDateSQLStart = pws.getValidFrom();
            LocalDate localDateSQLStop  = pws.getValidTo();

            for (LocalDate date = localDateStartIV; date.isBefore(localDateStopIV); date = date.plusDays(1)) {
                boolean fit = true; // checks if the current sql record is fitting the verification interval
                WeekFields weekFields = WeekFields.of(Locale.getDefault()); 
                int weekNumber = date.get(weekFields.weekOfWeekBasedYear());
                if(pws.getWeekType().getId()==1 && weekNumber%2!=0) { // 1 = even week
                    fit = false;
                } else if(pws.getWeekType().getId()==2 && weekNumber%2==0) { // 2 = odd week
                    fit = false;
                }
                if(fit) {
                    if(localDateSQLStart.isEqual(date) || localDateSQLStop.equals(date) || (localDateSQLStart.isBefore(date) && localDateSQLStop.isAfter(date))) { // current date is in the verification interval
                        //getDayOfWeek: Monday = 1 .... Sunday =7
                        Integer dayOfWeek = date.getDayOfWeek().getValue();
                        if(        (pws.getLuni()==1      && dayOfWeek==1) 
                                || (pws.getMarti()==1     && dayOfWeek==2) 
                                || (pws.getMiercuri()==1  && dayOfWeek==3)
                                || (pws.getJoi()==1       && dayOfWeek==4) 
                                || (pws.getVineri()==1    && dayOfWeek==5) 
                                || (pws.getSambata()==1   && dayOfWeek==6)
                                || (pws.getDuminica()==1  && dayOfWeek==7)
                            ) { // day of the week is the same
                            String formattedDate = date.format(formatterDate);
                            LocalDateTime startI = LocalDateTime.parse(formattedDate+" "+pws.getStart()+":00", formatterDateTime);
                            LocalDateTime stopI  = LocalDateTime.parse(formattedDate+" "+pws.getStop()+":00", formatterDateTime);
                            LocalDateTimeInterval ldti = new LocalDateTimeInterval(startI, stopI);
                            System.out.println("working schedule in interval "+ldti);
                            workingIntersections.add(ldti);
                        }
                    }
                }
            }
        }
        Collections.sort(workingIntersections, (LocalDateTimeInterval o1, LocalDateTimeInterval o2) -> o1.getStart().compareTo(o2.getStart()));
        // END of part I - calculate intervals when the medic is working in the requested period(startIV - stopIV) -->

        // <-- START of part II - calculate intervals when the medic is free within the working schedule
        freeIntervals.clear();
        appointments.clear();
        for(LocalDateTimeInterval ldti : workingIntersections) {
            System.err.println("[1] workingIntersections start "+ldti.getStart()+" stop "+ldti.getStop());
            freeIntervals.add(ldti.clone(ldti));
        }
        for(LocalDateTimeInterval ldti:freeIntervals) {
            System.err.println("[1] Free interval start "+ldti.getStart()+" stop "+ldti.getStop());
        }

        List<Appointments> al = appointmentsRepository.getPhysicianAppointments(physician.getId(), speciality.getId(), localDateStartIV,localDateStopIV);
        for(Appointments a:al) {
            String numePacient = a.getIdPerson().getName()+" "+a.getIdPerson().getSurname();
            //LocalDateTime appointmentStart = a.getOraProgramare().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            LocalDateTime appointmentStart = a.getOraProgramare();
            LocalDateTime appointmentStop = appointmentStart.plus(Duration.of(a.getMinuteEstimate(), ChronoUnit.MINUTES));

            LocalDateTimeInterval appointment = new LocalDateTimeInterval(appointmentStart, appointmentStop,numePacient);
            appointment.setDescription("Programare pentru pacientul "+numePacient+" de la ora "+appointmentStart+" până la ora "+appointmentStop);
            appointments.add(appointment);
            int size = freeIntervals.size();
            for(int i=0;i<size;i++) {
                LocalDateTimeInterval ldti = freeIntervals.get(i);
                if(ldti!=null) {
                    if(appointmentStart.isEqual(ldti.getStart()) && appointmentStop.isEqual(ldti.getStop())) {
                        //[S....E] APPOINTMENT
                        //[S....E] WORKING SCHEDULE
                        freeIntervals.set(i, null);
                    } else if(appointmentStart.isEqual(ldti.getStart()) && appointmentStop.isBefore(ldti.getStop())) {
                        //[S....E]      APPOINTMENT
                        //[S.........E] WORKING SCHEDULE
                        ldti.setStart(appointmentStop);
                        ldti.setDescription("Interval dispobnibil pentru programari de la ora "+ldti.getStart().toLocalTime()+" până la ora "+ldti.getStop().toLocalTime());
                        System.out.println("now interval liber "+ldti);
                        freeIntervals.set(i, ldti);
                    } else if(appointmentStop.isEqual(ldti.getStop()) && appointmentStart.isAfter(ldti.getStart())) {
                        //     [S....E] APPOINTMENT
                        //[S.........E] WORKING SCHEDULE
                        ldti.setStop(appointmentStart);
                        ldti.setDescription("Interval dispobnibil pentru programari de la ora "+ldti.getStart().toLocalTime()+" până la ora "+ldti.getStop().toLocalTime());
                        freeIntervals.set(i, ldti);                            
                        System.out.println("now interval liber "+ldti);
                    } else if(appointmentStop.isBefore(ldti.getStop()) && appointmentStart.isAfter(ldti.getStart())) {
                        //     [S....E]    APPOINTMENT
                        //[S............E] WORKING SCHEDULE
                        LocalDateTimeInterval ldtiNew = new LocalDateTimeInterval(appointmentStop, ldti.getStop());
                        ldti.setStop(appointmentStart);
                        freeIntervals.set(i, ldti);
                        System.out.println("now interval liber "+ldti);
                        ldti.setDescription("Interval dispobnibil pentru programari de la ora "+ldti.getStart().toLocalTime()+" până la ora "+ldti.getStop().toLocalTime());

                        ldtiNew.setDescription("Interval dispobnibil pentru programari de la ora "+ldtiNew.getStart().toLocalTime()+" până la ora "+ldtiNew.getStop().toLocalTime());
                        freeIntervals.add(ldtiNew);
                        System.out.println("now interval liber "+ldtiNew);
                        size++;
                    }
                }
            }
        }
        freeIntervals.removeAll(Collections.singleton(null));

        Collections.sort(freeIntervals, (LocalDateTimeInterval o1, LocalDateTimeInterval o2) -> o1.getStart().compareTo(o2.getStart()));
        System.err.println("Free intervals are: "+freeIntervals);
        Collections.sort(appointments, (LocalDateTimeInterval o1, LocalDateTimeInterval o2) -> o1.getStart().compareTo(o2.getStart()));
        // END of part II - calculate intervals when the medic is free within the working schedule -->

        // <-- START of part III - calculate next appointment
        List<Appointments> returnAppointments = new ArrayList();

        // Start calculating the number of minutes necessary using the selected services
        Integer necessaryMinutes = 0;

        Iterator<LabTestsGroups> iterator = request.getLabTestsGroups().iterator();

        while (iterator.hasNext()) {
            LabTestsGroups ltg = iterator.next();
            System.out.println("value= " + ltg);
            necessaryMinutes+=ltg.getMinuteEstimate();
        }
        //necessaryMinutes = request.getLabTestsGroups().stream().map(ltg -> ltg.getMinuteEstimate()).reduce(necessaryMinutes, Integer::sum);            

        for(LocalDateTimeInterval ldti:freeIntervals) {
            if(ldti!=null) {
                //System.err.println("Free interval start "+ldti.getStart()+" stop "+ldti.getStop()+" period "+period.toMinutes()+" minute necesare "+minute_necesare);
                while(ldti.getStart().isBefore(ldti.getStop())) {
                    Duration period = Duration.between(ldti.getStart(), ldti.getStop());
                    if(period.toMinutes() >= necessaryMinutes) {
                        Appointments a = new Appointments();
                        a.setIdAppointmentsTypes(at);
                        //a.setIdLabTestsGroups(ltg);
                        //a.setIdPersonnel(physician.getIdPersonnel());
                        //a.setIdPhysicians(physician);
                        //a.setIdSpeciality(speciality);
                        a.setMinuteEstimate(necessaryMinutes);
                        a.setOraProgramare(ldti.getStart());
                        returnAppointments.add(a);
                        System.err.println("New appointment with start at "+a.getOraProgramare());
                    }

                    ldti.setStart(ldti.getStart().plusMinutes(necessaryMinutes));
                }
            }
        }
        return returnAppointments;
        // STOP of part III - calculate next appointment -->
    }

}
