package com.caido.appointments.services;

import com.caido.appointments.Util.Exceptions.RootExceptionHandler;
import com.caido.appointments.Util.LocalDateTimeInterval;
import com.caido.appointments.entity.Appointments;
import com.caido.appointments.entity.AppointmentsTypes;
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
import java.util.List;
import java.util.Locale;
import org.springframework.stereotype.Service;

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
    
    public List<Appointments> calculateAppointments (
            LocalDate localDateStartIV, 
            LocalDate localDateStopIV,
            Specialities speciality, 
            Integer idLabTestsGroups, 
            Physicians physician) {
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
            LabTestsGroups ltg = this.labTestsGroupsRepository.findById(idLabTestsGroups).orElseThrow(() -> new RootExceptionHandler("Serviciul cu id-ul "+idLabTestsGroups+" nu a fost gasit in baza de date"));
            Integer necessaryMinutes = ltg.getMinuteEstimate();
            for(LocalDateTimeInterval ldti:freeIntervals) {
                if(ldti!=null) {
                    //System.err.println("Free interval start "+ldti.getStart()+" stop "+ldti.getStop()+" period "+period.toMinutes()+" minute necesare "+minute_necesare);
                    while(ldti.getStart().isBefore(ldti.getStop())) {
                        Duration period = Duration.between(ldti.getStart(), ldti.getStop());
                        if(period.toMinutes() >= necessaryMinutes) {
                            Appointments a = new Appointments();
                            a.setIdAppointmentsTypes(at);
                            a.setIdLabTestsGroups(ltg);
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
