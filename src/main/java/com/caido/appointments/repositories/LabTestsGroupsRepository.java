package com.caido.appointments.repositories;

import com.caido.appointments.entity.LabTestsGroups;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface LabTestsGroupsRepository extends JpaRepository<LabTestsGroups, Integer> {
    @Query("SELECT ltg FROM LabTestsGroups ltg WHERE ltg.canBeScheduledOnline=1 ORDER BY ltg.name ASC")
    @Override
    List<LabTestsGroups> findAll();
}
