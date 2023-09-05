package com.caido.appointments.entity.DTO;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.time.LocalDateTime;

public class ConsultatiiDTO {
    private Integer id;
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime consultationDate;
    private String speciality;
    private SimplePhysicianDTO physician;
    private Integer idScrisoareMedicala;
    private String serviceName;

    public void setConsultationDate(LocalDateTime consultationDate) {
        this.consultationDate = consultationDate;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public LocalDateTime getConsultationDate() {
        return consultationDate;
    }

    public String getServiceName() {
        return serviceName;
    }

    @Override
    public String toString() {
        return "ConsultatiiDTO{" + "id=" + id + ", appointmentDate=" + consultationDate + ", speciality=" + speciality + ", physician=" + physician + ", hasSM=" + idScrisoareMedicala + '}';
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public void setPhysician(SimplePhysicianDTO physician) {
        this.physician = physician;
    }

    public void setIdScrisoareMedicala(Integer idScrisoareMedicala) {
        this.idScrisoareMedicala = idScrisoareMedicala;
    }

    public Integer getId() {
        return id;
    }

    public String getSpeciality() {
        return speciality;
    }

    public SimplePhysicianDTO getPhysician() {
        return physician;
    }

    public Integer getIdScrisoareMedicala() {
        return idScrisoareMedicala;
    }

    public ConsultatiiDTO(Integer id, LocalDateTime consultationDate, String speciality, SimplePhysicianDTO physician, Integer idScrisoareMedicala, String serviceName) {
        this.id = id;
        this.consultationDate = consultationDate;
        this.speciality = speciality;
        this.physician = physician;
        this.idScrisoareMedicala = idScrisoareMedicala;
        this.serviceName = serviceName;
    }

    public ConsultatiiDTO() {
    }

}
