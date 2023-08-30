package com.caido.appointments.entity.DTO;

public class SimplePhysicianDTO {
    private Integer id;
    private String stencilNo;
    private String imagineAngajat;
    private String name;
    private String surname;

    @Override
    public String toString() {
        return "SimplePhysicianDTO{" + "id=" + id + ", stencilNo=" + stencilNo + ", imagineAngajat=" + imagineAngajat + ", name=" + name + ", surname=" + surname + '}';
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

    public SimplePhysicianDTO(Integer id, String stencilNo, String imagineAngajat, String name, String surname) {
        this.id = id;
        this.stencilNo = stencilNo;
        this.imagineAngajat = imagineAngajat;
        this.name = name;
        this.surname = surname;
    }

    public SimplePhysicianDTO() {
    }


}