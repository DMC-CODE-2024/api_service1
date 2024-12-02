package com.gl.ceir.config.feature.alert;

import jakarta.persistence.Transient;

public class AllRequest {

	@Transient
	private String username;
	
	@Transient
	private long dataId;
	
	@Transient
	private long userTypeId;
	@Transient
	private long userId;
	@Transient
	private long featureId;
	
	@Transient
	private String userType;
	
	@Transient
	private int action;
	@Transient
	private String browser;
	@Transient
	public String publicIp;
//	@Transient	private String columnName;
	// @Transient	private String order;
//	@Transient
//	private String userAgent;
//	
//	@Transient
//	private String publicIp;
	
	
	public String getUsername() {
		return username;
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
	public void setUsername(String username) {
		this.username = username;
	}
	public long getUserTypeId() {
		return userTypeId;
	}
	public void setUserTypeId(long userTypeId) {
		this.userTypeId = userTypeId;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	
public int getAction() {
		return action;
	}
	public void setAction(int action) {
		this.action = action;
	}
	//	public String getUserAgent() {
//		return userAgent;
//	}
//	public void setUserAgent(String userAgent) {
//		this.userAgent = userAgent;
//	}
//	public String getPublicIp() {
//		return publicIp;
//	}
//	public void setPublicIp(String publicIp) {
//		this.publicIp = publicIp;
//	}
	public long getFeatureId() {
		return featureId;
	}
	public void setFeatureId(long featureId) {
		this.featureId = featureId;
	}
	
	
	public long getDataId() {
		return dataId;
	}
	public void setDataId(long dataId) {
		this.dataId = dataId;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AllRequest [username=");
		builder.append(username);
		builder.append(", dataId=");
		builder.append(dataId);
		builder.append(", userTypeId=");
		builder.append(userTypeId);
		builder.append(", userId=");
		builder.append(userId);
		builder.append(", featureId=");
		builder.append(featureId);
		builder.append(", userType=");
		builder.append(userType);
		builder.append(", action=");
		builder.append(action);
		builder.append(", browser=");
		builder.append(browser);
		builder.append(", publicIp=");
		builder.append(publicIp);
		builder.append("]");
		return builder.toString();
	}
	
	
	
}
