package com.gl.ceir.config.model.aud;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "modules_audit_trail")
public class ModulesAuditModel {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;


	@CreationTimestamp
	@JsonFormat(pattern="dd-MM-yyyy HH:mm")
	private LocalDateTime createdOn;

	@UpdateTimestamp
	@JsonFormat(pattern="dd-MM-yyyy HH:mm")
	private LocalDateTime modifiedOn;
	private String executionTime;
	private Integer statusCode;
	private Integer count,count2,failureCount;
	private String status;
	private String errorMessage;
	private String moduleName;
	private String featureName;
	private String action;
	private String info;
	private String serverName;
		public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getId() {
		return id;
	}

	public ModulesAuditModel setId(Long id) {
		this.id = id;
		return this;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public ModulesAuditModel setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
		return this;
	}

	public LocalDateTime getModifiedOn() {
		return modifiedOn;
	}

	public ModulesAuditModel setModifiedOn(LocalDateTime modifiedOn) {
		this.modifiedOn = modifiedOn;
		return this;
	}

	public String getExecutionTime() {
		return executionTime;
	}

	public ModulesAuditModel setExecutionTime(String executionTime) {
		this.executionTime = executionTime;
		return this;
	}

	public Integer getStatusCode() {
		return statusCode;
	}

	public ModulesAuditModel setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
		return this;
	}

	public Integer getCount() {
		return count;
	}

	public ModulesAuditModel setCount(Integer count) {
		this.count = count;
		return this;
	}

	public Integer getCount2() {
		return count2;
	}

	public ModulesAuditModel setCount2(Integer count2) {
		this.count2 = count2;
		return this;
	}

	public Integer getFailureCount() {
		return failureCount;
	}

	public ModulesAuditModel setFailureCount(Integer failureCount) {
		this.failureCount = failureCount;
		return this;
	}

	public String getStatus() {
		return status;
	}

	public ModulesAuditModel setStatus(String status) {
		this.status = status;
		return this;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public ModulesAuditModel setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
		return this;
	}

	public String getModuleName() {
		return moduleName;
	}

	public ModulesAuditModel setModuleName(String moduleName) {
		this.moduleName = moduleName;
		return this;
	}

	public String getFeatureName() {
		return featureName;
	}

	public ModulesAuditModel setFeatureName(String featureName) {
		this.featureName = featureName;
		return this;
	}

	public String getAction() {
		return action;
	}

	public ModulesAuditModel setAction(String action) {
		this.action = action;
		return this;
	}

	public String getInfo() {
		return info;
	}

	public ModulesAuditModel setInfo(String info) {
		this.info = info;
		return this;
	}

	public String getServerName() {
		return serverName;
	}

	public ModulesAuditModel setServerName(String serverName) {
		this.serverName = serverName;
		return this;
	}

	@Override
	public String toString() {
		return "ModulesAuditModel [id=" + id + ", createdOn=" + createdOn + ", modifiedOn=" + modifiedOn
				+ ", executionTime=" + executionTime + ", statusCode=" + statusCode + ", count=" + count + ", count2="
				+ count2 + ", failureCount=" + failureCount + ", status=" + status + ", errorMessage=" + errorMessage
				+ ", moduleName=" + moduleName + ", featureName=" + featureName + ", action=" + action + ", info="
				+ info + ", serverName=" + serverName + "]";
	}

	
	
	
}
