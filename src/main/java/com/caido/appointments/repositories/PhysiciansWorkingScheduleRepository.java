package com.caido.appointments.repositories;

import com.caido.appointments.entity.PhysiciansWorkingSchedule;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PhysiciansWorkingScheduleRepository extends JpaRepository<PhysiciansWorkingSchedule, Integer> {
    @Query("SELECT c FROM PhysiciansWorkingSchedule c ORDER BY c.id DESC")
    @Override
    List<PhysiciansWorkingSchedule> findAll();
}
