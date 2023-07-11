package com.caido.appointments.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author victor
 */
@Entity
@Table(name = "appointments_types")
@NamedQueries({
    @NamedQuery(name = "AppointmentsTypes.findAll", query = "SELECT a FROM AppointmentsTypes a"),
    @NamedQuery(name = "AppointmentsTypes.findById", query = "SELECT a FROM AppointmentsTypes a WHERE a.id = :id"),
    @NamedQuery(name = "AppointmentsTypes.findByName", query = "SELECT a FROM AppointmentsTypes a WHERE a.name = :name")})
public class AppointmentsTypes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idAppointmentsTypes")
    private Collection<Appointments> appointmentsCollection;

    public AppointmentsTypes() {
    }

    public AppointmentsTypes(Integer id) {
        this.id = id;
    }

    public AppointmentsTypes(Integer id, String name) {
        this.id = id;
        this.name = name;
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

    public Collection<Appointments> getAppointmentsCollection() {
        return appointmentsCollection;
    }

    public void setAppointmentsCollection(Collection<Appointments> appointmentsCollection) {
        this.appointmentsCollection = appointmentsCollection;
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
        if (!(object instanceof AppointmentsTypes)) {
            return false;
        }
        AppointmentsTypes other = (AppointmentsTypes) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.caido.appointments.entity.AppointmentsTypes[ id=" + id + " ]";
    }
    
}
