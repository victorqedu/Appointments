package com.caido.appointments.entity.DTO;

import com.caido.appointments.entity.LabTestsGroups;
import com.caido.appointments.entity.Physicians;
import com.caido.appointments.entity.Specialities;
import java.time.LocalDate;
import java.util.Collection;

public class RequestAppointmentDTO {
    private LocalDate localDateStartIV;
    private LocalDate localDateStopIV;
    private Specialities idSpeciality;
    private Physicians idPhysician;
    private Collection<LabTestsGroups> labTestsGroups;

    public RequestAppointmentDTO() {
    }

    public RequestAppointmentDTO(LocalDate localDateStartIV, LocalDate localDateStopIV, Specialities idSpeciality, Physicians idPhysician, Collection<LabTestsGroups> labTestsGroups) {
        this.localDateStartIV = localDateStartIV;
        this.localDateStopIV = localDateStopIV;
        this.idSpeciality = idSpeciality;
        this.idPhysician = idPhysician;
        this.labTestsGroups = labTestsGroups;
    }

    public LocalDate getLocalDateStartIV() {
        return localDateStartIV;
    }

    public LocalDate getLocalDateStopIV() {
        return localDateStopIV;
    }

    public Specialities getIdSpeciality() {
        return idSpeciality;
    }

    public Physicians getIdPhysician() {
        return idPhysician;
    }

    public Collection<LabTestsGroups> getLabTestsGroups() {
        return labTestsGroups;
    }

    public void setLocalDateStartIV(LocalDate localDateStartIV) {
        this.localDateStartIV = localDateStartIV;
    }

    public void setLocalDateStopIV(LocalDate localDateStopIV) {
        this.localDateStopIV = localDateStopIV;
    }

    public void setIdSpeciality(Specialities idSpeciality) {
        this.idSpeciality = idSpeciality;
    }

    public void setIdPhysician(Physicians idPhysician) {
        this.idPhysician = idPhysician;
    }

    public void setLabTestsGroups(Collection<LabTestsGroups> labTestsGroups) {
        this.labTestsGroups = labTestsGroups;
    }

    @Override
    public String toString() {
        return "RequestAppointmentDTO{" + "localDateStartIV=" + localDateStartIV + ", localDateStopIV=" + localDateStopIV + ", idSpeciality=" + idSpeciality + ", idPhysician=" + idPhysician + ", labTestsGroups=" + labTestsGroups + '}';
    }


}
