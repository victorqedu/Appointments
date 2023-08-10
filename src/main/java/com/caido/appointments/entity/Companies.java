package com.caido.appointments.entity;

import java.io.Serializable;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "companies")
public class Companies implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "code")
    private String code;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "fiscal_code")
    private String fiscalCode;
    
    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    @Column(name = "mail")
    private String mail;
    
    @Column(name = "local_urg_unit")
    private Integer localUrgUnit;

    public Companies() {
    }

    public Companies(String code, String name, String fiscalCode, String address, String phone, String mail, Integer localUrgUnit) {
        this.code = code;
        this.name = name;
        this.fiscalCode = fiscalCode;
        this.address = address;
        this.phone = phone;
        this.mail = mail;
        this.localUrgUnit = localUrgUnit;
    }

    @Override
    public String toString() {
        return "Companies{" + "id=" + id + ", code=" + code + ", name=" + name + ", fiscalCode=" + fiscalCode + ", address=" + address + ", phone=" + phone + ", mail=" + mail + ", localUrgUnit=" + localUrgUnit + '}';
    }

    public Integer getLocalUrgUnit() {
        return localUrgUnit;
    }

    public void setLocalUrgUnit(Integer localUrgUnit) {
        this.localUrgUnit = localUrgUnit;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFiscalCode(String fiscalCode) {
        this.fiscalCode = fiscalCode;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getFiscalCode() {
        return fiscalCode;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getMail() {
        return mail;
    }
        
}
