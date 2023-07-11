package com.caido.appointments.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "appointments")
@NamedQueries({
    @NamedQuery(name = "Appointments.findAll", query = "SELECT a FROM Appointments a"),
    @NamedQuery(name = "Appointments.findById", query = "SELECT a FROM Appointments a WHERE a.id = :id"),
    @NamedQuery(name = "Appointments.findByOraProgramare", query = "SELECT a FROM Appointments a WHERE a.oraProgramare = :oraProgramare"),
    @NamedQuery(name = "Appointments.findByMinuteEstimate", query = "SELECT a FROM Appointments a WHERE a.minuteEstimate = :minuteEstimate"),
    @NamedQuery(name = "Appointments.findByOraConfirmare", query = "SELECT a FROM Appointments a WHERE a.oraConfirmare = :oraConfirmare"),
    @NamedQuery(name = "Appointments.findByComments", query = "SELECT a FROM Appointments a WHERE a.comments = :comments")})
public class Appointments implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Basic(optional = false)
    @Column(name = "ora_programare")
    @Temporal(TemporalType.TIMESTAMP)
    private Date oraProgramare;
    
    @Basic(optional = false)
    @Column(name = "minute_estimate")
    private int minuteEstimate;
    
    @Column(name = "ora_confirmare")
    @Temporal(TemporalType.TIMESTAMP)
    private Date oraConfirmare;
    
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

    public Appointments() {
    }

    public Appointments(Integer id) {
        this.id = id;
    }

    public Appointments(Integer id, Date oraProgramare, int minuteEstimate) {
        this.id = id;
        this.oraProgramare = oraProgramare;
        this.minuteEstimate = minuteEstimate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getOraProgramare() {
        return oraProgramare;
    }

    public void setOraProgramare(Date oraProgramare) {
        this.oraProgramare = oraProgramare;
    }

    public int getMinuteEstimate() {
        return minuteEstimate;
    }

    public void setMinuteEstimate(int minuteEstimate) {
        this.minuteEstimate = minuteEstimate;
    }

    public Date getOraConfirmare() {
        return oraConfirmare;
    }

    public void setOraConfirmare(Date oraConfirmare) {
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
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.caido.appointments.entity.Appointments[ id=" + id + " ]";
    }
    
}
