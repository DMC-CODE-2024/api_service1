package com.gl.ceir.config.model.app;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Audited
@AuditTable(value = "lost_device_detail_aud", schema = "aud")
@Table(name = "lost_device_detail")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
public class LostStolenMgmt {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@CreationTimestamp
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDateTime createdOn;
	
	@UpdateTimestamp
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDateTime modified_on;
	
	@Column(name = "imei") 
	private String imei;
	
	@Column(name = "contact_number") 
	private String contactNumber;
	
	@Column(name = "device_brand") 
	private String deviceBrand;
	
	@Column(name = "device_model") 
	private String deviceModel;
	

	@Column(name = "request_id") 
	private String requestId;
	
	@Column(name = "request_type") 
	private String requestType;
	
	@Column(name = "status") 
	private String status;

	@Column(name = "lost_stolen_request_id") 
	private String lostRequestid;




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

	public LocalDateTime getModified_on() {
		return modified_on;
	}

	public void setModified_on(LocalDateTime modified_on) {
		this.modified_on = modified_on;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getDeviceBrand() {
		return deviceBrand;
	}

	public void setDeviceBrand(String deviceBrand) {
		this.deviceBrand = deviceBrand;
	}

	public String getDeviceModel() {
		return deviceModel;
	}

	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLostRequestid() {
		return lostRequestid;
	}

	public void setLostRequestid(String lostRequestid) {
		this.lostRequestid = lostRequestid;
	}


	@Override
	public String toString() {
		return "LostStolenMgmt{" +
				"id=" + id +
				", createdOn=" + createdOn +
				", modified_on=" + modified_on +
				", imei='" + imei + '\'' +
				", contactNumber='" + contactNumber + '\'' +
				", deviceBrand='" + deviceBrand + '\'' +
				", deviceModel='" + deviceModel + '\'' +
				", requestId='" + requestId + '\'' +
				", requestType='" + requestType + '\'' +
				", status='" + status + '\'' +
				", lostRequestid='" + lostRequestid + '\'' +
				'}';
	}
}
