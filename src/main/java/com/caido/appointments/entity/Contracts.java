package com.caido.appointments.entity;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDate;


@Entity
@Table(name = "contracts")
@NamedQueries({
    @NamedQuery(name = "Contracts.findAll", query = "SELECT c FROM Contracts c")})
public class Contracts implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "contract_no")
    private String contractNo;
    @Basic(optional = false)
    @Column(name = "issue_date")
    @Temporal(TemporalType.DATE)
    private LocalDate issueDate;
    @Basic(optional = false)
    @Column(name = "valid_from")
    @Temporal(TemporalType.DATE)
    private LocalDate validFrom;
    @Basic(optional = false)
    @Column(name = "valid_to")
    @Temporal(TemporalType.DATE)
    private LocalDate validTo;
    @Column(name = "cmzdm")
    private String cmzdm;
    @Column(name = "provider_type_code")
    private String providerTypeCode;
    @Column(name = "perm_center_code")
    private String permCenterCode;
    @Column(name = "perm_center_name")
    private String permCenterName;
    @Column(name = "numeric_reporting")
    private String numericReporting;
    @Column(name = "importid")
    private Integer importid;
    @Column(name = "discount")
    private BigInteger discount;
    @JoinColumn(name = "org_unit_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private OrgUnits orgUnitId;
    
    @Column(name = "contract_type")
    private Integer contractType;

    public void setContractType(Integer contractType) {
        this.contractType = contractType;
    }

    public Integer getContractType() {
        return contractType;
    }

    public Contracts() {
    }

    public Contracts(Integer id) {
        this.id = id;
    }

    public Contracts(Integer id, String contractNo, LocalDate issueDate, LocalDate validFrom, LocalDate validTo) {
        this.id = id;
        this.contractNo = contractNo;
        this.issueDate = issueDate;
        this.validFrom = validFrom;
        this.validTo = validTo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }

    public LocalDate getValidTo() {
        return validTo;
    }

    public void setValidTo(LocalDate validTo) {
        this.validTo = validTo;
    }

    public String getCmzdm() {
        return cmzdm;
    }

    public void setCmzdm(String cmzdm) {
        this.cmzdm = cmzdm;
    }

    public String getProviderTypeCode() {
        return providerTypeCode;
    }

    public void setProviderTypeCode(String providerTypeCode) {
        this.providerTypeCode = providerTypeCode;
    }

    public String getPermCenterCode() {
        return permCenterCode;
    }

    public void setPermCenterCode(String permCenterCode) {
        this.permCenterCode = permCenterCode;
    }

    public String getPermCenterName() {
        return permCenterName;
    }

    public void setPermCenterName(String permCenterName) {
        this.permCenterName = permCenterName;
    }

    public String getNumericReporting() {
        return numericReporting;
    }

    public void setNumericReporting(String numericReporting) {
        this.numericReporting = numericReporting;
    }

    public Integer getImportid() {
        return importid;
    }

    public void setImportid(Integer importid) {
        this.importid = importid;
    }

    public BigInteger getDiscount() {
        return discount;
    }

    public void setDiscount(BigInteger discount) {
        this.discount = discount;
    }

    public OrgUnits getOrgUnitId() {
        return orgUnitId;
    }

    public void setOrgUnitId(OrgUnits orgUnitId) {
        this.orgUnitId = orgUnitId;
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
        if (!(object instanceof Contracts)) {
            return false;
        }
        Contracts other = (Contracts) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.caido.appointments.entity.Contracts[ id=" + id + " ]";
    }
    
}
