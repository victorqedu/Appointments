package com.caido.appointments.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

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
    
    @JoinColumn(name = "idperson", referencedColumnName = "id")
    @OneToOne(optional = false)
    private Person idperson;

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


    public Person getIdperson() {
        return idperson;
    }

    public void setIdperson(Person idperson) {
        this.idperson = idperson;
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
