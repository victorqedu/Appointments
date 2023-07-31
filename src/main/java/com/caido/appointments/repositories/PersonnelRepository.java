package com.caido.appointments.repositories;

import com.caido.appointments.entity.Personnel;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PersonnelRepository extends JpaRepository<Personnel, Integer> {
    @Query("SELECT c "
            + "FROM Personnel c "
            + "WHERE c.validfrom <= date(now()) AND (c.validto IS NULL OR c.validto >= date(now())) "
            + "ORDER BY c.id DESC")
    @Override
    List<Personnel> findAll();
}
