package com.caido.appointments.entity;

import static com.caido.appointments.Util.CNPStuff.checkCNP;
import static com.caido.appointments.Util.CNPStuff.getBirthDate;
import static com.caido.appointments.Util.CNPStuff.getSex;
import static com.caido.appointments.Util.Functions.checkEmail;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Collection;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.time.LocalDate;

@Entity
@Table(name = "person")
public class Person implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @JsonIgnore
    @Basic(optional = false)
    @Column(name = "cnp")
    private String cnp;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "surname")
    private String surname;
    
    @JsonIgnore
    @Basic(optional = false)
    @Column(name = "birthdate")
    @Temporal(TemporalType.DATE)
    private LocalDate birthdate;
    
    @JsonIgnore
    @Column(name = "cid")
    private String cid;
    
    @JsonIgnore
    @Column(name = "cod_strain")
    private String codStrain;
    
    @Column(name = "email")
    private String email;
    
    @OneToMany(mappedBy = "idpersoana")
    private Collection<Phone> phoneCollection;
    
    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "idperson")
    private Personnel personnel;
    
   @Column(name = "online_password")
    private String onlinePassword;
    public String getOnlinePassword() {
        return onlinePassword;
    }

    public void setOnlinePassword(String onlinePassword) {
        this.onlinePassword = onlinePassword;
    }
    
    @Column(name = "idsex")
    private Integer idsex;
    public Integer getIdsex() {
        return idsex;
    }

    public void setIdsex(Integer idsex) {
        this.idsex = idsex;
    }

    @Column(name = "auth_email")
    private String authEmail;
    public String getAuthEmail() {
        return authEmail;
    }

    public void setAuthEmail(String authEmail) {
        if(checkEmail(authEmail)) {
            this.authEmail = authEmail;
        } else {
            throw new RuntimeException("Email invalid "+authEmail);
        }
    }

    public Person() {
    }

    public Person(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCnp() {
        return cnp;
    }

    public void setCnp(String cnp) {
        if(!cnp.equals("0000000000000") && checkCNP(cnp)) {
            this.cnp = cnp;
            this.setBirthdate(getBirthDate(cnp));
            this.setIdsex(getSex(cnp));
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getCodStrain() {
        return codStrain;
    }

    public void setCodStrain(String codStrain) {
        this.codStrain = codStrain;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Collection<Phone> getPhoneCollection() {
        return phoneCollection;
    }

    public void setPhoneCollection(Collection<Phone> phoneCollection) {
        this.phoneCollection = phoneCollection;
    }

    public Personnel getPersonnel() {
        return personnel;
    }

    public void setPersonnel(Personnel personnel) {
        this.personnel = personnel;
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
        if (!(object instanceof Person)) {
            return false;
        }
        Person other = (Person) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.caido.appointments.entity.Person[ id=" + id + " ]";
    }
    
}
