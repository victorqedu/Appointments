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
@Table(name = "specialities_lab_tests_groups")
public class SpecialitiesLabTestsGroups {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @JoinColumn(name = "id_speciality", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Specialities idSpeciality;

    @JoinColumn(name = "id_lab_tests_groups", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private LabTestsGroups idLabTestsGroups;    

    public SpecialitiesLabTestsGroups() {
    }

    public SpecialitiesLabTestsGroups(Integer id, Specialities idSpeciality, LabTestsGroups idLabTestsGroups) {
        this.id = id;
        this.idSpeciality = idSpeciality;
        this.idLabTestsGroups = idLabTestsGroups;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public Specialities getIdSpeciality() {
        return idSpeciality;
    }

    public LabTestsGroups getIdLabTestsGroups() {
        return idLabTestsGroups;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setIdSpeciality(Specialities idSpeciality) {
        this.idSpeciality = idSpeciality;
    }

    public void setIdLabTestsGroups(LabTestsGroups idLabTestsGroups) {
        this.idLabTestsGroups = idLabTestsGroups;
    }

    @Override
    public String toString() {
        return "SpecialitiesLabTestsGroups{" + "id=" + id + ", idSpeciality=" + idSpeciality + ", idLabTestsGroups=" + idLabTestsGroups + '}';
    }


}

