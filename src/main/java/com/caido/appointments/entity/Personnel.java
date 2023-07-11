package com.caido.appointments.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author victor
 */
@Entity
@Table(name = "personnel")
@NamedQueries({
    @NamedQuery(name = "Personnel.findAll", query = "SELECT p FROM Personnel p"),
    @NamedQuery(name = "Personnel.findById", query = "SELECT p FROM Personnel p WHERE p.id = :id"),
    @NamedQuery(name = "Personnel.findByValidfrom", query = "SELECT p FROM Personnel p WHERE p.validfrom = :validfrom"),
    @NamedQuery(name = "Personnel.findByValidto", query = "SELECT p FROM Personnel p WHERE p.validto = :validto"),
    @NamedQuery(name = "Personnel.findByIdConnectedUser", query = "SELECT p FROM Personnel p WHERE p.idConnectedUser = :idConnectedUser"),
    @NamedQuery(name = "Personnel.findByImagineBase64", query = "SELECT p FROM Personnel p WHERE p.imagineBase64 = :imagineBase64"),
    @NamedQuery(name = "Personnel.findByEmail", query = "SELECT p FROM Personnel p WHERE p.email = :email"),
    @NamedQuery(name = "Personnel.findByTelefon", query = "SELECT p FROM Personnel p WHERE p.telefon = :telefon"),
    @NamedQuery(name = "Personnel.findByImagineAngajat", query = "SELECT p FROM Personnel p WHERE p.imagineAngajat = :imagineAngajat")})
public class Personnel implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Basic(optional = false)
    @Column(name = "validfrom")
    @Temporal(TemporalType.DATE)
    private Date validfrom;
    
    @Column(name = "validto")
    @Temporal(TemporalType.DATE)
    private Date validto;
    
    @Column(name = "id_connected_user")
    private Integer idConnectedUser;
    
    @Basic(optional = false)
    @Lob
    @Column(name = "uid")
    private Object uid;
    
    @Column(name = "imagine_base64")
    private String imagineBase64;
    
    @Column(name = "email")
    private String email;
    
    @Column(name = "telefon")
    private String telefon;
    
    @Column(name = "imagine_angajat")
    private String imagineAngajat;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPersonnel")
    private Collection<Appointments> appointmentsCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPersonnel")
    private Collection<Physicians> physiciansCollection;
    
    @JoinColumn(name = "idperson", referencedColumnName = "id")
    @OneToOne(optional = false)
    private Person idperson;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPersonnel")
    private Collection<PhysiciansWorkingSchedule> physiciansWorkingScheduleCollection;

    public Personnel() {
    }

    public Personnel(Integer id) {
        this.id = id;
    }

    public Personnel(Integer id, Date validfrom, Object uid) {
        this.id = id;
        this.validfrom = validfrom;
        this.uid = uid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getIdConnectedUser() {
        return idConnectedUser;
    }

    public void setIdConnectedUser(Integer idConnectedUser) {
        this.idConnectedUser = idConnectedUser;
    }

    public Object getUid() {
        return uid;
    }

    public void setUid(Object uid) {
        this.uid = uid;
    }

    public String getImagineBase64() {
        return imagineBase64;
    }

    public void setImagineBase64(String imagineBase64) {
        this.imagineBase64 = imagineBase64;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getImagineAngajat() {
        return imagineAngajat;
    }

    public void setImagineAngajat(String imagineAngajat) {
        this.imagineAngajat = imagineAngajat;
    }

    public Collection<Appointments> getAppointmentsCollection() {
        return appointmentsCollection;
    }

    public void setAppointmentsCollection(Collection<Appointments> appointmentsCollection) {
        this.appointmentsCollection = appointmentsCollection;
    }

    public Collection<Physicians> getPhysiciansCollection() {
        return physiciansCollection;
    }

    public void setPhysiciansCollection(Collection<Physicians> physiciansCollection) {
        this.physiciansCollection = physiciansCollection;
    }

    public Person getIdperson() {
        return idperson;
    }

    public void setIdperson(Person idperson) {
        this.idperson = idperson;
    }

    public Collection<PhysiciansWorkingSchedule> getPhysiciansWorkingScheduleCollection() {
        return physiciansWorkingScheduleCollection;
    }

    public void setPhysiciansWorkingScheduleCollection(Collection<PhysiciansWorkingSchedule> physiciansWorkingScheduleCollection) {
        this.physiciansWorkingScheduleCollection = physiciansWorkingScheduleCollection;
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
        if (!(object instanceof Personnel)) {
            return false;
        }
        Personnel other = (Personnel) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.caido.appointments.entity.Personnel[ id=" + id + " ]";
    }
    
}
