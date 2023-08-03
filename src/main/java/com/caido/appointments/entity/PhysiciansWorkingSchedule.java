package com.caido.appointments.entity;

import java.io.Serializable;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 *
 * @author victor
 */
@Entity
@Table(name = "physicians_working_schedule")
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
    private LocalTime start;
    
    @Basic(optional = false)
    @Column(name = "stop")
    @Temporal(TemporalType.TIME)
    private LocalTime stop;
    
    @Basic(optional = false)
    @Column(name = "valid_from")
    @Temporal(TemporalType.DATE)
    private LocalDate validFrom;
    
    @Column(name = "valid_to")
    @Temporal(TemporalType.DATE)
    private LocalDate validTo;
    
    @Basic(optional = false)
    @Column(name = "zile_libere_legal")
    private int zileLibereLegal;
    
    @JoinColumn(name = "id_personnel", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Personnel idPersonnel;
    
    @JoinColumn(name = "id_speciality", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Specialities idSpeciality;
    
    @JoinColumn(name = "id_week_types", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private WeekTypes idWeekTypes;

    public PhysiciansWorkingSchedule() {
    }

    public PhysiciansWorkingSchedule(Integer id) {
        this.id = id;
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

    public LocalTime getStart() {
        return start;
    }

    public void setStart(LocalTime start) {
        this.start = start;
    }

    public LocalTime getStop() {
        return stop;
    }

    public void setStop(LocalTime stop) {
        this.stop = stop;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }

    public LocalDate getValidTo() {
        return validTo;
    }

    public void setValidTo(LocalDate validTo) {
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

    public WeekTypes getWeekType() {
        return idWeekTypes;
    }

    public void setIdSpeciality(WeekTypes idWeekTypes) {
        this.idWeekTypes = idWeekTypes;
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
        return "PhysiciansWorkingSchedule{" + "id=" + id + ", luni=" + luni + ", marti=" + marti + ", miercuri=" + miercuri + ", joi=" + joi + ", vineri=" + vineri + ", sambata=" + sambata + ", duminica=" + duminica + ", start=" + start + ", stop=" + stop + ", validFrom=" + validFrom + ", validTo=" + validTo + ", zileLibereLegal=" + zileLibereLegal + ", idPersonnel=" + idPersonnel + ", idSpeciality=" + idSpeciality + ", idWeekTypes=" + idWeekTypes + '}';
    }

}
