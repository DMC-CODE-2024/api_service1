package com.gl.ceir.config.model.app;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class GreylistDb implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@CreationTimestamp
	@JsonFormat(pattern="dd-MM-yyyy HH:mm")
	@Column(nullable = false, updatable = false)
	private LocalDateTime createdOn;
	
	@JsonIgnore
	@UpdateTimestamp
	@JsonFormat(pattern="dd-MM-yyyy HH:mm")
	private LocalDateTime modifiedOn;
	private String imei;
	@Column(length = 15)
	private String roleType;
	private String userId;
	@Column(length = 20)
	private String txnId;
	private String deviceNumber;
	private String deviceType;
	private String deviceAction;
	private String deviceStatus;
	private String DeviceLaunchDate;
	private String multipleSimStatus;
//	private String  deviceId;
	private String imeiEsnMeid;
	
	//@Type(type="date")
	@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
	private Date expiryDate;
	
	@Column(name = "mode2")
	private String mode;
	private String requestType;
	private String userType;
	private String complainType;
	
	@Transient
	private String complainTypeInterp;
	private String modeType;
	
	private String deviceIdType;
	
	public String getDeviceIdType() {
		return deviceIdType;
	}
	public void setDeviceIdType(String deviceIdType) {
		this.deviceIdType = deviceIdType;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTxnId() {
		return txnId;
	}
	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}
	public String getRoleType() {
		return roleType;
	}
	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getDeviceNumber() {
		return deviceNumber;
	}
	public void setDeviceNumber(String deviceNumber) {
		this.deviceNumber = deviceNumber;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public String getDeviceAction() {
		return deviceAction;
	}
	public void setDeviceAction(String deviceAction) {
		this.deviceAction = deviceAction;
	}
	public String getDeviceStatus() {
		return deviceStatus;
	}
	public void setDeviceStatus(String deviceStatus) {
		this.deviceStatus = deviceStatus;
	}
	public String getDeviceLaunchDate() {
		return DeviceLaunchDate;
	}
	public void setDeviceLaunchDate(String deviceLaunchDate) {
		DeviceLaunchDate = deviceLaunchDate;
	}
	public String getMultipleSimStatus() {
		return multipleSimStatus;
	}
	public void setMultipleSimStatus(String multipleSimStatus) {
		this.multipleSimStatus = multipleSimStatus;
	}
//	public String getDeviceId() {
//		return deviceId;
//	}
//	public void setDeviceId(String deviceId) {
//		this.deviceId = deviceId;
//	}
	public String getImeiEsnMeid() {
		return imeiEsnMeid;
	}
	public void setImeiEsnMeid(String imeiEsnMeid) {
		this.imeiEsnMeid = imeiEsnMeid;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	
	
	public Date getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String getRequestType() {
		return requestType;
	}
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	
	public String getComplainType() {
		return complainType;
	}
	public void setComplainType(String complainType) {
		this.complainType = complainType;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}
	public void setModifiedOn(LocalDateTime modifiedOn) {
		this.modifiedOn = modifiedOn;
	}
	public String getModeType() {
		return modeType;
	}
	public void setModeType(String modeType) {
		this.modeType = modeType;
	}
	
	public LocalDateTime getCreatedOn() {
		return createdOn;
	}
	public LocalDateTime getModifiedOn() {
		return modifiedOn;
	}
	
	public String getComplainTypeInterp() {
		return complainTypeInterp;
	}
	public void setComplainTypeInterp(String complainTypeInterp) {
		this.complainTypeInterp = complainTypeInterp;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GreylistDb [id=");
		builder.append(id);
		builder.append(", createdOn=");
		builder.append(createdOn);
		builder.append(", modifiedOn=");
		builder.append(modifiedOn);
		builder.append(", imei=");
		builder.append(imei);
		builder.append(", roleType=");
		builder.append(roleType);
		builder.append(", userId=");
		builder.append(userId);
		builder.append(", txnId=");
		builder.append(txnId);
		builder.append(", deviceNumber=");
		builder.append(deviceNumber);
		builder.append(", deviceType=");
		builder.append(deviceType);
		builder.append(", deviceAction=");
		builder.append(deviceAction);
		builder.append(", deviceStatus=");
		builder.append(deviceStatus);
		builder.append(", DeviceLaunchDate=");
		builder.append(DeviceLaunchDate);
		builder.append(", multipleSimStatus=");
		builder.append(multipleSimStatus);
		builder.append(", imeiEsnMeid=");
		builder.append(imeiEsnMeid);
		builder.append(", expiryDate=");
		builder.append(expiryDate);
		builder.append(", mode=");
		builder.append(mode);
		builder.append(", requestType=");
		builder.append(requestType);
		builder.append(", userType=");
		builder.append(userType);
		builder.append(", complainType=");
		builder.append(complainType);
		builder.append(", modeType=");
		builder.append(modeType);
		builder.append(", complainTypeInterp=");
		builder.append(complainTypeInterp);
		builder.append("]");
		return builder.toString();
	}

}
