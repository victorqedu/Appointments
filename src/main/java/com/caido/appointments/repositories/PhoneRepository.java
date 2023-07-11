package com.caido.appointments.repositories;

import com.caido.appointments.entity.Phone;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PhoneRepository extends JpaRepository<Phone, Long> {
    @Query("SELECT c FROM Phone c ORDER BY c.id DESC")
    @Override
    List<Phone> findAll();
}
