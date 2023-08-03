package com.caido.appointments.repositories;

import com.caido.appointments.entity.Appointments;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AppointmentsRepository extends JpaRepository<Appointments, Integer> {
    /**
     * 
     * @param idPhysician
     * @param idSpeciality
     * @param startIVS - start date for the interval where we will check if the physician has a working schedule
     * @param stopIVS - stop date for the interval where we will check if the physician has a working schedule
     * @return a list with all the appointments already existent in the database for the @idPhysician and the @idSpeciality
     */
    @Query("select a " 
        + "from Appointments a "
        + "join Person p on (p.id = a.idPerson.id) " 
        + "where a.idPhysicians.id = ?1 and a.idSpeciality.id = ?2 AND DATE(a.oraProgramare) >= ?3 AND DATE(a.oraProgramare) <= ?4")
    List<Appointments> getPhysicianAppointments(Integer idPhysician, Integer idSpeciality, LocalDate startIVS, LocalDate stopIVS);
}
