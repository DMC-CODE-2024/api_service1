package com.gl.ceir.config.model.app;

import java.io.Serializable;

public class FeatureValidateReq implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private int featureId;
	private int usertypeId;
	
	public FeatureValidateReq(int featureId, int usertypeId) {
		this.featureId = featureId;
		this.usertypeId = usertypeId;
	}

	public int getFeatureId() {
		return featureId;
	}

	public void setFeatureId(int featureID) {
		this.featureId = featureID;
	}

	public int getUsertypeId() {
		return usertypeId;
	}

	public void setUsertypeId(int usertypeId) {
		this.usertypeId = usertypeId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FeatureValidateReq [featureId=");
		builder.append(featureId);
		builder.append(", usertypeId=");
		builder.append(usertypeId);
		builder.append("]");
		return builder.toString();
	}

}
