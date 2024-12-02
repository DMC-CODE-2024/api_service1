package com.gl.ceir.config.model.app;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
 
@Entity
public class VipList implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ImeiMsisdnIdentity imeiMsisdnIdentity;

	@CreationTimestamp
	@JsonFormat(pattern="yyyy-MM-dd HH:mm")
	@Column(nullable = false, updatable = false)
	private LocalDateTime createdOn;

	@UpdateTimestamp
	private LocalDateTime modifiedOn;
	
	@JsonIgnore
	private String requestedBy;
	
	@JsonIgnore
	private String approvedBy;
	
	private String txnId;
	private String remark;

	
	public VipList() {}
	
	public VipList(String imei, String msisdn) {
		this.imeiMsisdnIdentity = new ImeiMsisdnIdentity(imei, msisdn);
	}
	
	public VipList(String imei, String msisdn, String requestedBy, String approvedBy, String txnId) {
		this.imeiMsisdnIdentity = new ImeiMsisdnIdentity(imei, msisdn);
		this.requestedBy = requestedBy;
		this.approvedBy = approvedBy;
		this.txnId = txnId;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public ImeiMsisdnIdentity getImeiMsisdnIdentity() {
		return imeiMsisdnIdentity;
	}

	public void setImeiMsisdnIdentity(ImeiMsisdnIdentity imeiMsisdnIdentity) {
		this.imeiMsisdnIdentity = imeiMsisdnIdentity;
	}

	public String getRequestedBy() {
		return requestedBy;
	}

	public void setRequestedBy(String requestedBy) {
		this.requestedBy = requestedBy;
	}

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public String getTxnId() {
		return txnId;
	}

	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	

}
