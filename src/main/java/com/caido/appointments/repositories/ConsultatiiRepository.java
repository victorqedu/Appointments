package com.caido.appointments.repositories;

import com.caido.appointments.entity.DTO.ConsultatiiDTO;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.caido.appointments.entity.Consultatii;

public interface ConsultatiiRepository extends JpaRepository<Consultatii, Integer> {
    @Query("SELECT "
        + "NEW com.caido.appointments.entity.DTO.ConsultatiiDTO(c.id, c.dataConsultatiei, c.idSpecialities.name, "
            + "NEW com.caido.appointments.entity.DTO.SimplePhysicianDTO(p.id, p.stencilNo, p.idPersonnel.imagineAngajat, p.idPersonnel.idperson.name, p.idPersonnel.idperson.surname, pr.idSex, p.idPersonnel.id, d.id), "
            + "sm.id, cs.name) "
        + "FROM Consultatii c "
        + "JOIN Clinicservice cs ON (cs.id = c.idClinicservice.id) "
        + "JOIN Physicians p ON (p.id = c.idPhysicians.id) "
        + "JOIN Personnel pe ON (pe.id = p.idPersonnel.id) "
        + "JOIN PersonnelDepartment pd ON (pd.idPersonnel = pe.id and pd.validFrom <= date(c.dataConsultatiei) AND (pd.validTo >= date(c.dataConsultatiei) OR pd.validTo IS NULL)) "
        + "JOIN Department d ON (d.id = pd.idDepartment and d.idSpeciality = c.idSpecialities.id AND d.ambulatoriu = 1 ) "
        + "JOIN Person pr ON (pr.id = pe.idperson.id) "
        + "LEFT JOIN ScrisoareMedicala sm ON (sm.idConsultatie.id = c.id) "
        + "WHERE c.idPerson.id = ?1 "
        + "ORDER BY c.dataConsultatiei DESC "
        + "LIMIT ?2 OFFSET ?3")
    List<ConsultatiiDTO> getPatientConsultations(Integer idPerson, Integer recordsPerPage, Integer pageNumber);
    
    @Query("SELECT count(c)"
        + "FROM Consultatii c "
        + "JOIN Physicians p ON (p.id = c.idPhysicians.id) "
        + "JOIN Personnel pe ON (pe.id = p.idPersonnel.id) "
        + "JOIN PersonnelDepartment pd ON (pd.idPersonnel = pe.id and pd.validFrom <= date(c.dataConsultatiei) AND (pd.validTo >= date(c.dataConsultatiei) OR pd.validTo IS NULL)) "
        + "JOIN Department d ON (d.id = pd.idDepartment and d.idSpeciality = c.idSpecialities.id AND d.ambulatoriu = 1 ) "
        + "JOIN Person pr ON (pr.id = pe.idperson.id) "
        + "WHERE c.idPerson.id = ?1 ")
    Integer countPatientConsultations(Integer idPerson);
}
