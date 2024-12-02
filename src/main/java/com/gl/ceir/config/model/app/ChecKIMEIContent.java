package com.gl.ceir.config.model.app;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation. constraints.NotBlank;
import jakarta.validation. constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Audited
@AuditTable(value = "label_mul_lingual_text_aud", schema = "aud")
@Table(name = "label_mul_lingual_text")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
public class ChecKIMEIContent {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@CreationTimestamp
	@JsonFormat(pattern="yyyy-MM-dd HH:mm")
	private LocalDateTime createdOn;

	@Column(length = 30)
	private String featureName;
	
	@NotNull
	@NotBlank
	private String label;
	
	@NotNull
	@NotBlank
	@Column(name = "english_name") 
	private String englishName;
	
	@NotNull
	@NotBlank
	@Column(name = "khmer_name") 
	private String khmerName;
	
	
	@Transient
	private long userId;
	@Transient
	private String userName;
	@Transient
	private long userTypeId;
	@Transient
	private long featureId;
	@Transient
	private String roleType;
	
	@Transient
	private String publicIp;
	
	@Transient
	private String browser;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}

	public String getFeatureName() {
		return featureName;
	}

	public void setFeatureName(String featureName) {
		this.featureName = featureName;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	
	public String getEnglishName() {
		return englishName;
	}

	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}

	public String getKhmerName() {
		return khmerName;
	}

	public void setKhmerName(String khmerName) {
		this.khmerName = khmerName;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public long getUserTypeId() {
		return userTypeId;
	}

	public void setUserTypeId(long userTypeId) {
		this.userTypeId = userTypeId;
	}

	public long getFeatureId() {
		return featureId;
	}

	public void setFeatureId(long featureId) {
		this.featureId = featureId;
	}

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "ChecKIMEIContent [id=" + id + ", createdOn=" + createdOn + ", featureName=" + featureName + ", label="
				+ label + ", englishName=" + englishName + ", khmerName=" + khmerName + ", userId=" + userId
				+ ", userName=" + userName + ", userTypeId=" + userTypeId + ", featureId=" + featureId + ", roleType="
				+ roleType + ", publicIp=" + publicIp + ", browser=" + browser + "]";
	}



}
