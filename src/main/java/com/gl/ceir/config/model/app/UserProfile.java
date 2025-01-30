package com.gl.ceir.config.model.app;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "user_profile")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UserProfile{");
        sb.append("id=").append(id);
        sb.append(", createdOn=").append(createdOn);
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", phoneNo='").append(phoneNo).append('\'');
        sb.append(", commune='").append(commune).append('\'');
        sb.append(", policeStationDb=").append(policeStationDb);
        sb.append(", district='").append(district).append('\'');
        sb.append(", districtDb=").append(districtDb);
        sb.append(", province='").append(province).append('\'');
        sb.append(", provinceDb=").append(provinceDb);
        sb.append(", userGroupMemberships=").append(userGroupMemberships);
        sb.append('}');
        return sb.toString();
    }

    @CreationTimestamp
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime createdOn;

   // @Column(name = "user_id")
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "phone_no")
    private String phoneNo;


    @Column(name = "commune")
    private String commune;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "commune", insertable = false, updatable = false)
    @JsonProperty(value = "policeStationDb", access = JsonProperty.Access.READ_ONLY)
    private PoliceStationDb policeStationDb;


   /* @JoinColumn(name = "commune", referencedColumnName = "commune_id", insertable = false, updatable = false)
    @JsonProperty(value = "policeStationDb", access = JsonProperty.Access.READ_ONLY)
    private PoliceStationDb policeStationDb;*/

    @Column(name = "district")
    private String district;
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "district", insertable = false, updatable = false)
    @JsonProperty(value = "districtDb", access = JsonProperty.Access.READ_ONLY)
    private DistrictDb districtDb;


    @Column(name = "province")
    private String province;
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "province", insertable = false, updatable = false)
    @JsonProperty(value = "provinceDb", access = JsonProperty.Access.READ_ONLY)
    private ProvinceDb provinceDb;

    /*
        @OneToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "user_id",referencedColumnName = "userId")
        private UserGroupMembership userGroupMembership;
    */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userProfile")
    private List<UserGroupMembership> userGroupMemberships;  // Plural form

    public List<UserGroupMembership> getUserGroupMemberships() {
        return userGroupMemberships;
    }

    public void setUserGroupMemberships(List<UserGroupMembership> userGroupMemberships) {
        this.userGroupMemberships = userGroupMemberships;
    }

 /*   public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }*/

    public ProvinceDb getProvinceDb() {
        return provinceDb;
    }

    public void setProvinceDb(ProvinceDb provinceDb) {
        this.provinceDb = provinceDb;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public PoliceStationDb getPoliceStationDb() {
        return policeStationDb;
    }

    public void setPoliceStationDb(PoliceStationDb policeStationDb) {
        this.policeStationDb = policeStationDb;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public String getCommune() {
        return commune;
    }

    public void setCommune(String commune) {
        this.commune = commune;
    }


    public DistrictDb getDistrictDb() {
        return districtDb;
    }

    public void setDistrictDb(DistrictDb districtDb) {
        this.districtDb = districtDb;
    }

}
