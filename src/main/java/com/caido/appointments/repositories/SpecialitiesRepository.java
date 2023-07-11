package com.caido.appointments.repositories;

import com.caido.appointments.entity.Specialities;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SpecialitiesRepository extends JpaRepository<Specialities, Long> {
    @Query("SELECT c FROM Specialities c ORDER BY c.id DESC")
    @Override
    List<Specialities> findAll();    
}
