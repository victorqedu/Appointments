package com.caido.appointments.entity.DTO;

import com.caido.appointments.entity.LabTestsGroups;
import com.caido.appointments.entity.Specialities;
import java.time.LocalDateTime;
import java.util.Collection;

public class AppointmentDTO {
    private Integer id;
    private SimplePhysicianDTO physician;
    private Specialities speciality;
    private boolean hasAssociatedConsultation;
    private boolean canceled;
    private Collection<LabTestsGroups> servicii;
    private LocalDateTime oraProgramare;

//            + " 1=1,"
//            + " a.labTestsGroups, "
//            + " a.oraProgramare"

    public AppointmentDTO(Integer id, SimplePhysicianDTO physician, Specialities speciality, boolean hasAssociatedConsultation, boolean canceled, Collection<LabTestsGroups> servicii, LocalDateTime oraProgramare) {
        this.id = id;
        this.physician = physician;
        this.speciality = speciality;
        this.hasAssociatedConsultation = hasAssociatedConsultation;
        this.canceled = canceled;
        this.servicii = servicii;
        this.oraProgramare = oraProgramare;
    }

    public void setOraProgramare(LocalDateTime oraProgramare) {
        this.oraProgramare = oraProgramare;
    }

    public LocalDateTime getOraProgramare() {
        return oraProgramare;
    }

    @Override
    public String toString() {
        return "AppointmentDTO{" + "id=" + id + ", physician=" + physician + ", speciality=" + speciality + ", hasAssociatedConsultation=" + hasAssociatedConsultation + ", canceled=" + canceled + ", servicii=" + servicii + '}';
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setPhysician(SimplePhysicianDTO physician) {
        this.physician = physician;
    }

    public void setSpeciality(Specialities speciality) {
        this.speciality = speciality;
    }

    public void setHasAssociatedConsultation(boolean hasAssociatedConsultation) {
        this.hasAssociatedConsultation = hasAssociatedConsultation;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }

    public void setServicii(Collection<LabTestsGroups> servicii) {
        this.servicii = servicii;
    }

    public Integer getId() {
        return id;
    }

    public SimplePhysicianDTO getPhysician() {
        return physician;
    }

    public Specialities getSpeciality() {
        return speciality;
    }

    public boolean isHasAssociatedConsultation() {
        return hasAssociatedConsultation;
    }

    public boolean isCanceled() {
        return canceled;
    }

    public Collection<LabTestsGroups> getServicii() {
        return servicii;
    }

}
