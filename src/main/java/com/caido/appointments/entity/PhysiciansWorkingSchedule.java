package com.caido.appointments.entity;

import java.io.Serializable;
import java.util.Date;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

/**
 *
 * @author victor
 */
@Entity
@Table(name = "physicians_working_schedule")
@NamedQueries({
    @NamedQuery(name = "PhysiciansWorkingSchedule.findAll", query = "SELECT p FROM PhysiciansWorkingSchedule p"),
    @NamedQuery(name = "PhysiciansWorkingSchedule.findById", query = "SELECT p FROM PhysiciansWorkingSchedule p WHERE p.id = :id"),
    @NamedQuery(name = "PhysiciansWorkingSchedule.findByLuni", query = "SELECT p FROM PhysiciansWorkingSchedule p WHERE p.luni = :luni"),
    @NamedQuery(name = "PhysiciansWorkingSchedule.findByMarti", query = "SELECT p FROM PhysiciansWorkingSchedule p WHERE p.marti = :marti"),
    @NamedQuery(name = "PhysiciansWorkingSchedule.findByMiercuri", query = "SELECT p FROM PhysiciansWorkingSchedule p WHERE p.miercuri = :miercuri"),
    @NamedQuery(name = "PhysiciansWorkingSchedule.findByJoi", query = "SELECT p FROM PhysiciansWorkingSchedule p WHERE p.joi = :joi"),
    @NamedQuery(name = "PhysiciansWorkingSchedule.findByVineri", query = "SELECT p FROM PhysiciansWorkingSchedule p WHERE p.vineri = :vineri"),
    @NamedQuery(name = "PhysiciansWorkingSchedule.findBySambata", query = "SELECT p FROM PhysiciansWorkingSchedule p WHERE p.sambata = :sambata"),
    @NamedQuery(name = "PhysiciansWorkingSchedule.findByDuminica", query = "SELECT p FROM PhysiciansWorkingSchedule p WHERE p.duminica = :duminica"),
    @NamedQuery(name = "PhysiciansWorkingSchedule.findByStart", query = "SELECT p FROM PhysiciansWorkingSchedule p WHERE p.start = :start"),
    @NamedQuery(name = "PhysiciansWorkingSchedule.findByStop", query = "SELECT p FROM PhysiciansWorkingSchedule p WHERE p.stop = :stop"),
    @NamedQuery(name = "PhysiciansWorkingSchedule.findByValidFrom", query = "SELECT p FROM PhysiciansWorkingSchedule p WHERE p.validFrom = :validFrom"),
    @NamedQuery(name = "PhysiciansWorkingSchedule.findByValidTo", query = "SELECT p FROM PhysiciansWorkingSchedule p WHERE p.validTo = :validTo"),
    @NamedQuery(name = "PhysiciansWorkingSchedule.findByZileLibereLegal", query = "SELECT p FROM PhysiciansWorkingSchedule p WHERE p.zileLibereLegal = :zileLibereLegal")})
public class PhysiciansWorkingSchedule implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "luni")
    private int luni;
    @Basic(optional = false)
    @Column(name = "marti")
    private int marti;
    @Basic(optional = false)
    @Column(name = "miercuri")
    private int miercuri;
    @Basic(optional = false)
    @Column(name = "joi")
    private int joi;
    @Basic(optional = false)
    @Column(name = "vineri")
    private int vineri;
    @Basic(optional = false)
    @Column(name = "sambata")
    private int sambata;
    @Basic(optional = false)
    @Column(name = "duminica")
    private int duminica;
    @Basic(optional = false)
    @Column(name = "start")
    @Temporal(TemporalType.TIME)
    private Date start;
    @Basic(optional = false)
    @Column(name = "stop")
    @Temporal(TemporalType.TIME)
    private Date stop;
    @Basic(optional = false)
    @Column(name = "valid_from")
    @Temporal(TemporalType.DATE)
    private Date validFrom;
    @Column(name = "valid_to")
    @Temporal(TemporalType.DATE)
    private Date validTo;
    @Basic(optional = false)
    @Column(name = "zile_libere_legal")
    private int zileLibereLegal;
    @JoinColumn(name = "id_personnel", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Personnel idPersonnel;
    @JoinColumn(name = "id_speciality", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Specialities idSpeciality;

    public PhysiciansWorkingSchedule() {
    }

    public PhysiciansWorkingSchedule(Integer id) {
        this.id = id;
    }

    public PhysiciansWorkingSchedule(Integer id, int luni, int marti, int miercuri, int joi, int vineri, int sambata, int duminica, Date start, Date stop, Date validFrom, int zileLibereLegal) {
        this.id = id;
        this.luni = luni;
        this.marti = marti;
        this.miercuri = miercuri;
        this.joi = joi;
        this.vineri = vineri;
        this.sambata = sambata;
        this.duminica = duminica;
        this.start = start;
        this.stop = stop;
        this.validFrom = validFrom;
        this.zileLibereLegal = zileLibereLegal;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getLuni() {
        return luni;
    }

    public void setLuni(int luni) {
        this.luni = luni;
    }

    public int getMarti() {
        return marti;
    }

    public void setMarti(int marti) {
        this.marti = marti;
    }

    public int getMiercuri() {
        return miercuri;
    }

    public void setMiercuri(int miercuri) {
        this.miercuri = miercuri;
    }

    public int getJoi() {
        return joi;
    }

    public void setJoi(int joi) {
        this.joi = joi;
    }

    public int getVineri() {
        return vineri;
    }

    public void setVineri(int vineri) {
        this.vineri = vineri;
    }

    public int getSambata() {
        return sambata;
    }

    public void setSambata(int sambata) {
        this.sambata = sambata;
    }

    public int getDuminica() {
        return duminica;
    }

    public void setDuminica(int duminica) {
        this.duminica = duminica;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getStop() {
        return stop;
    }

    public void setStop(Date stop) {
        this.stop = stop;
    }

    public Date getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    public Date getValidTo() {
        return validTo;
    }

    public void setValidTo(Date validTo) {
        this.validTo = validTo;
    }

    public int getZileLibereLegal() {
        return zileLibereLegal;
    }

    public void setZileLibereLegal(int zileLibereLegal) {
        this.zileLibereLegal = zileLibereLegal;
    }

    public Personnel getIdPersonnel() {
        return idPersonnel;
    }

    public void setIdPersonnel(Personnel idPersonnel) {
        this.idPersonnel = idPersonnel;
    }

    public Specialities getIdSpeciality() {
        return idSpeciality;
    }

    public void setIdSpeciality(Specialities idSpeciality) {
        this.idSpeciality = idSpeciality;
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
        if (!(object instanceof PhysiciansWorkingSchedule)) {
            return false;
        }
        PhysiciansWorkingSchedule other = (PhysiciansWorkingSchedule) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.caido.appointments.entity.PhysiciansWorkingSchedule[ id=" + id + " ]";
    }
    
}
