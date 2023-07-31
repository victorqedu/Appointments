package com.caido.appointments.entity;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name = "physicians")
@NamedQueries({
    @NamedQuery(name = "Physicians.findAll", query = "SELECT p FROM Physicians p"),
    @NamedQuery(name = "Physicians.findById", query = "SELECT p FROM Physicians p WHERE p.id = :id"),
    @NamedQuery(name = "Physicians.findByStencilNo", query = "SELECT p FROM Physicians p WHERE p.stencilNo = :stencilNo")})
public class Physicians implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Basic(optional = false)
    @Column(name = "stencil_no")
    private String stencilNo;
    
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPhysicians")
//    private Collection<Appointments> appointmentsCollection;
    
    @JoinColumn(name = "id_personnel", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Personnel idPersonnel;

    public Physicians() {
    }

    public Physicians(Integer id) {
        this.id = id;
    }

    public Physicians(Integer id, String stencilNo) {
        this.id = id;
        this.stencilNo = stencilNo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStencilNo() {
        return stencilNo;
    }

    public void setStencilNo(String stencilNo) {
        this.stencilNo = stencilNo;
    }

//    public Collection<Appointments> getAppointmentsCollection() {
//        return appointmentsCollection;
//    }
//
//    public void setAppointmentsCollection(Collection<Appointments> appointmentsCollection) {
//        this.appointmentsCollection = appointmentsCollection;
//    }

    public Personnel getIdPersonnel() {
        return idPersonnel;
    }

    public void setIdPersonnel(Personnel idPersonnel) {
        this.idPersonnel = idPersonnel;
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
        if (!(object instanceof Physicians)) {
            return false;
        }
        Physicians other = (Physicians) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.caido.appointments.entity.Physicians[ id=" + id + " ]";
    }
    
}
