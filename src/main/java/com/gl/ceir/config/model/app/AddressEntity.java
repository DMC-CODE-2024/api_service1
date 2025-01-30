package com.gl.ceir.config.model.app;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.gl.ceir.config.feature.addressmanagement.AuditTrailModel;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import java.time.LocalDateTime;

@Entity
@Audited
@AuditTable(value = "address_list_mgmt_aud", schema = "aud")
@Table(name = "address_list_mgmt")
public class AddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "created_on")
    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime createdOn;

    @Column(name = "modified_on")
    @UpdateTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime modifiedOn;

    @Column(name = "province")
    private String province;

    @Column(name = "district")
    private String district;

    @Column(name = "commune")
    private String commune;

    @Column(name = "province_km")
    private String provinceKm;

    @Column(name = "district_km")
    private String districtKm;

    @Column(name = "commune_km")
    private String communeKm;

    @Transient
    private String language;
    @Transient
    @JsonProperty(value = "auditTrailModel", access = JsonProperty.Access.WRITE_ONLY)
    private AuditTrailModel auditTrailModel;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getProvinceKm() {
        return provinceKm;
    }

    public AddressEntity setProvinceKm(String provinceKm) {
        this.provinceKm = provinceKm;
        return this;
    }

    public String getDistrictKm() {
        return districtKm;
    }

    public AddressEntity setDistrictKm(String districtKm) {
        this.districtKm = districtKm;
        return this;
    }

    public String getCommuneKm() {
        return communeKm;
    }

    public AddressEntity setCommuneKm(String communeKm) {
        this.communeKm = communeKm;
        return this;
    }

    public AuditTrailModel getAuditTrailModel() {
        return auditTrailModel;
    }

    public AddressEntity setAuditTrailModel(AuditTrailModel auditTrailModel) {
        this.auditTrailModel = auditTrailModel;
        return this;
    }

    public Long getId() {
        return id;
    }

    public AddressEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public AddressEntity setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public LocalDateTime getModifiedOn() {
        return modifiedOn;
    }

    public AddressEntity setModifiedOn(LocalDateTime modifiedOn) {
        this.modifiedOn = modifiedOn;
        return this;
    }

    public String getProvince() {
        return province;
    }

    public AddressEntity setProvince(String province) {
        this.province = province;
        return this;
    }

    public String getDistrict() {
        return district;
    }

    public AddressEntity setDistrict(String district) {
        this.district = district;
        return this;
    }

    public String getCommune() {
        return commune;
    }

    public AddressEntity setCommune(String commune) {
        this.commune = commune;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AddressEntity{");
        sb.append("id=").append(id);
        sb.append(", createdOn=").append(createdOn);
        sb.append(", modifiedOn=").append(modifiedOn);
        sb.append(", province='").append(province).append('\'');
        sb.append(", district='").append(district).append('\'');
        sb.append(", commune='").append(commune).append('\'');
        sb.append(", provinceKm='").append(provinceKm).append('\'');
        sb.append(", districtKm='").append(districtKm).append('\'');
        sb.append(", communeKm='").append(communeKm).append('\'');
        sb.append(", language='").append(language).append('\'');
        sb.append(", auditTrailModel=").append(auditTrailModel);
        sb.append('}');
        return sb.toString();
    }
}
