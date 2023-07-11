package com.caido.appointments.repositories;

import com.caido.appointments.entity.Physicians;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PhysiciansRepository extends JpaRepository<Physicians, Long> {
    @Query("SELECT c FROM Physicians c ORDER BY c.id DESC")
    @Override
    List<Physicians> findAll();
}
