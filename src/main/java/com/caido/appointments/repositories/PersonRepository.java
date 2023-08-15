package com.caido.appointments.repositories;

import com.caido.appointments.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface PersonRepository extends JpaRepository<Person, Integer> {
    @Query("SELECT p FROM person p WHERE p.authEmail = ?1")
    public Person findByEmail(String Email);

    @Query("SELECT p FROM person p WHERE p.cnp = ?1")
    public Person findByCNP(String cnp);

}
