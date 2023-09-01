package com.caido.appointments.entity;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "department")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "code")
    private String code;

    @Column(name = "id_speciality")
    private Integer idSpeciality;

    public Department(Integer id, String name, String code, Integer idSpeciality) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.idSpeciality = idSpeciality;
    }

    @Override
    public String toString() {
        return "Department{" + "id=" + id + ", name=" + name + ", code=" + code + ", idSpeciality=" + idSpeciality + '}';
    }


    public void setIdSpeciality(Integer idSpeciality) {
        this.idSpeciality = idSpeciality;
    }

    public Integer getIdSpeciality() {
        return idSpeciality;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

}
