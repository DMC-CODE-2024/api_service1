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
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDateTime createdOn;

	@Column(name = "modified_on")
	@UpdateTimestamp
	@JsonFormat(pattern="yyyy-MM-dd")
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

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("EirsResponse{");
		sb.append("id=").append(id);
		sb.append(", createdOn=").append(createdOn);
		sb.append(", modified_on=").append(modifiedOn);
		sb.append(", value='").append(value).append('\'');
		sb.append(", tag='").append(tag).append('\'');
		sb.append(", description='").append(description).append('\'');
		sb.append(", language='").append(language).append('\'');
		sb.append(", featureName='").append(featureName).append('\'');
		sb.append(", type=").append(type);
		sb.append(", active=").append(active);
		sb.append(", remarks='").append(remarks).append('\'');
		sb.append(", auditTrailModel=").append(auditTrailModel);
		sb.append('}');
		return sb.toString();
	}


}
