package com.gl.ceir.config.model.app;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;
import java.util.Date;


@Entity
public class TypeApprovedDb {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String manufacturerId;
	private String manufacturerName;
	private String country;

	//@Type(type="date")
	@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
	private Date requestDate;
	
	private String tac;
	private Integer approveStatus;
	@ColumnDefault("-1")
	private Integer adminApproveStatus;

	@Column(name="user_id")
	private Long userId;
	private String userType;
	private Long adminUserId;
	private String adminUserType;
	
	
	//@Type(type="date")
	@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm")
	private Date approveDisapproveDate;
	
	private String remark;
	private String adminRemark;
	private String fileName;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
	@CreationTimestamp
	@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	@Column(nullable = false, updatable = false)
	private Date createdOn;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
	@UpdateTimestamp
	@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	private Date modifiedOn;
	
	private String txnId;

	@OneToOne
	@JoinColumn(name="user_id",insertable = false, updatable = false)
	@JsonIgnore
	private User userForTypeApprove;

	@Transient
	private String stateInterp;
	@Transient
	private String adminStateInterp;

	private String trademark;
	
	private Long productName;
	
	private int modelNumber;
	
	@Transient
	private String productNameInterp;
	
	@Transient
	private String modelNumberInterp;
	
	private String manufacturerCountry;
	
	private String frequencyRange;

	private Long featureId;
	
	public String getTrademark() {
		return trademark;
	}
	public void setTrademark(String trademark) {
		this.trademark = trademark;
	}
	public String getManufacturerCountry() {
		return manufacturerCountry;
	}
	public void setManufacturerCountry(String manufacturerCountry) {
		this.manufacturerCountry = manufacturerCountry;
	}
	public String getFrequencyRange() {
		return frequencyRange;
	}
	public void setFrequencyRange(String frequencyRange) {
		this.frequencyRange = frequencyRange;
	}
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getManufacturerId() {
		return manufacturerId;
	}
	public void setManufacturerId(String manufacturerId) {
		this.manufacturerId = manufacturerId;
	}
	public String getManufacturerName() {
		return manufacturerName;
	}
	public void setManufacturerName(String manufacturerName) {
		this.manufacturerName = manufacturerName;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public Date getRequestDate() {
		return requestDate;
	}
	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}
	public String getTac() {
		return tac;
	}
	public void setTac(String tac) {
		this.tac = tac;
	}
	public Date getApproveDisapproveDate() {
		return approveDisapproveDate;
	}
	public void setApproveDisapproveDate(Date approveDisapproveDate) {
		this.approveDisapproveDate = approveDisapproveDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public Date getModifiedOn() {
		return modifiedOn;
	}
	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}
	public Integer getApproveStatus() {
		return approveStatus;
	}
	public void setApproveStatus(Integer approveStatus) {
		this.approveStatus = approveStatus;
	}
	public String getTxnId() {
		return txnId;
	}
	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}
	public User getUserForTypeApprove() {
		return userForTypeApprove; 
	} 
	public void setUserForTypeApprove(User userForTypeApprove) {
		this.userForTypeApprove
		= userForTypeApprove; 
	}
	public String getStateInterp() {
		return stateInterp;
	}
	public void setStateInterp(String stateInterp) {
		this.stateInterp = stateInterp;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Integer getAdminApproveStatus() {
		return adminApproveStatus;
	}
	public void setAdminApproveStatus(Integer adminApproveStatus) {
		this.adminApproveStatus = adminApproveStatus;
	}
	public String getAdminStateInterp() {
		return adminStateInterp;
	}
	public void setAdminStateInterp(String adminStateInterp) {
		this.adminStateInterp = adminStateInterp;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public Long getAdminUserId() {
		return adminUserId;
	}
	public void setAdminUserId(Long adminUserId) {
		this.adminUserId = adminUserId;
	}
	public String getAdminUserType() {
		return adminUserType;
	}
	public void setAdminUserType(String adminUserType) {
		this.adminUserType = adminUserType;
	}
	public String getAdminRemark() {
		return adminRemark;
	}
	public void setAdminRemark(String adminRemark) {
		this.adminRemark = adminRemark;
	}
	public Long getProductName() {
		return productName;
	}
	public void setProductName(Long productName) {
		this.productName = productName;
	}
	public int getModelNumber() {
		return modelNumber;
	}
	public void setModelNumber(int modelNumber) {
		this.modelNumber = modelNumber;
	}
	public String getProductNameInterp() {
		return productNameInterp;
	}
	public void setProductNameInterp(String productNameInterp) {
		this.productNameInterp = productNameInterp;
	}
	public String getModelNumberInterp() {
		return modelNumberInterp;
	}
	public void setModelNumberInterp(String modelNumberInterp) {
		this.modelNumberInterp = modelNumberInterp;
	}
	public Long getFeatureId() {
		return featureId;
	}
	public void setFeatureId(Long featureId) {
		this.featureId = featureId;
	}
	/*@PostLoad
    public void postLoad() {
        if((stateInterp == null || stateInterp.isEmpty()) && this.approveStatus != null) {
        	this.stateInterp = TypeApprovedStatus.getActionNames( this.approveStatus ).toString();
        }
        if((adminStateInterp == null || adminStateInterp.isEmpty()) && this.adminApproveStatus != null) {
        	this.adminStateInterp = TypeApprovedStatus.getActionNames( this.adminApproveStatus ).toString();
        }
    }*/

	@Override
	public String toString() {
		return "TypeApprovedDb [id=" + id + ", manufacturerId=" + manufacturerId + ", manufacturerName="
				+ manufacturerName + ", country=" + country + ", requestDate=" + requestDate + ", tac=" + tac
				+ ", approveStatus=" + approveStatus + ", approveDisapproveDate="
				+ approveDisapproveDate + ", remark=" + remark + ", file=" + fileName + ", createdOn=" + createdOn
				+ ", modifiedOn=" + modifiedOn + ", txnId=" + txnId + ",trademark="+trademark + ",modelNumber="+modelNumber + 
				",productName="+productName + ",manufacturerCountry="+ manufacturerCountry +",frequencyRange="+ frequencyRange+ "]";
	}
}
