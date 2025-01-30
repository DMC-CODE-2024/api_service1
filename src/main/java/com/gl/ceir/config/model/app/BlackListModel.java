package com.gl.ceir.config.model.app;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "black_list")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
public class BlackListModel {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long sno;

	@CreationTimestamp
	@JsonFormat(pattern="dd-MM-yyyy HH:mm")
	private LocalDateTime creationDate;
	
	
	@Column(name = "imei") 
	private String imei;
	
	
	@Column(name = "imsi") 
	private String imsi;
	
	
	@Column(name = "msisdn") 
	private String msisdn;

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
	

	public Long getSno() {
		return sno;
	}


	public void setSno(Long sno) {
		this.sno = sno;
	}


	

	public String getImei() {
		return imei;
	}


	public void setImei(String imei) {
		this.imei = imei;
	}


	public String getImsi() {
		return imsi;
	}


	public void setImsi(String imsi) {
		this.imsi = imsi;
	}


	public String getMsisdn() {
		return msisdn;
	}


	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}


	public LocalDateTime getCreationDate() {
		return creationDate;
	}


	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
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


	@Override
	public String toString() {
		return "BlackListModel [sno=" + sno + ", creationDate=" + creationDate + ", imei=" + imei + ", imsi=" + imsi
				+ ", msisdn=" + msisdn + ", userId=" + userId + ", userName=" + userName + ", userTypeId=" + userTypeId
				+ ", featureId=" + featureId + ", roleType=" + roleType + ", publicIp=" + publicIp + ", browser="
				+ browser + "]";
	}


	
	
}
