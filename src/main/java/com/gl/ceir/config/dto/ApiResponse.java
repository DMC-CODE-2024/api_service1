package com.gl.ceir.config.dto;

public class ApiResponse {

	private int responseCode;
	private String responseStatus;
	private String message;
	private Object data;

	public ApiResponse(Object data) {
		this.responseCode = 0;
		this.responseStatus = "SUCCESS";
		this.message = "SUCCESS";
		this.data = data;
	}

	public ApiResponse(int responseCode, String responseStatus, String message) {
		this.responseCode = responseCode;
		this.responseStatus = responseStatus;
		this.message = message;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public String getResponseStatus() {
		return responseStatus;
	}

	public Object getData() {
		return data;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	public void setResponseStatus(String responseStatus) {
		this.responseStatus = responseStatus;
	}

	public void setData(Object data) {
		this.data = data;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ApiResponse [responseCode=");
		builder.append(responseCode);
		builder.append(", responseStatus=");
		builder.append(responseStatus);
		builder.append(", message=");
		builder.append(message);
		builder.append(", data=");
		builder.append(data);
		builder.append("]");
		return builder.toString();
	}

}
