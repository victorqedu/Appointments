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
import java.time.LocalDateTime;

@Entity
@Table(name = "consultatii")
public class Consultatii {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "data_consultatiei", nullable=false)
    private LocalDateTime dataConsultatiei;
    
    @JoinColumn(name = "id_physicians", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Physicians idPhysicians;

    @JoinColumn(name = "id_specialities", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Specialities idSpecialities;
    
    @JoinColumn(name = "id_person", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Person idPerson;
    
    @JoinColumn(name = "id_clinicservice", referencedColumnName = "id")
    @ManyToOne(optional = false)    
    private Clinicservice idClinicservice;

    public Clinicservice getIdClinicservice() {
        return idClinicservice;
    }

    public void setIdClinicservice(Clinicservice idClinicservice) {
        this.idClinicservice = idClinicservice;
    }
    
    public Consultatii() {
    }

    public Consultatii(Integer id, LocalDateTime dataConsultatiei, Physicians idPhysicians, Specialities idSpecialities, Person idPerson, Clinicservice idClinicservice) {
        this.id = id;
        this.dataConsultatiei = dataConsultatiei;
        this.idPhysicians = idPhysicians;
        this.idSpecialities = idSpecialities;
        this.idPerson = idPerson;
        this.idClinicservice = idClinicservice;
    }


    public Integer getId() {
        return id;
    }

    public LocalDateTime getDataConsultatiei() {
        return dataConsultatiei;
    }

    public Physicians getIdPhysicians() {
        return idPhysicians;
    }

    public Specialities getIdSpecialities() {
        return idSpecialities;
    }

    public Person getIdPerson() {
        return idPerson;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDataConsultatiei(LocalDateTime dataConsultatiei) {
        this.dataConsultatiei = dataConsultatiei;
    }

    public void setIdPhysicians(Physicians idPhysicians) {
        this.idPhysicians = idPhysicians;
    }

    public void setIdSpecialities(Specialities idSpecialities) {
        this.idSpecialities = idSpecialities;
    }

    public void setIdPerson(Person idPerson) {
        this.idPerson = idPerson;
    }

    @Override
    public String toString() {
        return "Consultatii{" + "id=" + id + ", dataConsultatiei=" + dataConsultatiei + ", idPhysicians=" + idPhysicians + ", idSpecialities=" + idSpecialities + ", idPerson=" + idPerson + '}';
    }

    
}
