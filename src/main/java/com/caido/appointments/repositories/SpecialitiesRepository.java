package com.caido.appointments.repositories;

import com.caido.appointments.entity.Specialities;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SpecialitiesRepository extends JpaRepository<Specialities, Integer> {
    @Query("SELECT distinct s "
            + "FROM Personnel e "
            + "JOIN Physicians p ON (p.idPersonnel.id = e.id) "
            + "JOIN Physicianspecialities ps ON (ps.idphysician = p.id AND ps.validfrom <= NOW() AND (ps.validto >= NOW() OR ps.validto IS NULL)) "
            + "JOIN Specialities s ON (s.id = ps.idspeciality.id) "
            + "WHERE e.validfrom <= NOW() AND (e.validto >= NOW() OR e.validto IS NULL) "
            + "ORDER BY s.name ASC")
    @Override
    List<Specialities> findAll();    
}
