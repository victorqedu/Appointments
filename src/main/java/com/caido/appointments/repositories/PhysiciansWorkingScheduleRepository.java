package com.caido.appointments.repositories;

import com.caido.appointments.entity.PhysiciansWorkingSchedule;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PhysiciansWorkingScheduleRepository extends JpaRepository<PhysiciansWorkingSchedule, Integer> {
    /**
     * 
     * @param idPhysician
     * @param idSpeciality
     * @param startIVS - start date for the interval where we will check if the physician has a working schedule
     * @param stopIVS - stop date for the interval where we will check if the physician has a working schedule
     * @return a list with all the working schedules of the @idPhysician for the @idSpeciality
     */
    @Query("select pws "
            +"from PhysiciansWorkingSchedule pws " 
            +"join Physicians p ON (p.idPersonnel = pws.idPersonnel and p.id = ?1) " 
            +"where pws.idSpeciality.id = ?2 AND " 
                +"("
                + "pws.validFrom <= ?3 and (pws.validTo is null or pws.validTo >= ?3) OR "
                + "pws.validFrom <= ?4 and (pws.validTo is null or pws.validTo >= ?4) "
                + ")")
    List<PhysiciansWorkingSchedule> findPhysicianWorkingSchedule(Integer idPhysician, Integer idSpeciality, LocalDate startIVS, LocalDate stopIVS);
}
