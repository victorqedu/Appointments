package com.caido.appointments.repositories;


import com.caido.appointments.entity.Config;
import com.caido.appointments.entity.Specialities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ConfigRepository extends JpaRepository<Specialities, Integer> {
    @Query("SELECT c "
            + "FROM Config c "
            + "WHERE c.nume = 'PROGRAMARI_ONLINE_TERMS_AND_CONDITIONS' ")
    Config getTermsAndConditions();    

    @Query("SELECT c "
            + "FROM Config c "
            + "WHERE c.nume = 'PROGRAMARI_ONLINE_POLICY_OF_CONFIDENTIALITY' ")
    Config getPolicyOfConfidentiality(); 
    
    @Query("SELECT c "
            + "FROM Config c "
            + "WHERE c.nume = 'BAM_COD_IMAGINE_DREAPTA' ")
    Config getBamCodImagineDreapta(); 
    
    @Query("SELECT c "
            + "FROM Config c "
            + "WHERE c.nume = 'IMAGINE_IN_CURS_DE_ACREDITARE' ")
    Config getImagineInCursDeAcreditare(); 
    
  
        
}
