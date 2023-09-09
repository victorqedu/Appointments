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
    
    @Query("SELECT c "
            + "FROM Config c "
            + "WHERE c.nume = 'MAIL_SERVER' ")
    Config getMailServer(); 

    @Query("SELECT c "
            + "FROM Config c "
            + "WHERE c.nume = 'MAIL_USER' ")
    Config getMailUser(); 
    
    @Query("SELECT c "
            + "FROM Config c "
            + "WHERE c.nume = 'MAIL_PASSWORD' ")
    Config getMailPassword(); 
        
    @Query("SELECT c "
            + "FROM Config c "
            + "WHERE c.nume = 'MAIL_SMTP_PORT' ")
    Config getMailSMTPPort(); 

    @Query("SELECT c "
            + "FROM Config c "
            + "WHERE c.nume = 'MAIL_SMTP_LOCALHOST' ")
    Config getMailSMTPLocalhost(); 
    
    @Query("SELECT c "
            + "FROM Config c "
            + "WHERE c.nume = 'MAIL_PROGRAMARI_ONLINE_SUBIECT_CONFIRMARE_CONT' ")
    Config getMailProgramariOnlineSubiectConfirmareCont(); 
    
    @Query("SELECT c "
            + "FROM Config c "
            + "WHERE c.nume = 'MAIL_PROGRAMARI_ONLINE_CONTINUT_CONFIRMARE_CONT' ")
    Config getMailProgramariOnlineContinutConfirmareCont(); 
    
    @Query("SELECT c "
            + "FROM Config c "
            + "WHERE c.nume = 'MAIL_PROGRAMARI_ONLINE_FROM' ")
    Config getMailProgramariOnlineFrom(); 
    
    @Query("SELECT c "
            + "FROM Config c "
            + "WHERE c.nume = 'PROGRAMARI_ONLINE_FRONTEND' ")
    Config getMailProgramariOnlineFrontend(); 
    
    
    
}
