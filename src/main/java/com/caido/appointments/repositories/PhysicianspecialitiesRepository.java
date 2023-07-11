package com.caido.appointments.repositories;

import com.caido.appointments.entity.Physicianspecialities;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PhysicianspecialitiesRepository extends JpaRepository<Physicianspecialities, Long> {
    @Query("SELECT c FROM Physicianspecialities c ORDER BY c.id DESC")
    @Override
    List<Physicianspecialities> findAll();
}
