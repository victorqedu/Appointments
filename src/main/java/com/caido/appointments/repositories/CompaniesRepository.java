package com.caido.appointments.repositories;

import com.caido.appointments.entity.Companies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CompaniesRepository extends JpaRepository<Companies, Integer> {
    @Query("select a " 
        + "from Companies a "
        + "where a.localUrgUnit = 1")
    Companies getLocalOrgUnit();
}
