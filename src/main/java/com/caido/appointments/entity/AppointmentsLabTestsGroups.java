package com.caido.appointments.entity;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "appointments_lab_tests_groups")
public class AppointmentsLabTestsGroups {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @JoinColumn(name = "id_appointments", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Appointments idAppointment;

    @JoinColumn(name = "id_lab_tests_groups", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private LabTestsGroups idLabTestsGroups;    

    public AppointmentsLabTestsGroups() {
    }

    public AppointmentsLabTestsGroups(Integer id, Appointments idAppointment, LabTestsGroups idLabTestsGroups) {
        this.id = id;
        this.idAppointment = idAppointment;
        this.idLabTestsGroups = idLabTestsGroups;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setIdAppointment(Appointments idAppointment) {
        this.idAppointment = idAppointment;
    }

    public void setIdLabTestsGroups(LabTestsGroups idLabTestsGroups) {
        this.idLabTestsGroups = idLabTestsGroups;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public Appointments getIdAppointment() {
        return idAppointment;
    }

    public LabTestsGroups getIdLabTestsGroups() {
        return idLabTestsGroups;
    }

    @Override
    public String toString() {
        return "AppointmentsLabTestsGroups{" + "id=" + id + ", idAppointment=" + idAppointment + ", idLabTestsGroups=" + idLabTestsGroups + '}';
    }


}
