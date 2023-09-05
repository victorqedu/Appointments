package com.caido.appointments.entity;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "scrisoare_medicala")
public class ScrisoareMedicala implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "data")
    @Temporal(TemporalType.DATE)
    private Date data;
    @Column(name = "nr_registru_consultatii")
    private String nrRegistruConsultatii;
    @Column(name = "diagnostic")
    private String diagnostic;
    @Column(name = "anamneza")
    private String anamneza;
    @Column(name = "anamneza_factori_risc")
    private String anamnezaFactoriRisc;
    @Column(name = "examen_clinic_general")
    private String examenClinicGeneral;
    @Column(name = "examen_clinic_local")
    private String examenClinicLocal;
    @Column(name = "analiza_laborator_normale")
    private String analizaLaboratorNormale;
    @Column(name = "analiza_laborator_patologice")
    private String analizaLaboratorPatologice;
    @Column(name = "examene_paraclinice_ekg")
    private String exameneParacliniceEkg;
    @Column(name = "examene_paraclinice_eco")
    private String exameneParacliniceEco;
    @Column(name = "examene_paraclinice_rx")
    private String exameneParacliniceRx;
    @Column(name = "examene_paraclinice_altele")
    private String exameneParacliniceAltele;
    @Column(name = "tratament_efectuat")
    private String tratamentEfectuat;
    @Column(name = "alte_informatii")
    private String alteInformatii;
    @Column(name = "revine_pentru_internare_in")
    private String revinePentruInternareIn;
    @Column(name = "tratament_recomandat")
    private String tratamentRecomandat;
    @Basic(optional = false)
    @Column(name = "checkbox_da_revine_internare")
    private short checkboxDaRevineInternare;
    @Basic(optional = false)
    @Column(name = "checkbox_nu_revine_internare")
    private short checkboxNuRevineInternare;
    @Basic(optional = false)
    @Column(name = "checkbox_da_prescriptie")
    private short checkboxDaPrescriptie;
    @Basic(optional = false)
    @Column(name = "checkbox_nu_este_necesara_prescriptie")
    private short checkboxNuEsteNecesaraPrescriptie;
    @Basic(optional = false)
    @Column(name = "checkbox_nu_prescriptie")
    private short checkboxNuPrescriptie;
    @Basic(optional = false)
    @Column(name = "checkbox_da_cm")
    private short checkboxDaCm;
    @Basic(optional = false)
    @Column(name = "checkbox_nu_cm")
    private short checkboxNuCm;
    @Basic(optional = false)
    @Column(name = "checkbox_nu_este_necesar_cm")
    private short checkboxNuEsteNecesarCm;
    @Basic(optional = false)
    @Column(name = "checkbox_da_ingrijiri")
    private short checkboxDaIngrijiri;
    @Basic(optional = false)
    @Column(name = "checkbox_nu_ingrijiri")
    private short checkboxNuIngrijiri;
    @Basic(optional = false)
    @Column(name = "checkbox_da_dm")
    private short checkboxDaDm;
    @Basic(optional = false)
    @Column(name = "checkbox_nu_dm")
    private short checkboxNuDm;
    @Column(name = "inaltime")
    private String inaltime;
    @Column(name = "greutate")
    private String greutate;
    @Column(name = "tensiune")
    private String tensiune;
    @Column(name = "aritmie_ventriculara")
    private String aritmieVentriculara;

    @JoinColumn(name = "id_person", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Person idPerson;
    
    @JoinColumn(name = "id_consultatie", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Consultatii idConsultatie;
    
    @JoinColumn(name = "id_physicians", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Physicians idPhysicians;
    
    @JoinColumn(name = "id_specialities", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Specialities idSpecialities;

    @JoinColumn(name = "id_icd10_cunoscut", referencedColumnName = "id")
    @ManyToOne()
    private Icd10 idIcd10Cunoscut;

    @JoinColumn(name = "id_icd10_prezumptiv", referencedColumnName = "id")
    @ManyToOne()
    private Icd10 idIcd10Prezumptiv;
    
    @JoinColumn(name = "id_scrisoare_medicala_motive_prezentare", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private ScrisoareMedicalaMotivePrezentare idScrisoareMedicalaMotivePrezentare;

    public void setIdPerson(Person idPerson) {
        this.idPerson = idPerson;
    }

    public void setIdConsultatie(Consultatii idConsultatie) {
        this.idConsultatie = idConsultatie;
    }

    public void setIdPhysicians(Physicians idPhysicians) {
        this.idPhysicians = idPhysicians;
    }

    public void setIdSpecialities(Specialities idSpecialities) {
        this.idSpecialities = idSpecialities;
    }

    public void setIdIcd10Cunoscut(Icd10 idIcd10Cunoscut) {
        this.idIcd10Cunoscut = idIcd10Cunoscut;
    }

    public void setIdIcd10Prezumptiv(Icd10 idIcd10Prezumptiv) {
        this.idIcd10Prezumptiv = idIcd10Prezumptiv;
    }

    public void setIdScrisoareMedicalaMotivePrezentare(ScrisoareMedicalaMotivePrezentare idScrisoareMedicalaMotivePrezentare) {
        this.idScrisoareMedicalaMotivePrezentare = idScrisoareMedicalaMotivePrezentare;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Person getIdPerson() {
        return idPerson;
    }

    public Consultatii getIdConsultatie() {
        return idConsultatie;
    }

    public Physicians getIdPhysicians() {
        return idPhysicians;
    }

    public Specialities getIdSpecialities() {
        return idSpecialities;
    }

    public Icd10 getIdIcd10Cunoscut() {
        return idIcd10Cunoscut;
    }

    public Icd10 getIdIcd10Prezumptiv() {
        return idIcd10Prezumptiv;
    }

    public ScrisoareMedicalaMotivePrezentare getIdScrisoareMedicalaMotivePrezentare() {
        return idScrisoareMedicalaMotivePrezentare;
    }

                            
    
    public ScrisoareMedicala() {
    }

    public ScrisoareMedicala(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getNrRegistruConsultatii() {
        return nrRegistruConsultatii;
    }

    public void setNrRegistruConsultatii(String nrRegistruConsultatii) {
        this.nrRegistruConsultatii = nrRegistruConsultatii;
    }

    public String getDiagnostic() {
        return diagnostic;
    }

    public void setDiagnostic(String diagnostic) {
        this.diagnostic = diagnostic;
    }

    public String getAnamneza() {
        return anamneza;
    }

    public void setAnamneza(String anamneza) {
        this.anamneza = anamneza;
    }

    public String getAnamnezaFactoriRisc() {
        return anamnezaFactoriRisc;
    }

    public void setAnamnezaFactoriRisc(String anamnezaFactoriRisc) {
        this.anamnezaFactoriRisc = anamnezaFactoriRisc;
    }

    public String getExamenClinicGeneral() {
        return examenClinicGeneral;
    }

    public void setExamenClinicGeneral(String examenClinicGeneral) {
        this.examenClinicGeneral = examenClinicGeneral;
    }

    public String getExamenClinicLocal() {
        return examenClinicLocal;
    }

    public void setExamenClinicLocal(String examenClinicLocal) {
        this.examenClinicLocal = examenClinicLocal;
    }

    public String getAnalizaLaboratorNormale() {
        return analizaLaboratorNormale;
    }

    public void setAnalizaLaboratorNormale(String analizaLaboratorNormale) {
        this.analizaLaboratorNormale = analizaLaboratorNormale;
    }

    public String getAnalizaLaboratorPatologice() {
        return analizaLaboratorPatologice;
    }

    public void setAnalizaLaboratorPatologice(String analizaLaboratorPatologice) {
        this.analizaLaboratorPatologice = analizaLaboratorPatologice;
    }

    public String getExameneParacliniceEkg() {
        return exameneParacliniceEkg;
    }

    public void setExameneParacliniceEkg(String exameneParacliniceEkg) {
        this.exameneParacliniceEkg = exameneParacliniceEkg;
    }

    public String getExameneParacliniceEco() {
        return exameneParacliniceEco;
    }

    public void setExameneParacliniceEco(String exameneParacliniceEco) {
        this.exameneParacliniceEco = exameneParacliniceEco;
    }

    public String getExameneParacliniceRx() {
        return exameneParacliniceRx;
    }

    public void setExameneParacliniceRx(String exameneParacliniceRx) {
        this.exameneParacliniceRx = exameneParacliniceRx;
    }

    public String getExameneParacliniceAltele() {
        return exameneParacliniceAltele;
    }

    public void setExameneParacliniceAltele(String exameneParacliniceAltele) {
        this.exameneParacliniceAltele = exameneParacliniceAltele;
    }

    public String getTratamentEfectuat() {
        return tratamentEfectuat;
    }

    public void setTratamentEfectuat(String tratamentEfectuat) {
        this.tratamentEfectuat = tratamentEfectuat;
    }

    public String getAlteInformatii() {
        return alteInformatii;
    }

    public void setAlteInformatii(String alteInformatii) {
        this.alteInformatii = alteInformatii;
    }

    public String getRevinePentruInternareIn() {
        return revinePentruInternareIn;
    }

    public void setRevinePentruInternareIn(String revinePentruInternareIn) {
        this.revinePentruInternareIn = revinePentruInternareIn;
    }

    public String getTratamentRecomandat() {
        return tratamentRecomandat;
    }

    public void setTratamentRecomandat(String tratamentRecomandat) {
        this.tratamentRecomandat = tratamentRecomandat;
    }

    public short getCheckboxDaRevineInternare() {
        return checkboxDaRevineInternare;
    }

    public void setCheckboxDaRevineInternare(short checkboxDaRevineInternare) {
        this.checkboxDaRevineInternare = checkboxDaRevineInternare;
    }

    public short getCheckboxNuRevineInternare() {
        return checkboxNuRevineInternare;
    }

    public void setCheckboxNuRevineInternare(short checkboxNuRevineInternare) {
        this.checkboxNuRevineInternare = checkboxNuRevineInternare;
    }

    public short getCheckboxDaPrescriptie() {
        return checkboxDaPrescriptie;
    }

    public void setCheckboxDaPrescriptie(short checkboxDaPrescriptie) {
        this.checkboxDaPrescriptie = checkboxDaPrescriptie;
    }

    public short getCheckboxNuEsteNecesaraPrescriptie() {
        return checkboxNuEsteNecesaraPrescriptie;
    }

    public void setCheckboxNuEsteNecesaraPrescriptie(short checkboxNuEsteNecesaraPrescriptie) {
        this.checkboxNuEsteNecesaraPrescriptie = checkboxNuEsteNecesaraPrescriptie;
    }

    public short getCheckboxNuPrescriptie() {
        return checkboxNuPrescriptie;
    }

    public void setCheckboxNuPrescriptie(short checkboxNuPrescriptie) {
        this.checkboxNuPrescriptie = checkboxNuPrescriptie;
    }

    public short getCheckboxDaCm() {
        return checkboxDaCm;
    }

    public void setCheckboxDaCm(short checkboxDaCm) {
        this.checkboxDaCm = checkboxDaCm;
    }

    public short getCheckboxNuCm() {
        return checkboxNuCm;
    }

    public void setCheckboxNuCm(short checkboxNuCm) {
        this.checkboxNuCm = checkboxNuCm;
    }

    public short getCheckboxNuEsteNecesarCm() {
        return checkboxNuEsteNecesarCm;
    }

    public void setCheckboxNuEsteNecesarCm(short checkboxNuEsteNecesarCm) {
        this.checkboxNuEsteNecesarCm = checkboxNuEsteNecesarCm;
    }

    public short getCheckboxDaIngrijiri() {
        return checkboxDaIngrijiri;
    }

    public void setCheckboxDaIngrijiri(short checkboxDaIngrijiri) {
        this.checkboxDaIngrijiri = checkboxDaIngrijiri;
    }

    public short getCheckboxNuIngrijiri() {
        return checkboxNuIngrijiri;
    }

    public void setCheckboxNuIngrijiri(short checkboxNuIngrijiri) {
        this.checkboxNuIngrijiri = checkboxNuIngrijiri;
    }

    public short getCheckboxDaDm() {
        return checkboxDaDm;
    }

    public void setCheckboxDaDm(short checkboxDaDm) {
        this.checkboxDaDm = checkboxDaDm;
    }

    public short getCheckboxNuDm() {
        return checkboxNuDm;
    }

    public void setCheckboxNuDm(short checkboxNuDm) {
        this.checkboxNuDm = checkboxNuDm;
    }

    public String getInaltime() {
        return inaltime;
    }

    public void setInaltime(String inaltime) {
        this.inaltime = inaltime;
    }

    public String getGreutate() {
        return greutate;
    }

    public void setGreutate(String greutate) {
        this.greutate = greutate;
    }

    public String getTensiune() {
        return tensiune;
    }

    public void setTensiune(String tensiune) {
        this.tensiune = tensiune;
    }

    public String getAritmieVentriculara() {
        return aritmieVentriculara;
    }

    public void setAritmieVentriculara(String aritmieVentriculara) {
        this.aritmieVentriculara = aritmieVentriculara;
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
        if (!(object instanceof ScrisoareMedicala)) {
            return false;
        }
        ScrisoareMedicala other = (ScrisoareMedicala) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.caido.appointments.entity.ScrisoareMedicala[ id=" + id + " ]";
    }
    
}
