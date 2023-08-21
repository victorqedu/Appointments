package com.caido.appointments.repositories;

import com.caido.appointments.entity.Person;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

@Transactional 
@Component
public interface PersonRepository extends JpaRepository<Person, Integer> {
    @Query("SELECT p FROM Person p WHERE p.authEmail = ?1")
    public Person findByEmail(String email);

    @Query("SELECT p FROM Person p WHERE p.cnp = ?1")
    public Person findByCNP(String cnp);
}
