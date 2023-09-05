package com.caido.appointments.entity.DTO;

import com.caido.appointments.entity.Person;
import java.time.LocalDateTime;

public class ScrisoareMedicalaDTO {
    private Integer id;
    private String numeMedic;
    private String contract;
    private String cjas;
    private Person person;
    private Integer nrConsultatie;
    private LocalDateTime dataConsultatie;
    private String motivelePrezentarii;
    private String diagnosticSiCodificare;
    private String anamneza;
    private String factoriDeRisc;
    private String examenClinicGeneral;
    private String examenClinicLocal;
    private String alte;
    private String tratamentEfectuat;
    private String alteInformatii;
    private String tratamentRecomandat;
    private String reteta;
    private String retetaZile;
    private String concediuMedical;
    private String exameneParacliniceEkg;
    private String exameneParacliniceEco;
    private String exameneParacliniceRx;

    public String getExameneParacliniceEkg() {
        return exameneParacliniceEkg;
    }

    public String getExameneParacliniceEco() {
        return exameneParacliniceEco;
    }

    public String getExameneParacliniceRx() {
        return exameneParacliniceRx;
    }

    public void setExameneParacliniceEkg(String exameneParacliniceEkg) {
        this.exameneParacliniceEkg = exameneParacliniceEkg;
    }

    public void setExameneParacliniceEco(String exameneParacliniceEco) {
        this.exameneParacliniceEco = exameneParacliniceEco;
    }

    public void setExameneParacliniceRx(String exameneParacliniceRx) {
        this.exameneParacliniceRx = exameneParacliniceRx;
    }

    public ScrisoareMedicalaDTO(Integer id, String numeMedic, String contract, String cjas, Person person, Integer nrConsultatie, LocalDateTime dataConsultatie, String motivelePrezentarii, String diagnosticSiCodificare, String anamneza, String factoriDeRisc, String examenClinicGeneral, String examenClinicLocal, String alte, String tratamentEfectuat, String alteInformatii, String tratamentRecomandat, String reteta, String retetaZile, String concediuMedical, String exameneParacliniceEkg, String exameneParacliniceEco, String exameneParacliniceRx) {
        this.id = id;
        this.numeMedic = numeMedic;
        this.contract = contract;
        this.cjas = cjas;
        this.person = person;
        this.nrConsultatie = nrConsultatie;
        this.dataConsultatie = dataConsultatie;
        this.motivelePrezentarii = motivelePrezentarii;
        this.diagnosticSiCodificare = diagnosticSiCodificare;
        this.anamneza = anamneza;
        this.factoriDeRisc = factoriDeRisc;
        this.examenClinicGeneral = examenClinicGeneral;
        this.examenClinicLocal = examenClinicLocal;
        this.alte = alte;
        this.tratamentEfectuat = tratamentEfectuat;
        this.alteInformatii = alteInformatii;
        this.tratamentRecomandat = tratamentRecomandat;
        this.reteta = reteta;
        this.retetaZile = retetaZile;
        this.concediuMedical = concediuMedical;
        this.exameneParacliniceEkg = exameneParacliniceEkg;
        this.exameneParacliniceEco = exameneParacliniceEco;
        this.exameneParacliniceRx = exameneParacliniceRx;
    }


    
    @Override
    public String toString() {
        return "ScrisoareMedicalaDTO{" + "id=" + id + ", numeMedic=" + numeMedic + ", contract=" + contract + ", cjas=" + cjas + ", person=" + person + ", nrConsultatie=" + nrConsultatie + ", dataConsultatie=" + dataConsultatie + ", motivelePrezentarii=" + motivelePrezentarii + ", diagnosticSiCodificare=" + diagnosticSiCodificare + ", anamneza=" + anamneza + ", factoriDeRisc=" + factoriDeRisc + ", examenClinicGeneral=" + examenClinicGeneral + ", examenClinicLocal=" + examenClinicLocal + ", alte=" + alte + ", tratamentEfectuat=" + tratamentEfectuat + ", alteInformatii=" + alteInformatii + ", tratamentRecomandat=" + tratamentRecomandat + ", reteta=" + reteta + ", retetaZile=" + retetaZile + ", concediuMedical=" + concediuMedical + '}';
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setNumeMedic(String numeMedic) {
        this.numeMedic = numeMedic;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }

    public void setCjas(String cjas) {
        this.cjas = cjas;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public void setNrConsultatie(Integer nrConsultatie) {
        this.nrConsultatie = nrConsultatie;
    }

    public void setDataConsultatie(LocalDateTime dataConsultatie) {
        this.dataConsultatie = dataConsultatie;
    }

    public void setMotivelePrezentarii(String motivelePrezentarii) {
        this.motivelePrezentarii = motivelePrezentarii;
    }

    public void setDiagnosticSiCodificare(String diagnosticSiCodificare) {
        this.diagnosticSiCodificare = diagnosticSiCodificare;
    }

    public void setAnamneza(String anamneza) {
        this.anamneza = anamneza;
    }

    public void setFactoriDeRisc(String factoriDeRisc) {
        this.factoriDeRisc = factoriDeRisc;
    }

    public void setExamenClinicGeneral(String examenClinicGeneral) {
        this.examenClinicGeneral = examenClinicGeneral;
    }

    public void setExamenClinicLocal(String examenClinicLocal) {
        this.examenClinicLocal = examenClinicLocal;
    }

    public void setAlte(String alte) {
        this.alte = alte;
    }

    public void setTratamentEfectuat(String tratamentEfectuat) {
        this.tratamentEfectuat = tratamentEfectuat;
    }

    public void setAlteInformatii(String alteInformatii) {
        this.alteInformatii = alteInformatii;
    }

    public void setTratamentRecomandat(String tratamentRecomandat) {
        this.tratamentRecomandat = tratamentRecomandat;
    }

    public void setReteta(String reteta) {
        this.reteta = reteta;
    }

    public void setRetetaZile(String retetaZile) {
        this.retetaZile = retetaZile;
    }

    public void setConcediuMedical(String concediuMedical) {
        this.concediuMedical = concediuMedical;
    }

    public Integer getId() {
        return id;
    }

    public String getNumeMedic() {
        return numeMedic;
    }

    public String getContract() {
        return contract;
    }

    public String getCjas() {
        return cjas;
    }

    public Person getPerson() {
        return person;
    }

    public Integer getNrConsultatie() {
        return nrConsultatie;
    }

    public LocalDateTime getDataConsultatie() {
        return dataConsultatie;
    }

    public String getMotivelePrezentarii() {
        return motivelePrezentarii;
    }

    public String getDiagnosticSiCodificare() {
        return diagnosticSiCodificare;
    }

    public String getAnamneza() {
        return anamneza;
    }

    public String getFactoriDeRisc() {
        return factoriDeRisc;
    }

    public String getExamenClinicGeneral() {
        return examenClinicGeneral;
    }

    public String getExamenClinicLocal() {
        return examenClinicLocal;
    }

    public String getAlte() {
        return alte;
    }

    public String getTratamentEfectuat() {
        return tratamentEfectuat;
    }

    public String getAlteInformatii() {
        return alteInformatii;
    }

    public String getTratamentRecomandat() {
        return tratamentRecomandat;
    }

    public String getReteta() {
        return reteta;
    }

    public String getRetetaZile() {
        return retetaZile;
    }

    public String getConcediuMedical() {
        return concediuMedical;
    }

    public ScrisoareMedicalaDTO() {
    }

}
