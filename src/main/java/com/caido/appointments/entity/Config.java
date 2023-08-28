package com.caido.appointments.entity;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "config")
public class Config {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "nume")
    private String nume;
    
    @Column(name = "valoare", columnDefinition = "TEXT")
    private String valoare;
    
    @Column(name = "limba")
    private String limba;
    
    @Column(name = "explicatii")
    private String explicatii;

    public Config(Integer id, String nume, String valoare, String limba, String explicatii) {
        this.id = id;
        this.nume = nume;
        this.valoare = valoare;
        this.limba = limba;
        this.explicatii = explicatii;
    }

    public Config() {
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public String getNume() {
        return nume;
    }

    public String getValoare() {
        return valoare;
    }

    public String getLimba() {
        return limba;
    }

    public String getExplicatii() {
        return explicatii;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public void setValoare(String valoare) {
        this.valoare = valoare;
    }

    public void setLimba(String limba) {
        this.limba = limba;
    }

    public void setExplicatii(String explicatii) {
        this.explicatii = explicatii;
    }


}
