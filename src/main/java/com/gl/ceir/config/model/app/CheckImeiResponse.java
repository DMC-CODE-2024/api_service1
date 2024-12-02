package com.gl.ceir.config.model.app;

public class CheckImeiResponse {

	private String status;
	private String errorMessage;
 	private String deviceId;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	@Override
	public String toString() {
		return "CheckImeiResponse [status=" + status + ", errorMessage=" + errorMessage + ", deviceId=" + deviceId
				+ "]";
	}
	 
}
