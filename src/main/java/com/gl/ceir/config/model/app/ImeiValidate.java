package com.gl.ceir.config.model.app;

public class ImeiValidate {
	private static long serialVersionUID = 1L;
	private String feature;
	private int imei_type;
	private String user_type;
	private String imei;
	public String getFeature() {
		return feature;
	}
	public void setFeature(String feature) {
		this.feature = feature;
	}
	public int getImei_type() {
		return imei_type;
	}
	public void setImei_type(int imei_type) {
		this.imei_type = imei_type;
	}
	public String getUser_type() {
		return user_type;
	}
	public void setUser_type(String user_type) {
		this.user_type = user_type;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	
	public ImeiValidate() {
		super();
	}
	public ImeiValidate(String feature, int imei_type, String user_type, String imei) {
		super();
		this.feature = feature;
		this.imei_type = imei_type;
		this.user_type = user_type;
		this.imei = imei;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ImeiValidate [feature=");
		builder.append(feature);
		builder.append(", imei_type=");
		builder.append(imei_type);
		builder.append(", user_type=");
		builder.append(user_type);
		builder.append(", imei=");
		builder.append(imei);
		builder.append("]");
		return builder.toString();
	}
	
	
	
	
}
