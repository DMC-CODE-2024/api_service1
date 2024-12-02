package com.gl.ceir.config.model.app;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import jakarta.validation. constraints.NotEmpty;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.envers.Audited;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Table;

@Entity
@Audited
@Table(name = "sys_param_list_value")
public class SystemConfigListDb implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    @Column(updatable = false)
    private LocalDateTime createdOn;

    @UpdateTimestamp
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private LocalDateTime modifiedOn;


    private String tag;

    @NotEmpty (message="Existing values for given tags should not be null when saving")
    private String value;

    private String interpretation;

    private Integer listOrder;

    @Column(length = 10)
    private String tagId;

    private String description;
    private String displayName;


    private String modifiedBy;

    @Transient
    private String username;

    @Transient
    private Integer userId;

    @Transient
    private String publicIp;

    @Transient
    private String browser;

    @Transient
    private String userName;

    @Transient
    private String userType;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPublicIp() {
        return publicIp;
    }

    public void setPublicIp(String publicIp) {
        this.publicIp = publicIp;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public SystemConfigListDb() {
    }


    public SystemConfigListDb(String tag, String displayName) {
        super();
        this.tag = tag;
        this.displayName = displayName;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getTag() {
        return tag;
    }
    public void setTag(String tag) {
        this.tag = tag;
    }




    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }



    public static long getSerialversionuid() {
        return serialVersionUID;
    }
    public String getTagId() {
        return tagId;
    }
    public void setTagId(String tagId) {
        this.tagId = tagId;
    }
    public Integer getListOrder() {
        return listOrder;
    }
    public void setListOrder(Integer listOrder) {
        this.listOrder = listOrder;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getDisplayName() {
        return displayName;
    }
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public LocalDateTime getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(LocalDateTime modifiedOn) {
        this.modifiedOn = modifiedOn;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }





    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getInterpretation() {
        return interpretation;
    }

    public void setInterpretation(String interpretation) {
        this.interpretation = interpretation;
    }

    @Override
    public String toString() {
        return "SystemConfigListDb [id=" + id + ", createdOn=" + createdOn + ", modifiedOn=" + modifiedOn + ", tag="
                + tag + ", value=" + value + ", interpretation=" + interpretation + ", listOrder=" + listOrder
                + ", tagId=" + tagId + ", description=" + description + ", displayName=" + displayName + ", modifiedBy="
                + modifiedBy + ", username=" + username + ", userId=" + userId + ", publicIp=" + publicIp + ", browser="
                + browser + ", userName=" + userName + ", userType=" + userType + "]";
    }








}