package com.caido.appointments.entity;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "personnel_department")
public class PersonnelDepartment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Column(name = "id_personnel")
    private Integer idPersonnel;
    
    @Column(name = "id_department")
    private Integer idDepartment;
    
    @Column(name = "valid_from")
    private LocalDate validFrom;
    
    @Column(name = "valid_to")
    private LocalDate validTo;

    public PersonnelDepartment() {
    }

    public PersonnelDepartment(Integer id, Integer idPersonnel, Integer idDepartment, LocalDate validFrom, LocalDate valiTo) {
        this.id = id;
        this.idPersonnel = idPersonnel;
        this.idDepartment = idDepartment;
        this.validFrom = validFrom;
        this.validTo = valiTo;
    }

    public Integer getId() {
        return id;
    }

    public Integer getIdPersonnel() {
        return idPersonnel;
    }

    public Integer getIdDepartment() {
        return idDepartment;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public LocalDate getValiTo() {
        return validTo;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setIdPersonnel(Integer idPersonnel) {
        this.idPersonnel = idPersonnel;
    }

    public void setIdDepartment(Integer idDepartment) {
        this.idDepartment = idDepartment;
    }

    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }

    public void setValiTo(LocalDate valiTo) {
        this.validTo = valiTo;
    }

    @Override
    public String toString() {
        return "PersonnelDepartment{" + "id=" + id + ", idPersonnel=" + idPersonnel + ", idDepartment=" + idDepartment + ", validFrom=" + validFrom + ", valiTo=" + validTo + '}';
    }
    

}
