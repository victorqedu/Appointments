package com.caido.appointments.repositories;

import com.caido.appointments.entity.Email;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EmailRepository extends JpaRepository<Email, Integer> {
    @Query("SELECT c FROM Email c ORDER BY c.id DESC")
    @Override
    List<Email> findAll();
}
