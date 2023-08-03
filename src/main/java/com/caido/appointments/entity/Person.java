package com.caido.appointments.entity;

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
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.time.LocalDate;

/**
 *
 * @author victor
 */
@Entity
@Table(name = "person")
@NamedQueries({
    @NamedQuery(name = "Person.findAll", query = "SELECT p FROM Person p"),
    @NamedQuery(name = "Person.findById", query = "SELECT p FROM Person p WHERE p.id = :id"),
    @NamedQuery(name = "Person.findByCnp", query = "SELECT p FROM Person p WHERE p.cnp = :cnp"),
    @NamedQuery(name = "Person.findByBiserial", query = "SELECT p FROM Person p WHERE p.biserial = :biserial"),
    @NamedQuery(name = "Person.findByBinumber", query = "SELECT p FROM Person p WHERE p.binumber = :binumber"),
    @NamedQuery(name = "Person.findByCnnumber", query = "SELECT p FROM Person p WHERE p.cnnumber = :cnnumber"),
    @NamedQuery(name = "Person.findByCnserial", query = "SELECT p FROM Person p WHERE p.cnserial = :cnserial"),
    @NamedQuery(name = "Person.findByName", query = "SELECT p FROM Person p WHERE p.name = :name"),
    @NamedQuery(name = "Person.findBySurname", query = "SELECT p FROM Person p WHERE p.surname = :surname"),
    @NamedQuery(name = "Person.findByBirthdate", query = "SELECT p FROM Person p WHERE p.birthdate = :birthdate"),
    @NamedQuery(name = "Person.findByCid", query = "SELECT p FROM Person p WHERE p.cid = :cid"),
    @NamedQuery(name = "Person.findByDeceaseDate", query = "SELECT p FROM Person p WHERE p.deceaseDate = :deceaseDate"),
    @NamedQuery(name = "Person.findByCodStrain", query = "SELECT p FROM Person p WHERE p.codStrain = :codStrain"),
    @NamedQuery(name = "Person.findByEmail", query = "SELECT p FROM Person p WHERE p.email = :email")})
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
    
    @JsonIgnore
    @Column(name = "biserial")
    private String biserial;
    
    @JsonIgnore
    @Column(name = "binumber")
    private String binumber;
    
    @JsonIgnore
    @Column(name = "cnnumber")
    private String cnnumber;
    
    @JsonIgnore
    @Column(name = "cnserial")
    private String cnserial;
    
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
    
    @Column(name = "decease_date")
    @Temporal(TemporalType.DATE)
    private LocalDate deceaseDate;
    
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
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idPerson")
    private Collection<Email> emailCollection;

    public Person() {
    }

    public Person(Integer id) {
        this.id = id;
    }

    public Person(Integer id, String cnp, LocalDate birthdate) {
        this.id = id;
        this.cnp = cnp;
        this.birthdate = birthdate;
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
        this.cnp = cnp;
    }

    public String getBiserial() {
        return biserial;
    }

    public void setBiserial(String biserial) {
        this.biserial = biserial;
    }

    public String getBinumber() {
        return binumber;
    }

    public void setBinumber(String binumber) {
        this.binumber = binumber;
    }

    public String getCnnumber() {
        return cnnumber;
    }

    public void setCnnumber(String cnnumber) {
        this.cnnumber = cnnumber;
    }

    public String getCnserial() {
        return cnserial;
    }

    public void setCnserial(String cnserial) {
        this.cnserial = cnserial;
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

    public LocalDate getDeceaseDate() {
        return deceaseDate;
    }

    public void setDeceaseDate(LocalDate deceaseDate) {
        this.deceaseDate = deceaseDate;
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

    public Collection<Email> getEmailCollection() {
        return emailCollection;
    }

    public void setEmailCollection(Collection<Email> emailCollection) {
        this.emailCollection = emailCollection;
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
