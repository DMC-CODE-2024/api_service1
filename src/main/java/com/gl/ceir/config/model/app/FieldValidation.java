package com.gl.ceir.config.model.app;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class FieldValidation {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private String fieldName;
	
	private String fieldDescription;
	
	private Integer maxLength;
	
	private Integer minLength;
	
	private String type;
	
	private String allowedSpace;//Y if allowed N if not
	
	private String allowedCharacterset;
	
	private String regex;
	
	private String mandatory;
	
	private String usertype;
	
	private Integer userId;
	
	private Integer featureId;
	
	private String featureName;

	private String message;
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldDescription() {
		return fieldDescription;
	}

	public void setFieldDescription(String fieldDescription) {
		this.fieldDescription = fieldDescription;
	}

	public Integer getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(Integer maxLength) {
		this.maxLength = maxLength;
	}

	public Integer getMinLength() {
		return minLength;
	}

	public void setMinLength(Integer minLength) {
		this.minLength = minLength;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAllowedSpace() {
		return allowedSpace;
	}

	public void setAllowedSpace(String allowedSpace) {
		this.allowedSpace = allowedSpace;
	}

	public String getAllowedCharacterset() {
		return allowedCharacterset;
	}

	public void setAllowedCharacterset(String allowedCharacterset) {
		this.allowedCharacterset = allowedCharacterset;
	}

	public String getRegex() {
		return regex;
	}

	public void setRegex(String regex) {
		this.regex = regex;
	}

	public String getMandatory() {
		return mandatory;
	}

	public void setMandatory(String mandatory) {
		this.mandatory = mandatory;
	}

	public String getUsertype() {
		return usertype;
	}

	public void setUsertype(String usertype) {
		this.usertype = usertype;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getFeatureId() {
		return featureId;
	}

	public void setFeatureId(Integer featureId) {
		this.featureId = featureId;
	}

	public String getFeatureName() {
		return featureName;
	}

	public void setFeatureName(String featureName) {
		this.featureName = featureName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "FieldValidation [id=" + id + ", fieldName=" + fieldName + ", fieldDescription=" + fieldDescription
				+ ", maxLength=" + maxLength + ", minLength=" + minLength + ", type=" + type + ", allowedSpace="
				+ allowedSpace + ", allowedCharacterset=" + allowedCharacterset + ", regex=" + regex + ", mandatory="
				+ mandatory + ", usertype=" + usertype + ", userId=" + userId + ", featureId=" + featureId
				+ ", featureName=" + featureName + ", message=" + message + "]";
	}

 
	
	
}
