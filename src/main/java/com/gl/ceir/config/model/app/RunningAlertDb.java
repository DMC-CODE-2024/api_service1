package com.gl.ceir.config.model.app;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "sys_generated_alert")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class RunningAlertDb implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@CreationTimestamp
	@JsonFormat(pattern="yyyy-MM-dd HH:mm")
	private LocalDateTime createdOn;

	@UpdateTimestamp
	@JsonFormat(pattern="yyyy-MM-dd HH:mm")
	private LocalDateTime modifiedOn;

	private Integer userId;
	
	@Column(length = 20)
	private String alertId;
	private String description;
	private Integer status;
	private String  featureName;
	@Transient
	private String statusInterp;
	private String username;

	public RunningAlertDb() {
		// TODO Auto-generated constructor stub
	}
	
	public RunningAlertDb(Integer userId, String alertId, String description, Integer status) {
		this.userId = userId;
		this.alertId = alertId;
		this.description = description;
		this.status = status;
	}

	public String getFeatureName() {
		return featureName;
	}

	public void setFeatureName(String featureName) {
		this.featureName = featureName;
	}

	public String getStatusInterp() {
		return statusInterp;
	}

	public void setStatusInterp(String statusInterp) {
		this.statusInterp = statusInterp;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

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
	public LocalDateTime getModifiedOn() {
		return modifiedOn;
	}
	public void setModifiedOn(LocalDateTime modifiedOn) {
		this.modifiedOn = modifiedOn;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getAlertId() {
		return alertId;
	}
	public void setAlertId(String alertId) {
		this.alertId = alertId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("RunningAlertDb{");
		sb.append("id=").append(id);
		sb.append(", createdOn=").append(createdOn);
		sb.append(", modifiedOn=").append(modifiedOn);
		sb.append(", userId=").append(userId);
		sb.append(", alertId='").append(alertId).append('\'');
		sb.append(", description='").append(description).append('\'');
		sb.append(", status=").append(status);
		sb.append(", featureName='").append(featureName).append('\'');
		sb.append(", statusInterp='").append(statusInterp).append('\'');
		sb.append(", username='").append(username).append('\'');
		sb.append('}');
		return sb.toString();
	}

}
