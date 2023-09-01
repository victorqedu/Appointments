package com.caido.appointments.entity.DTO;

public class SimplePhysicianDTO {
    private Integer id;
    private String stencilNo;
    private String imagineAngajat;
    private String name;
    private String surname;
    private Integer idSex;
    private Integer idPersonnel;
    private Integer idDepartment;

    public SimplePhysicianDTO() {
    }

    public SimplePhysicianDTO(Integer id, String stencilNo, String imagineAngajat, String name, String surname, Integer idSex, Integer idPersonnel, Integer idDepartment) {
        this.id = id;
        this.stencilNo = stencilNo;
        this.imagineAngajat = imagineAngajat;
        this.name = name;
        this.surname = surname;
        this.idSex = idSex;
        this.idPersonnel = idPersonnel;
        this.idDepartment = idDepartment;
    }

    public void setIdDepartment(Integer idDepartment) {
        this.idDepartment = idDepartment;
    }

    public Integer getIdDepartment() {
        return idDepartment;
    }

    public Integer getId() {
        return id;
    }

    public String getStencilNo() {
        return stencilNo;
    }

    public String getImagineAngajat() {
        return imagineAngajat;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public Integer getIdSex() {
        return idSex;
    }

    public Integer getIdPersonnel() {
        return idPersonnel;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setStencilNo(String stencilNo) {
        this.stencilNo = stencilNo;
    }

    public void setImagineAngajat(String imagineAngajat) {
        this.imagineAngajat = imagineAngajat;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setIdSex(Integer idSex) {
        this.idSex = idSex;
    }

    public void setIdPersonnel(Integer idPersonnel) {
        this.idPersonnel = idPersonnel;
    }

    @Override
    public String toString() {
        return "SimplePhysicianDTO{" + "id=" + id + ", stencilNo=" + stencilNo + ", imagineAngajat=" + imagineAngajat + ", name=" + name + ", surname=" + surname + ", idSex=" + idSex + ", idPersonnel=" + idPersonnel + '}';
    }
    

}