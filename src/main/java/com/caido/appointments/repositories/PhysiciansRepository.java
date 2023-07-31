package com.caido.appointments.repositories;

import com.caido.appointments.entity.Physicians;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PhysiciansRepository extends JpaRepository<Physicians, Integer> {
    @Query("SELECT p "
            + "FROM Physicians p "
            + "JOIN Physicianspecialities ps ON (ps.idphysician = p.id AND ps.idspeciality.id = ?1 AND ps.validfrom <= ?2 AND (ps.validto >= ?2 OR ps.validto IS NULL)) "
            + "JOIN Personnel pe ON (pe.id = p.idPersonnel.id and pe.validfrom <= ?2 AND (pe.validto >= ?2 OR pe.validto IS NULL)) "
            + "ORDER BY p.id DESC")
    List<Physicians> getAllByDateAndSpeciality(Integer idSpeciality, Date date);
}
