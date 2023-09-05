package com.caido.appointments.repositories;

import com.caido.appointments.entity.DTO.ScrisoareMedicalaDTO;
import com.caido.appointments.entity.ScrisoareMedicala;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ScrisoareMedicalaRepository extends JpaRepository<ScrisoareMedicala, Integer> {
    @Query("SELECT NEW com.caido.appointments.entity.DTO.ScrisoareMedicalaDTO("
            + " s.id,"
            + " CONCAT(pr.name, ' ', pr.surname), "
            + " ctr.contractNo, "
            + " ctr.orgUnitId.name, "
            + " s.idPerson, "
            + " s.idConsultatie.numarRegistru, "
            + " s.idConsultatie.dataConsultatiei, "
            + " s.idScrisoareMedicalaMotivePrezentare.nume, "
            + " s.diagnostic, "
            + " s.anamneza, "
            + " s.anamnezaFactoriRisc, "
            + " s.examenClinicGeneral, "
            + " s.examenClinicLocal, "
            + " s.exameneParacliniceAltele, "
            + " s.tratamentEfectuat,"
            + " s.alteInformatii, "
            + " s.tratamentRecomandat,"
            + " '', "
            + " '', "
            + " '', "
            + " s.exameneParacliniceEkg, "
            + " s.exameneParacliniceEco, "
            + " s.exameneParacliniceRx "
            + ") "
            + "FROM ScrisoareMedicala s "
            + "JOIN Physicians p ON (p.id = s.idPhysicians.id) "
            + "JOIN Personnel pe ON (pe.id = p.idPersonnel.id) "
            + "JOIN Person pr ON (pr.id = pe.idperson.id) "
            + "LEFT JOIN Contracts ctr ON (ctr.validFrom <= DATE(NOW()) AND ctr.validTo >= DATE(NOW()) AND ctr.contractType = 3 ) "
            + "JOIN Consultatii c ON (c.id = s.idConsultatie.id)"
            + "WHERE s.id = ?1")
    ScrisoareMedicalaDTO get(Integer idScrisoareMedicala); 
    
    @Query("SELECT s.idPerson.id FROM ScrisoareMedicala s WHERE s.id = ?1")
    Integer getPersonId(Integer idScrisoareMedicala);
    
}
