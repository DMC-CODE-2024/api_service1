package com.gl.ceir.config.model.constants;

public enum GenericMessageTags {

	NULL_VISA ("NULL_VISA", "Request visa update should not be null."),
	NULL_Natinality ("NULL_Natinality", "Nationality should not be null."),
	EMPTY_VISA ("EMPTY_VISA", "Request visa update should not be empty."),
	NULL_NID ("NULL_NID", "Null NID is not allowed for the request."),
	VISA_UPDATE_NOT_ALLOWED ("VISA_UPDATE_NOT_ALLOWED", "You have not provided visa information at the time of registeration."),
	VISA_UPDATE_SUCCESS ("VISA_UPDATE_SUCCESS", "Visa of user have been updated succesfully."),
	VISA_UPDATE_REQUEST_SUCCESS ("VISA_UPDATE_REQUEST_SUCCESS", "Request of visa update has been added successfully"),
	VISA_UPDATE_FAIL ("VISA_UPDATE_FAIL", "Updation of Visa of user have been failed."),
	VISA_UPDATE_REQUEST_FAIL ("VISA_UPDATE_REQUEST_FAIL", "Request of visa update have been failed."),
	INVALID_USER ("INVALID_USER", "You are not a valid user. Please register first before updating"),
	NULL_REQ ("NULL_REQ", "Request can't be null."),
	USER_UPDATE_SUCCESS("USER_UPDATE_SUCCESS", "User have been updated successfully."),
	VISA_EMPTY("VISA_EMPTY", "Visa information in request not found"),	
	DISCREPENCY_IN_CONFIG("DISCREPENCY_IN_CONFIG", "Discrepancy found in configuration."),
	USER_REGISTER_SUCCESS("USER_REGISTER_SUCCESS", "User have been registered successfully."),
	FEATURE_NOT_ALLOWED("FEATURE_NOT_ALLOWED", "This functionality is not supported now."),
	NULL_USER_DEPARTMENT ("NULL_USER_DEPARTMENT", "User Department is null for VIP."),
	SUCCESS("SUCCESS", "Success"),
	INVALID_STATE_TRANSTION("INVALID_STATE_TRANSTION", "Operation not allowed."),
	INVALID_REQUEST("INVALID_REQUEST", "Invalid Request."),
	INVALID_TXN_ID("INVALID_TXN_ID", "Txn id is not valid."),
	FEATURE_NOT_SUPPORTED("FEATURE_NOT_SUPPORTED", "This feature is not supported."),
	NO_DATA("NO_DATA", "No Data"),
	USER_ALREADY_EXIST("USER_ALREADY_EXIST", "This User is already exist."),
	DUPLICATE_IMEI("DUPLICATE_IMEI" ,"IMEI is already registered in CEIR System"),
	REGULARISED_DEVICE_EXCEEDED("REGULARISED_DEVICE_EXCEEDED", "Regularized Devices are exceeding the allowed count."),
	DEVICE_REGISTRATION_FAILED("DEVICE_REGISTRATION_FAILED","End user device registration have been failed."),
	DUPLICATE_IMEI_IN_REQUEST("DUPLICATE_IMEI_IN_REQUEST" ,"Duplicate IMEI received in request"),
	INVALID_TUPLE_FOR_IMEI_AND_MSISDN("INVALID_TUPLE_FOR_IMEI_AND_MSISDN", "This combination of imei and msisdn is invalid."),
	NO_IMEI_FOR_MSISDN("NO_IMEI_FOR_MSISDN", "No imei is attached with the msisdn"),
	INVALID_IMEI("INVALID_IMEI", "IMEI is invalid."),
	NULL_EndUser("NULL_ENDUSER", "End user data is null for this visa"),
	COMMAN_FAIL_MSG("COMMAN_FAIL_MSG","Oops something wrong happened")
	;
	
	private String tag;
	private String message;
	
	private GenericMessageTags() {

	}

	GenericMessageTags(String tag, String message) {
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
