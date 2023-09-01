package com.caido.appointments.repositories;

import com.caido.appointments.entity.LabTestsGroups;
import com.caido.appointments.entity.SpecialitiesLabTestsGroups;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SpecialitiesLabTestsGroupsRepository extends JpaRepository<SpecialitiesLabTestsGroups, Integer> {
    @Query("SELECT s.idLabTestsGroups "
            + "FROM SpecialitiesLabTestsGroups s "
            + "JOIN LabTestsGroups ltg ON (ltg.id = s.idLabTestsGroups.id and ltg.visible = 1 and ltg.minuteEstimate IS NOT NULL and ltg.minuteEstimate > 0)"
            + "WHERE s.idSpeciality.id = ?1")
    List<LabTestsGroups> findLabTestsGroupsBySpeciality(Integer idSpeciality);
}
