package com.caido.appointments.entity;

import java.io.Serializable;
import java.util.Collection;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 *
 * @author victor
 */
@Entity
@Table(name = "specialities")
@NamedQueries({
    @NamedQuery(name = "Specialities.findAll", query = "SELECT s FROM Specialities s"),
    @NamedQuery(name = "Specialities.findById", query = "SELECT s FROM Specialities s WHERE s.id = :id"),
    @NamedQuery(name = "Specialities.findByName", query = "SELECT s FROM Specialities s WHERE s.name = :name"),
    @NamedQuery(name = "Specialities.findByCode", query = "SELECT s FROM Specialities s WHERE s.code = :code")})
public class Specialities implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "code")
    private String code;
    
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idSpeciality")
//    private Collection<Appointments> appointmentsCollection;
//    
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idSpeciality")
//    private Collection<PhysiciansWorkingSchedule> physiciansWorkingScheduleCollection;
//    
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idspeciality")
//    private Collection<Physicianspecialities> physicianspecialitiesCollection;

    public Specialities() {
    }

    public Specialities(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

//    public Collection<Appointments> getAppointmentsCollection() {
//        return appointmentsCollection;
//    }
//
//    public void setAppointmentsCollection(Collection<Appointments> appointmentsCollection) {
//        this.appointmentsCollection = appointmentsCollection;
//    }
//
//    public Collection<PhysiciansWorkingSchedule> getPhysiciansWorkingScheduleCollection() {
//        return physiciansWorkingScheduleCollection;
//    }
//
//    public void setPhysiciansWorkingScheduleCollection(Collection<PhysiciansWorkingSchedule> physiciansWorkingScheduleCollection) {
//        this.physiciansWorkingScheduleCollection = physiciansWorkingScheduleCollection;
//    }
//
//    public Collection<Physicianspecialities> getPhysicianspecialitiesCollection() {
//        return physicianspecialitiesCollection;
//    }
//
//    public void setPhysicianspecialitiesCollection(Collection<Physicianspecialities> physicianspecialitiesCollection) {
//        this.physicianspecialitiesCollection = physicianspecialitiesCollection;
//    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Specialities)) {
            return false;
        }
        Specialities other = (Specialities) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.caido.appointments.entity.Specialities[ id=" + id + " ]";
    }
    
}
