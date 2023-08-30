package com.caido.appointments.repositories;

import com.caido.appointments.entity.LabTestsGroups;
import com.caido.appointments.entity.SpecialitiesLabTestsGroups;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SpecialitiesLabTestsGroupsRepository extends JpaRepository<SpecialitiesLabTestsGroups, Integer> {
    @Query("SELECT s.idLabTestsGroups "
            + "FROM SpecialitiesLabTestsGroups s "
            + "WHERE s.idSpeciality.id = ?1")
    List<LabTestsGroups> findLabTestsGroupsBySpeciality(Integer idSpeciality);
}
