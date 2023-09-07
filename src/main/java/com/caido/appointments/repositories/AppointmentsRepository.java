package com.caido.appointments.repositories;

import com.caido.appointments.entity.Appointments;
import com.caido.appointments.entity.DTO.AppointmentDTO;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;

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
        + "where a.idPhysicians.id = ?1 and a.idSpeciality.id = ?2 AND DATE(a.oraProgramare) >= ?3 AND DATE(a.oraProgramare) <= ?4 AND a.canceled = 0")
    List<Appointments> getPhysicianAppointments(Integer idPhysician, Integer idSpeciality, LocalDate startIVS, LocalDate stopIVS);

    /**
     * 
     * @param idPerson
     * @param pageable
     * @return - a list of appointments for the connected user with id = idPerson
     */
    @Query("SELECT a, c1.id AS idFirstAssociatedConsulation "
//            + "NEW com.caido.appointments.entity.DTO.AppointmentDTO("
//            + " a.id,"
//            + " NEW com.caido.appointments.entity.DTO.SimplePhysicianDTO(p.id, p.stencilNo, p.idPersonnel.imagineAngajat, p.idPersonnel.idperson.name, p.idPersonnel.idperson.surname, pr.idSex, p.idPersonnel.id, d.id), "
//            + " a.idSpeciality, "
//            + " 1=1,"
//            + " 1=1,"
//            + " a.labTestsGroups, "
//            + " a.oraProgramare"
//        + ") "
//            + " (SELECT lt FROM AppointmentsLabTestsGroups altg JOIN LabTestsGroups lt ON (lt.id = altg.idLabTestsGroups.id) WHERE altg.idAppointment.id = a.id) AS servicii, "
//            + " (SELECT NEW com.caido.appointments.entity.LabTestsGroups(lt.id, lt.code, lt.name) FROM AppointmentsLabTestsGroups altg JOIN LabTestsGroups lt ON (lt.id = altg.idLabTestsGroups.id) WHERE altg.idAppointment.id = a.id) AS servicii, "
//        + "NEW com.caido.appointments.entity.DTO.AppointmentDTO("
//            + " a.id AS id,"
//            + " NEW com.caido.appointments.entity.DTO.SimplePhysicianDTO(p.id, p.stencilNo, p.idPersonnel.imagineAngajat, p.idPersonnel.idperson.name, p.idPersonnel.idperson.surname, pr.idSex, p.idPersonnel.id, d.id) AS physician, "
//            + " a.idSpeciality AS speciality, "
//            + " false AS hasAssociatedConsultation,"
//            + " false AS canceled,"
//              + " (SELECT lt FROM AppointmentsLabTestsGroups altg JOIN LabTestsGroups lt ON (lt.id = altg.idLabTestsGroups.id) WHERE altg.idAppointment.id = a.id) AS servicii, "
//            + " a.oraProgramare AS oraProgramare"
//        + ") "
        + "FROM Appointments a "
        + "LEFT JOIN Consultatii c1 ON (c1.idPerson.id = a.idPerson.id and c1.idSpecialities.id = a.idSpeciality.id AND date(c1.dataConsultatiei) = date(a.oraProgramare) ) "
        + "LEFT JOIN Consultatii c2 ON (c2.idPerson.id = a.idPerson.id and c2.idSpecialities.id = a.idSpeciality.id AND date(c2.dataConsultatiei) = date(a.oraProgramare) AND c2.id < c1.id ) "
//        + "JOIN Physicians p ON (p.id = a.idPhysicians.id) "
//        + "JOIN Personnel pe ON (pe.id = p.idPersonnel.id) "
//        + "JOIN PersonnelDepartment pd ON (pd.idPersonnel = pe.id and pd.validFrom <= date(a.oraProgramare) AND (pd.validTo >= date(a.oraProgramare) OR pd.validTo IS NULL)) "
//        + "JOIN Department d ON (d.id = pd.idDepartment and d.idSpeciality = a.idSpeciality.id AND d.ambulatoriu = 1 ) "
//        + "JOIN Person pr ON (pr.id = pe.idperson.id) "
        + "WHERE a.idPerson.id = ?1 AND c2.id IS NULL "
        + "ORDER BY a.oraProgramare DESC, a.id DESC")
    List<Object[]> getConnectedUserAppointments(Integer idPerson, Pageable pageable);

    @Query("SELECT count(a) "
            + "FROM Appointments a "
            + "WHERE a.idPerson.id = ?1")
    Integer countConnectedUserAppointments(Integer idPerson);
}
