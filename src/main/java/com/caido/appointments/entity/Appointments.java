package com.caido.appointments.entity;

import java.io.Serializable;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@Table(name = "appointments")
public class Appointments implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "id_city", nullable = false)
    private Integer idCity;

    public Integer getIdCity() {
        return idCity;
    }

    public void setIdCity(Integer idCity) {
        this.idCity = idCity;
    }

    @Column(name = "id_department", nullable = false)
    private Integer idDepartment;

    public Integer getIdDepartment() {
        return idDepartment;
    }

    public void setIdDepartment(Integer idDepartment) {
        this.idDepartment = idDepartment;
    }
    
    @Basic(optional = false)
    @Column(name = "ora_programare")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime oraProgramare;
    
    @Basic(optional = false)
    @Column(name = "minute_estimate")
    private int minuteEstimate;
    
    @Column(name = "ora_confirmare")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime oraConfirmare;
    
    @Column(name = "comments")
    private String comments;
    
    @JoinColumn(name = "id_appointments_types", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private AppointmentsTypes idAppointmentsTypes;
    
    @JoinColumn(name = "id_person", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Person idPerson;
    
    @JoinColumn(name = "id_personnel", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Personnel idPersonnel;
    
    @JoinColumn(name = "id_physicians", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Physicians idPhysicians;
    
    @JoinColumn(name = "id_speciality", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Specialities idSpeciality;

    @ManyToMany
    @JoinTable(name = "appointments_lab_tests_groups",
               joinColumns = @JoinColumn(name = "id_appointments"),
               inverseJoinColumns = @JoinColumn(name = "id_lab_tests_groups"))
    private Collection<LabTestsGroups> labTestsGroups;

    public Collection<LabTestsGroups> getLabTestsGroups() {
        return labTestsGroups;
    }

    public void setLabTestsGroups(Collection<LabTestsGroups> labTestsGroups) {
        this.labTestsGroups = labTestsGroups;
    }
//    @JoinColumn(name = "id_lab_tests_groups", referencedColumnName = "id")
//    @ManyToOne(optional = false)
//    private LabTestsGroups idLabTestsGroups;

    public Appointments() {
    }

    public Appointments(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getOraProgramare() {
        return oraProgramare;
    }

    public void setOraProgramare(LocalDateTime oraProgramare) {
        this.oraProgramare = oraProgramare;
    }

    public int getMinuteEstimate() {
        return minuteEstimate;
    }

    public void setMinuteEstimate(int minuteEstimate) {
        this.minuteEstimate = minuteEstimate;
    }

    public LocalDateTime getOraConfirmare() {
        return oraConfirmare;
    }

    public void setOraConfirmare(LocalDateTime oraConfirmare) {
        this.oraConfirmare = oraConfirmare;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public AppointmentsTypes getIdAppointmentsTypes() {
        return idAppointmentsTypes;
    }

    public void setIdAppointmentsTypes(AppointmentsTypes idAppointmentsTypes) {
        this.idAppointmentsTypes = idAppointmentsTypes;
    }

    public Person getIdPerson() {
        return idPerson;
    }

    public void setIdPerson(Person idPerson) {
        this.idPerson = idPerson;
    }

    public Personnel getIdPersonnel() {
        return idPersonnel;
    }

    public void setIdPersonnel(Personnel idPersonnel) {
        this.idPersonnel = idPersonnel;
    }

    public Physicians getIdPhysicians() {
        return idPhysicians;
    }

    public void setIdPhysicians(Physicians idPhysicians) {
        this.idPhysicians = idPhysicians;
    }

    public Specialities getIdSpeciality() {
        return idSpeciality;
    }

    public void setIdSpeciality(Specialities idSpeciality) {
        this.idSpeciality = idSpeciality;
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
        if (!(object instanceof Appointments)) {
            return false;
        }
        Appointments other = (Appointments) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));
    }

    @Override
    public String toString() {
        return "Appointments{" + "id=" + id + ", oraProgramare=" + oraProgramare + ", minuteEstimate=" + minuteEstimate + ", oraConfirmare=" + oraConfirmare + ", comments=" + comments + ", idAppointmentsTypes=" + idAppointmentsTypes + ", idPerson=" + idPerson + ", idPersonnel=" + idPersonnel + ", idPhysicians=" + idPhysicians + ", idSpeciality=" + idSpeciality +'}';
    }
        
}
