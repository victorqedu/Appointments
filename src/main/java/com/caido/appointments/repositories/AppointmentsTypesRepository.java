package com.caido.appointments.repositories;

import com.caido.appointments.entity.AppointmentsTypes;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AppointmentsTypesRepository extends JpaRepository<AppointmentsTypes, Integer>{
    @Query("SELECT c FROM AppointmentsTypes c ORDER BY c.id DESC")
    @Override
    List<AppointmentsTypes> findAll();
}
