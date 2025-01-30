package com.gl.ceir.config.model.app;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gl.ceir.config.feature.addressmanagement.AuditTrailModel;
import jakarta.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "eirs_response_param")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) 
public class EirsResponse {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "created_on")
	@CreationTimestamp
	@JsonFormat(pattern="dd-MM-yyyy HH:mm")
	private LocalDateTime createdOn;

	@Column(name = "modified_on")
	@UpdateTimestamp
	@JsonFormat(pattern="dd-MM-yyyy HH:mm")
	private LocalDateTime modifiedOn;

	@Column(length = 1000)
	private String value;


	private String tag;
	
	private String description,language,featureName;

	@Column(name = "type")
	private Integer type;
	@Column(name = "active")
	private Integer active;
	@Column(name = "remark")
	private String remarks;

	@Column(name = "subject")
	private String subject;

	@Transient
	@JsonProperty(value = "auditTrailModel", access = JsonProperty.Access.WRITE_ONLY)
	private AuditTrailModel auditTrailModel;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getActive() {
		return active;
	}

	public void setActive(Integer active) {
		this.active = active;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public AuditTrailModel getAuditTrailModel() {
		return auditTrailModel;
	}

	public void setAuditTrailModel(AuditTrailModel auditTrailModel) {
		this.auditTrailModel = auditTrailModel;
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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getFeatureName() {
		return featureName;
	}

	public void setFeatureName(String featureName) {
		this.featureName = featureName;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	@Override
	public String toString() {
		return "EirsResponse{" +
				"id=" + id +
				", createdOn=" + createdOn +
				", modifiedOn=" + modifiedOn +
				", value='" + value + '\'' +
				", tag='" + tag + '\'' +
				", description='" + description + '\'' +
				", language='" + language + '\'' +
				", featureName='" + featureName + '\'' +
				", type=" + type +
				", active=" + active +
				", remarks='" + remarks + '\'' +
				", subject='" + subject + '\'' +
				", auditTrailModel=" + auditTrailModel +
				'}';
	}
}
