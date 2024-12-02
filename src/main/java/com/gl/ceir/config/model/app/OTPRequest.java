package com.gl.ceir.config.model.app;

public class OTPRequest {

	private int statusCode;
	private String requestID;
	private String oldRequestID;
	private String requestType;
	private String statusMsg;
	private String otp;
	private String browser;
	private String publicIp;
	private String userAgent;
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public String getRequestID() {
		return requestID;
	}
	public void setRequestID(String requestID) {
		this.requestID = requestID;
	}
	public String getRequestType() {
		return requestType;
	}
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	public String getStatusMsg() {
		return statusMsg;
	}
	public void setStatusMsg(String statusMsg) {
		this.statusMsg = statusMsg;
	}
	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
	public String getOldRequestID() {
		return oldRequestID;
	}
	public void setOldRequestID(String oldRequestID) {
		this.oldRequestID = oldRequestID;
	}
	public String getBrowser() {
		return browser;
	}
	public void setBrowser(String browser) {
		this.browser = browser;
	}
	public String getPublicIp() {
		return publicIp;
	}
	public void setPublicIp(String publicIp) {
		this.publicIp = publicIp;
	}
	public String getUserAgent() {
		return userAgent;
	}
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}
	@Override
	public String toString() {
		return "OTPRequest [statusCode=" + statusCode + ", requestID=" + requestID + ", oldRequestID=" + oldRequestID
				+ ", requestType=" + requestType + ", statusMsg=" + statusMsg + ", otp=" + otp + ", browser=" + browser
				+ ", publicIp=" + publicIp + ", userAgent=" + userAgent + "]";
	}

	
}
