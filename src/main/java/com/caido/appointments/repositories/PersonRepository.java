package com.caido.appointments.repositories;

import com.caido.appointments.entity.Person;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface PersonRepository extends JpaRepository<Person, Long> {
    @Query("SELECT c FROM Person c ORDER BY c.id DESC")
    @Override
    List<Person> findAll();
}
