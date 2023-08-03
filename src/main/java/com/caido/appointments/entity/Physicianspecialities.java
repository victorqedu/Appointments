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
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.time.LocalDate;

/**
 *
 * @author victor
 */
@Entity
@Table(name = "physicianspecialities")
@NamedQueries({
    @NamedQuery(name = "Physicianspecialities.findAll", query = "SELECT p FROM Physicianspecialities p"),
    @NamedQuery(name = "Physicianspecialities.findById", query = "SELECT p FROM Physicianspecialities p WHERE p.id = :id"),
    @NamedQuery(name = "Physicianspecialities.findByIdphysician", query = "SELECT p FROM Physicianspecialities p WHERE p.idphysician = :idphysician"),
    @NamedQuery(name = "Physicianspecialities.findByValidfrom", query = "SELECT p FROM Physicianspecialities p WHERE p.validfrom = :validfrom"),
    @NamedQuery(name = "Physicianspecialities.findByValidto", query = "SELECT p FROM Physicianspecialities p WHERE p.validto = :validto")})
public class Physicianspecialities implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Basic(optional = false)
    @Column(name = "idphysician")
    private int idphysician;
    
    @Basic(optional = false)
    @Column(name = "validfrom")
    @Temporal(TemporalType.DATE)
    private LocalDate validfrom;
    
    @Column(name = "validto")
    @Temporal(TemporalType.DATE)
    private LocalDate validto;
    
    @JoinColumn(name = "idspeciality", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Specialities idspeciality;

    public Physicianspecialities() {
    }

    public Physicianspecialities(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getIdphysician() {
        return idphysician;
    }

    public void setIdphysician(int idphysician) {
        this.idphysician = idphysician;
    }

    public LocalDate getValidfrom() {
        return validfrom;
    }

    public void setValidfrom(LocalDate validfrom) {
        this.validfrom = validfrom;
    }

    public LocalDate getValidto() {
        return validto;
    }

    public void setValidto(LocalDate validto) {
        this.validto = validto;
    }

    public Specialities getIdspeciality() {
        return idspeciality;
    }

    public void setIdspeciality(Specialities idspeciality) {
        this.idspeciality = idspeciality;
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
        if (!(object instanceof Physicianspecialities)) {
            return false;
        }
        Physicianspecialities other = (Physicianspecialities) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.caido.appointments.entity.Physicianspecialities[ id=" + id + " ]";
    }
    
}
