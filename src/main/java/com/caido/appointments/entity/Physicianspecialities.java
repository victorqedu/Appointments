package com.caido.appointments.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
    private Date validfrom;
    @Column(name = "validto")
    @Temporal(TemporalType.DATE)
    private Date validto;
    @JoinColumn(name = "idspeciality", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Specialities idspeciality;

    public Physicianspecialities() {
    }

    public Physicianspecialities(Integer id) {
        this.id = id;
    }

    public Physicianspecialities(Integer id, int idphysician, Date validfrom) {
        this.id = id;
        this.idphysician = idphysician;
        this.validfrom = validfrom;
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

    public Date getValidfrom() {
        return validfrom;
    }

    public void setValidfrom(Date validfrom) {
        this.validfrom = validfrom;
    }

    public Date getValidto() {
        return validto;
    }

    public void setValidto(Date validto) {
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
