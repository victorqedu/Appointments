package com.caido.appointments.repositories;

import com.caido.appointments.entity.DTO.SimplePhysicianDTO;
import com.caido.appointments.entity.Physicians;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PhysiciansRepository extends JpaRepository<Physicians, Integer> {
    @Query("SELECT NEW com.caido.appointments.entity.DTO.SimplePhysicianDTO(p.id, p.stencilNo, p.idPersonnel.imagineAngajat, p.idPersonnel.idperson.name, p.idPersonnel.idperson.surname, pr.idSex, p.idPersonnel.id, MIN(d.id)) "
            + "FROM Physicians p "
            + "JOIN Physicianspecialities ps ON (ps.idphysician = p.id AND ps.idspeciality.id = ?1 AND ps.validfrom <= ?2 AND (ps.validto >= ?2 OR ps.validto IS NULL)) "
            + "JOIN Personnel pe ON (pe.id = p.idPersonnel.id and pe.validfrom <= ?2 AND (pe.validto >= ?2 OR pe.validto IS NULL)) "
            + "JOIN PersonnelDepartment pd ON (pd.idPersonnel = pe.id and pd.validFrom <= ?2 AND (pd.validTo >= ?2 OR pd.validTo IS NULL)) "
            + "JOIN Department d ON (d.id = pd.idDepartment and d.idSpeciality = ?1 AND d.ambulatoriu = 1 ) "
            + "JOIN Person pr ON (pr.id = pe.idperson.id) "
            + "GROUP BY p.id, p.stencilNo, p.idPersonnel.imagineAngajat, p.idPersonnel.idperson.name, p.idPersonnel.idperson.surname, pr.idSex, p.idPersonnel.id "
            + "ORDER BY p.id DESC")
    List<SimplePhysicianDTO> getAllByDateAndSpeciality(Integer idSpeciality, LocalDate date);
}
