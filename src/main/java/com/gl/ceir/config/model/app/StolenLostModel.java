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
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "lost_device_mgmt")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
public class StolenLostModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Transient
	private int statusCode;

	

	@CreationTimestamp
	@JsonFormat(pattern="dd-MM-yyyy HH:mm:ss")
	private LocalDateTime createdOn;
	
	@UpdateTimestamp
	@JsonFormat(pattern="dd-MM-yyyy HH:mm")
	private LocalDateTime modified_on;
	
	@Column(name = "owner_dob") 
	private String ownerDOB;
	
	@Column(name = "contact_number") 
	private String contactNumber;
	

	@Column(name = "imei1") 
	private String imei1;
	
	@Column(name = "imei2") 
	private String imei2;
	
	@Column(name = "imei3") 
	private String imei3;
	
	@Column(name = "imei4") 
	private String imei4;
	
	@Column(name = "device_brand") 
	private String deviceBrand;
	
	@Column(name = "device_model") 
	private String deviceModel;
	
	@Column(name = "device_purchase_invoice_url") 
	private String devicePurchaseInvoiceUrl;
	
	@Column(name = "device_lost_date_time") 
	private String deviceLostDdateTime;
	
	
	
	
	
	@Column(name = "device_owner_name") 
	private String deviceOwnerName;
	
	@Column(name = "device_owner_email") 
	private String deviceOwnerEmail;
	
	@Column(name = "device_owner_address") 
	private String deviceOwnerAddress;
	
	
	
	@Column(name = "device_owner_national_id_url") 
	private String deviceOwnerNationalIdUrl;
	
	@Column(name = "device_owner_national_id") 
	private String deviceOwnerNationalID;
	
	@Column(name = "device_owner_nationality") 
	private String deviceOwnerNationality;
	
	@Column(name = "contact_number_for_otp") 
	private String contactNumberForOtp;
	
	@Column(name = "otp") 
	private String otp;
	
	@Column(name = "fir_copy_url") 
	private String firCopyUrl;
	
	@Column(name = "remark")
	private String remarks;
	
	@Column(name = "status") 
	private String status;
	
	@Column(name = "created_by") 
	private String createdBy;
	
	@Column(name = "request_type") 
	private String requestType;
	
	@Column(name = "request_id") 
	private String requestId;
	

	@Column(name = "request_mode") 
	private String requestMode;
	
	@Column(name = "file_name") 
	private String fileName;
	
	@Column(name = "file_record_count") 
	private String fileRecordCount;
	
	@Column(name = "mobile_invoice_bill") 
	private String mobileInvoiceBill;
	
	
	
	@Column(name = "device_owner_address2") 
	private String deviceOwnerAddress2;
	
	@Column(name = "recovery_reason") 
	private String recoveryReason;

	@Column(name = "device_lost_province_city") 
	private String province;

	@Column(name = "device_lost_district") 
	private String district;
	
	@Column(name = "device_lost_commune") 
	private String commune;
	
	@Column(name = "police_station") 
	private String policeStation;
	
	
	
	
	@Column(name = "owner_passport_number") 
	private String passportNumber;
	
	@Column(name = "email_for_otp") 
	private String otpEmail;
	
	@Column(name = "category") 
	private String category;
	
	@Column(name = "user_status") 
	private String userStatus;
	
	@Column(name = "language") 
	private String language;
	
	@Column(name = "lost_id") 
	private String lostId;
	
	@Column(name = "serial_number") 
	private String serialNumber;

	@Column(name = "incident_detail")
	private String incidentDetail;

	@Column(name = "device_type")
	private String deviceType;

	@Column(name = "otp_retry_count")
	private int otpRetryCount;

	@Column(name = "other_document")
	private String otherDocument;
	
	
	@Transient 
	private String oldRequestId;

	@Transient 
	private String browser;
	
	@Transient 
	private String publicIp;

	@Transient
	private long  featureId;

	@Transient
	private long  userId;

	@Transient
	private String userName;

	@Transient
	private String userType;





	@Transient 
	private String userAgent;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
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

	

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getImei1() {
		return imei1;
	}

	public void setImei1(String imei1) {
		this.imei1 = imei1;
	}

	public String getImei2() {
		return imei2;
	}

	public void setImei2(String imei2) {
		this.imei2 = imei2;
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

	public String getDevicePurchaseInvoiceUrl() {
		return devicePurchaseInvoiceUrl;
	}

	public void setDevicePurchaseInvoiceUrl(String devicePurchaseInvoiceUrl) {
		this.devicePurchaseInvoiceUrl = devicePurchaseInvoiceUrl;
	}

	public String getDeviceLostDdateTime() {
		return deviceLostDdateTime;
	}

	public void setDeviceLostDdateTime(String deviceLostDdateTime) {
		this.deviceLostDdateTime = deviceLostDdateTime;
	}


	public String getDeviceOwnerName() {
		return deviceOwnerName;
	}

	public void setDeviceOwnerName(String deviceOwnerName) {
		this.deviceOwnerName = deviceOwnerName;
	}

	public String getDeviceOwnerEmail() {
		return deviceOwnerEmail;
	}

	public void setDeviceOwnerEmail(String deviceOwnerEmail) {
		this.deviceOwnerEmail = deviceOwnerEmail;
	}

	public String getDeviceOwnerAddress() {
		return deviceOwnerAddress;
	}

	public void setDeviceOwnerAddress(String deviceOwnerAddress) {
		this.deviceOwnerAddress = deviceOwnerAddress;
	}

	

	public String getDeviceOwnerNationalIdUrl() {
		return deviceOwnerNationalIdUrl;
	}

	public void setDeviceOwnerNationalIdUrl(String deviceOwnerNationalIdUrl) {
		this.deviceOwnerNationalIdUrl = deviceOwnerNationalIdUrl;
	}

	public String getDeviceOwnerNationality() {
		return deviceOwnerNationality;
	}

	public void setDeviceOwnerNationality(String deviceOwnerNationality) {
		this.deviceOwnerNationality = deviceOwnerNationality;
	}

	public String getContactNumberForOtp() {
		return contactNumberForOtp;
	}

	public void setContactNumberForOtp(String contactNumberForOtp) {
		this.contactNumberForOtp = contactNumberForOtp;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public String getFirCopyUrl() {
		return firCopyUrl;
	}

	public void setFirCopyUrl(String firCopyUrl) {
		this.firCopyUrl = firCopyUrl;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getRequestMode() {
		return requestMode;
	}

	public void setRequestMode(String requestMode) {
		this.requestMode = requestMode;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileRecordCount() {
		return fileRecordCount;
	}

	public void setFileRecordCount(String fileRecordCount) {
		this.fileRecordCount = fileRecordCount;
	}

	public String getMobileInvoiceBill() {
		return mobileInvoiceBill;
	}

	public void setMobileInvoiceBill(String mobileInvoiceBill) {
		this.mobileInvoiceBill = mobileInvoiceBill;
	}

	

	public String getDeviceOwnerAddress2() {
		return deviceOwnerAddress2;
	}

	public void setDeviceOwnerAddress2(String deviceOwnerAddress2) {
		this.deviceOwnerAddress2 = deviceOwnerAddress2;
	}

	

	public String getRecoveryReason() {
		return recoveryReason;
	}

	public void setRecoveryReason(String recoveryReason) {
		this.recoveryReason = recoveryReason;
	}

	public String getDeviceOwnerNationalID() {
		return deviceOwnerNationalID;
	}

	public void setDeviceOwnerNationalID(String deviceOwnerNationalID) {
		this.deviceOwnerNationalID = deviceOwnerNationalID;
	}

	public String getOldRequestId() {
		return oldRequestId;
	}

	public void setOldRequestId(String oldRequestId) {
		this.oldRequestId = oldRequestId;
	}



	public String getImei3() {
		return imei3;
	}

	public void setImei3(String imei3) {
		this.imei3 = imei3;
	}

	public String getImei4() {
		return imei4;
	}

	public void setImei4(String imei4) {
		this.imei4 = imei4;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getCommune() {
		return commune;
	}

	public void setCommune(String commune) {
		this.commune = commune;
	}

	public String getPoliceStation() {
		return policeStation;
	}

	public void setPoliceStation(String policeStation) {
		this.policeStation = policeStation;
	}

	public String getPassportNumber() {
		return passportNumber;
	}

	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}



	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getOwnerDOB() {
		return ownerDOB;
	}

	public void setOwnerDOB(String ownerDOB) {
		this.ownerDOB = ownerDOB;
	}

	public String getOtpEmail() {
		return otpEmail;
	}

	public void setOtpEmail(String otpEmail) {
		this.otpEmail = otpEmail;
	}

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public String getPublicIp() {
		return publicIp;
	}

	public void setPublicIp(String publicIp) {
		this.publicIp = publicIp;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getLostId() {
		return lostId;
	}

	public void setLostId(String lostId) {
		this.lostId = lostId;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getIncidentDetail() {
		return incidentDetail;
	}

	public void setIncidentDetail(String incidentDetail) {
		this.incidentDetail = incidentDetail;
	}

	public int getOtpRetryCount() {
		return otpRetryCount;
	}

	public void setOtpRetryCount(int otpRetryCount) {
		this.otpRetryCount = otpRetryCount;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public long getFeatureId() {
		return featureId;
	}

	public void setFeatureId(long featureId) {
		this.featureId = featureId;
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

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getOtherDocument() {
		return otherDocument;
	}

	public void setOtherDocument(String otherDocument) {
		this.otherDocument = otherDocument;
	}

	@Override
	public String toString() {
		return "StolenLostModel{" +
				"id=" + id +
				", statusCode=" + statusCode +
				", createdOn=" + createdOn +
				", modified_on=" + modified_on +
				", ownerDOB='" + ownerDOB + '\'' +
				", contactNumber='" + contactNumber + '\'' +
				", imei1='" + imei1 + '\'' +
				", imei2='" + imei2 + '\'' +
				", imei3='" + imei3 + '\'' +
				", imei4='" + imei4 + '\'' +
				", deviceBrand='" + deviceBrand + '\'' +
				", deviceModel='" + deviceModel + '\'' +
				", devicePurchaseInvoiceUrl='" + devicePurchaseInvoiceUrl + '\'' +
				", deviceLostDdateTime='" + deviceLostDdateTime + '\'' +
				", deviceOwnerName='" + deviceOwnerName + '\'' +
				", deviceOwnerEmail='" + deviceOwnerEmail + '\'' +
				", deviceOwnerAddress='" + deviceOwnerAddress + '\'' +
				", deviceOwnerNationalIdUrl='" + deviceOwnerNationalIdUrl + '\'' +
				", deviceOwnerNationalID='" + deviceOwnerNationalID + '\'' +
				", deviceOwnerNationality='" + deviceOwnerNationality + '\'' +
				", contactNumberForOtp='" + contactNumberForOtp + '\'' +
				", otp='" + otp + '\'' +
				", firCopyUrl='" + firCopyUrl + '\'' +
				", remarks='" + remarks + '\'' +
				", status='" + status + '\'' +
				", createdBy='" + createdBy + '\'' +
				", requestType='" + requestType + '\'' +
				", requestId='" + requestId + '\'' +
				", requestMode='" + requestMode + '\'' +
				", fileName='" + fileName + '\'' +
				", fileRecordCount='" + fileRecordCount + '\'' +
				", mobileInvoiceBill='" + mobileInvoiceBill + '\'' +
				", deviceOwnerAddress2='" + deviceOwnerAddress2 + '\'' +
				", recoveryReason='" + recoveryReason + '\'' +
				", province='" + province + '\'' +
				", district='" + district + '\'' +
				", commune='" + commune + '\'' +
				", policeStation='" + policeStation + '\'' +
				", passportNumber='" + passportNumber + '\'' +
				", otpEmail='" + otpEmail + '\'' +
				", category='" + category + '\'' +
				", userStatus='" + userStatus + '\'' +
				", language='" + language + '\'' +
				", lostId='" + lostId + '\'' +
				", serialNumber='" + serialNumber + '\'' +
				", incidentDetail='" + incidentDetail + '\'' +
				", deviceType='" + deviceType + '\'' +
				", otpRetryCount=" + otpRetryCount +
				", otherDocument='" + otherDocument + '\'' +
				", oldRequestId='" + oldRequestId + '\'' +
				", browser='" + browser + '\'' +
				", publicIp='" + publicIp + '\'' +
				", featureId=" + featureId +
				", userId=" + userId +
				", userName='" + userName + '\'' +
				", userType='" + userType + '\'' +
				", userAgent='" + userAgent + '\'' +
				'}';
	}
}
