package com.gl.ceir.config.model.app;

import java.util.Map;


public class ValidationOutput {
	
	private Boolean isValid;
	
	private Map<String,String> invalidFields;
	
	private int errorCode;
	private String tag;
	private String message;
	
	public ValidationOutput() {
		
	}
	
	public ValidationOutput(Boolean isValid, Map<String, String> invalidFields) {
		this.isValid = isValid;
		this.invalidFields = invalidFields;
	}
	public Boolean getIsValid() {
		return isValid;
	}
	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}
	public Map<String, String> getInvalidFields() {
		return invalidFields;
	}
	public void setInvalidFields(Map<String, String> invalidFields) {
		this.invalidFields = invalidFields;
	}
	
	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ValidationOutput [isValid=");
		builder.append(isValid);
		builder.append(", invalidFields=");
		builder.append(invalidFields);
		builder.append(", errorCode=");
		builder.append(errorCode);
		builder.append(", tag=");
		builder.append(tag);
		builder.append(", message=");
		builder.append(message);
		builder.append("]");
		return builder.toString();
	}


	
}
