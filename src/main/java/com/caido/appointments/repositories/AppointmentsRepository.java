package com.caido.appointments.repositories;

import com.caido.appointments.entity.Appointments;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AppointmentsRepository extends JpaRepository<Appointments, Integer> {
    @Query("SELECT c FROM Appointments c ORDER BY c.id DESC")
    @Override
    List<Appointments> findAll();
}
