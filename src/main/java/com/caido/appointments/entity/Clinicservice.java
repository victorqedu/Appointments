package com.caido.appointments.entity;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "clinicservice")
public class Clinicservice implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "code")
    private String code;
    @Column(name = "isconnectedservice")
    private Integer isconnectedservice;
    @Column(name = "isexam")
    private Integer isexam;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Column(name = "oproom")
    private Integer oproom;
    @Column(name = "servicegroup")
    private String servicegroup;
    @Column(name = "validto")
    @Temporal(TemporalType.DATE)
    private Date validto;
    @Basic(optional = false)
    @Column(name = "validfrom")
    @Temporal(TemporalType.DATE)
    private Date validfrom;
    @Column(name = "vasrta_minima")
    private Integer vasrtaMinima;
    @Column(name = "vasrta_maxima")
    private Integer vasrtaMaxima;

    public Clinicservice() {
    }

    public Clinicservice(Integer id) {
        this.id = id;
    }

    public Clinicservice(Integer id, String code, String name, Date validfrom) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.validfrom = validfrom;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getIsconnectedservice() {
        return isconnectedservice;
    }

    public void setIsconnectedservice(Integer isconnectedservice) {
        this.isconnectedservice = isconnectedservice;
    }

    public Integer getIsexam() {
        return isexam;
    }

    public void setIsexam(Integer isexam) {
        this.isexam = isexam;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOproom() {
        return oproom;
    }

    public void setOproom(Integer oproom) {
        this.oproom = oproom;
    }

    public String getServicegroup() {
        return servicegroup;
    }

    public void setServicegroup(String servicegroup) {
        this.servicegroup = servicegroup;
    }

    public Date getValidto() {
        return validto;
    }

    public void setValidto(Date validto) {
        this.validto = validto;
    }

    public Date getValidfrom() {
        return validfrom;
    }

    public void setValidfrom(Date validfrom) {
        this.validfrom = validfrom;
    }

    public Integer getVasrtaMinima() {
        return vasrtaMinima;
    }

    public void setVasrtaMinima(Integer vasrtaMinima) {
        this.vasrtaMinima = vasrtaMinima;
    }

    public Integer getVasrtaMaxima() {
        return vasrtaMaxima;
    }

    public void setVasrtaMaxima(Integer vasrtaMaxima) {
        this.vasrtaMaxima = vasrtaMaxima;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Clinicservice)) {
            return false;
        }
        Clinicservice other = (Clinicservice) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.caido.appointments.entity.Clinicservice[ id=" + id + " ]";
    }
    
}
