package com.caido.appointments.repositories;

import com.caido.appointments.entity.Specialities;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SpecialitiesRepository extends JpaRepository<Specialities, Integer> {
    @Query("SELECT s "
            + "FROM Personnel e "
            + "JOIN Physicians p ON (p.idPersonnel = e.id) "
            + "JOIN Physicianspecialities ps ON (ps.idphysician = p.id AND ps.validfrom >= NOW() AND (ps.validto <= NOW() OR ps.validto IS NULL)) "
            + "JOIN Specialities s ON (s.id = ps.idspeciality) "
            + "WHERE e.validfrom >= NOW() AND (e.validto <= NOW() OR e.validto IS NULL) "
            + "ORDER BY s.id DESC")
    @Override
    List<Specialities> findAll();    
}
