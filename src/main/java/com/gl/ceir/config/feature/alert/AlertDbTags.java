package com.gl.ceir.config.feature.alert;

public enum AlertDbTags {

	
	Alert_Data_By_Id_Fail("Alert_Data_By_Id_Fail","Failed to find find Alert data by Id"),
	Alert_Update_Sucess ("Alert_Update_Sucess", "Alert db has been sucessfully update"),
	Alert_Update_Fail("Alert_Update_Fail", "Alert db failed to update"),
	Alert_Wrong_ID("Alert_Wrong_ID", "Alert Db Id is wrong");
	private String tag;
	private String message;
	
	private AlertDbTags() {

	}

	AlertDbTags(String tag, String message) {
		this.tag = tag;
		this.message = message;
	}

	public String getTag() {
		return tag;
	}

	public String getMessage() {
		return message;
	}
}
