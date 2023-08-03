package com.caido.appointments.entity;

import java.io.Serializable;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 *
 * @author victor
 */
@Entity
@Table(name = "lab_tests_groups")
public class LabTestsGroups implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Column(name = "name")
    private String name;
    
    @Basic(optional = false)
    @Column(name = "code")
    private String code;
    
    @Column(name = "visible")
    private Short visible;
    
    @Column(name = "name2")
    private String name2;
    
    @Column(name = "supergroup")
    private Short supergroup;
    
    
    @Column(name = "minute_estimate")
    private Integer minuteEstimate;
    
    @Basic(optional = false)
    @Column(name = "can_be_scheduled_online")
    private short canBeScheduledOnline;
    
    public LabTestsGroups() {
    }

    public LabTestsGroups(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Short getVisible() {
        return visible;
    }

    public void setVisible(Short visible) {
        this.visible = visible;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public Short getSupergroup() {
        return supergroup;
    }

    public void setSupergroup(Short supergroup) {
        this.supergroup = supergroup;
    }

    public Integer getMinuteEstimate() {
        return minuteEstimate;
    }

    public void setMinuteEstimate(Integer minuteEstimate) {
        this.minuteEstimate = minuteEstimate;
    }

    public short getCanBeScheduledOnline() {
        return canBeScheduledOnline;
    }

    public void setCanBeScheduledOnline(short canBeScheduledOnline) {
        this.canBeScheduledOnline = canBeScheduledOnline;
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
        if (!(object instanceof LabTestsGroups)) {
            return false;
        }
        LabTestsGroups other = (LabTestsGroups) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.caido.appointments.entity.LabTestsGroups[ id=" + id + " ]";
    }
    
}
