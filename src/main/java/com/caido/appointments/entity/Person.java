package com.caido.appointments.entity;

import com.caido.appointments.Util.CNPStuff;
import static com.caido.appointments.Util.CNPStuff.checkCNP;
import static com.caido.appointments.Util.CNPStuff.getSex;
import static com.caido.appointments.Util.Functions.checkEmail;
import static com.caido.appointments.Util.Functions.empty;
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
import java.time.LocalDate;

@Entity
@Table(name = "person")
public class Person implements Serializable {

    @Column(name = "phone",nullable = false)
    private String phone;

    /**
     * Get the value of phone
     *
     * @return the value of phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Set the value of phone
     *
     * @param phone new value of phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Basic(optional = false)
    @Column(name = "cnp",nullable = false)
    private String cnp;
    
    @Column(name = "name",nullable = false)
    private String name;
    
    @Column(name = "surname",nullable = false)
    private String surname;
    
    @Basic(optional = false)
    @Column(name = "birthdate",nullable = false)
    //@Temporal(TemporalType.DATE)
    private LocalDate birthDate;
    
    @JsonIgnore
    @Column(name = "cid")
    private String cid;
    
    @OneToMany(mappedBy = "idpersoana")
    private Collection<Phone> phoneCollection;
    
    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "idperson")
    private Personnel personnel;
    
   @Column(name = "online_password")
    private String onlinePassword;
    public String getOnlinePassword() {
        return null;
    }

    @JsonIgnore
    public String getOnlinePasswordReal() {
        return this.onlinePassword;
    }

    public void setOnlinePassword(String onlinePassword) {
        this.onlinePassword = onlinePassword;
    }
    
    @Column(name = "idsex", nullable = false)
    private Integer idSex;
    public Integer getIdSex() {
        return idSex;
    }

    public void setIdSex(Integer idSex) {
        this.idSex = idSex;
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
            if(this.onlinePassword==null || this.onlinePassword.equals("")) {
                // I need to be able to set null in email when I update the olc person without a valid cnp to a new person with a valid cnp, email cant be twice in the database so it needs to be nullified
                this.authEmail = authEmail;
            } else {
                throw new RuntimeException("Email invalid "+authEmail);
            }
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
        System.out.println("Start set cnp "+cnp);
        if(empty(cnp)) {
            cnp = "0000000000000";
        }
        this.cnp = cnp;
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

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
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
        return "Person{" + "id=" + id + ", cnp=" + cnp + ", name=" + name + ", surname=" + surname + ", birthdate=" + birthDate + ", cid=" + cid + ", phoneCollection=" + phoneCollection + ", personnel=" + personnel + ", onlinePassword=" + onlinePassword + ", idsex=" + idSex + ", authEmail=" + authEmail + '}';
    }

    public boolean check() {
        if(empty(getOnlinePasswordReal())) {
            throw new RuntimeException("Parola nu poate fi nula");
        }
        if(!empty(cnp) && !cnp.equals("0000000000000") && checkCNP(cnp)) {
            this.setBirthDate(CNPStuff.getBirthDate(cnp));
            this.setIdSex(getSex(cnp));
        } 
        if(empty(getAuthEmail())) {
            throw new RuntimeException("Trebuie sa specificati un email");
        }
        if(this.birthDate==null) {
            throw new RuntimeException("Trebuie sa specificati data nasterii sau un cnp");
        }
        if(this.idSex==null) {
            throw new RuntimeException("Trebuie sa specificati sexul sau un cnp");
        }
        return true;
    }
}
