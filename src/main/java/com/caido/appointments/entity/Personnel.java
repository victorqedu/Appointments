package com.caido.appointments.entity;

import java.io.Serializable;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.time.LocalDate;
import java.util.Collection;

/**
 *
 * @author victor
 */
@Entity
@Table(name = "personnel")
public class Personnel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Basic(optional = false)
    @Column(name = "validfrom")
    @Temporal(TemporalType.DATE)
    private LocalDate validfrom;
    
    @Column(name = "validto")
    @Temporal(TemporalType.DATE)
    private LocalDate validto;
    
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

    public Personnel(Integer id, LocalDate validfrom, Object uid) {
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
