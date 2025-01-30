package com.gl.ceir.config.model.app;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation. constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class TagsMapping  implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JsonFormat(pattern="dd-MM-yyyy HH:mm")
	private Date createdOn;
	
	@JsonFormat(pattern="dd-MM-yyyy HH:mm")
	private Date updatedOn;
	
	@NotNull
	private Integer featureId;
	
	@NotNull
	private Integer userTypeId;
	
	@NotNull
	@Column(length = 30)
	private String parentTag;
	
	@NotNull
	@Column(length = 30)
	private String childTag;
	
	@NotNull
	private Integer parentValue;
	
	@NotNull
	private Integer childValue;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public Date getUpdatedOn() {
		return updatedOn;
	}
	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}
	public Integer getFeatureId() {
		return featureId;
	}
	public void setFeatureId(Integer featureId) {
		this.featureId = featureId;
	}
	public Integer getUserTypeId() {
		return userTypeId;
	}
	public void setUserTypeId(Integer userTypeId) {
		this.userTypeId = userTypeId;
	}
	public String getParentTag() {
		return parentTag;
	}
	public void setParentTag(String parentTag) {
		this.parentTag = parentTag;
	}
	public String getChildTag() {
		return childTag;
	}
	public void setChildTag(String childTag) {
		this.childTag = childTag;
	}
	public Integer getParentValue() {
		return parentValue;
	}
	public void setParentValue(Integer parentValue) {
		this.parentValue = parentValue;
	}
	public Integer getChildValue() {
		return childValue;
	}
	public void setChildValue(Integer childValue) {
		this.childValue = childValue;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TagsMapping [id=");
		builder.append(id);
		builder.append(", createdOn=");
		builder.append(createdOn);
		builder.append(", updatedOn=");
		builder.append(updatedOn);
		builder.append(", featureId=");
		builder.append(featureId);
		builder.append(", userTypeId=");
		builder.append(userTypeId);
		builder.append(", parentTag=");
		builder.append(parentTag);
		builder.append(", childTag=");
		builder.append(childTag);
		builder.append(", parentValue=");
		builder.append(parentValue);
		builder.append(", childValue=");
		builder.append(childValue);
		builder.append("]");
		return builder.toString();
	}
}
