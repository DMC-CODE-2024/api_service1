package com.gl.ceir.config.feature.alert;

public enum RegistrationTags {

	REG_SUCESS_RESP ("REG_SUCESS_RESP", "The text and and an e-mail with OTP details has been sent to your registered Phone Number and E-Mail ID"),
	REG_PROFILE_FAIL_RESP("REG_PROFILE_FAIL_RESP", "user profile data not added"),
	REG_USER_FAIL_RESP("REG_USER_FAIL_RESP", "user data not added"),
	REG_FAIL_ROLES_RESP("REG_FAIL_ROLES_RESP", "please select correct roles"),
	REG_WRONG_USER_ID("REG_WRONG_USER_ID","user id is wrong"),
	COMMAN_FAIL_MSG("COMMAN_FAIL_MSG","Oops something wrong happened"),
	OTP_SUCESS_RESP("OTP_SUCESS_RESP","Your OTP is verified! The form has been submitted for approval. You will receive an intimation on your registered e-mail with the approval status within 2 to 3 working days"),
	OTP_SUCESS_For_UpdateUSer("Otp_Sucess_For_UpdateUSer","Your one time password is verified!"),
	REG_WRONG_OTP("REG_WRONG_OTP", "Otp failed to verify"),
	REG_EXPIRE_OTP("REG_EXPIRE_OTP", "Otp time is expired"),
	REOTP_SUCESS_RESP("REOTP_SUCESS_RESP","A text message and e-mail with OTP has been sent"),
	REG_REOTP_FAIL("REG_REOTP_FAIL", "Otp failed to resend"),
    LOGIN_WRONG_DETAILS("LOGIN_WRONG_DETAILS","Please enter the correct credentials"),
    LOGIN_UNAUTHORIZED("LOGIN_UNAUTHORIZED","You are not allow to access this account"),
	Reg_userlimit_exceed("Reg_userlimit_exceed","User creation limit for the usertype exceeded !!"),
    Reg_flag_off("Reg_flag_off","This feature is not available as of now"),
    Reg_allowed("Reg_allowed","Registration feature is now working"),
    Email_Exist("Email_Exist","Email already exist in the system"),
    Phone_Exist("Phone_Exist","Phone Number already exist in the system"),
    Change_Role_Not_Allowed("Change_Role_Not_Allowed","Change Role feature for this usertype is not available"),
    Invalid_Action("Invalid_Action","This is invalid action"),
    User_Success_Save("User_Success_Save","User has been sucessfully saved"),
    User_Fail_Save("User_Fail_Save","User failed to saved"),
    User_Success_Update("User_Success_Update","User has been sucessfully update"),
    User_Fail_Update("User_Fail_Update","User has been failed to update"),
	User_Delete("User_Delete","User has been Deleted Successfully"),
	User_Fail("User_Fail","Unable to delete user"),
	No_Question_Mapped("No_Question_Mapped","This feature works only for external users"),
    Success_Save("success","Sucessfully saved"),
   Exception("exception","Exception occured"),
   exist("exist","already exist in DB"),
	delete_successfully("deleted","Successfully deleted"),
	user_exceed_limit("user_exceed_limit","User creation limit for the usertype is now exceeded"),
	SOFT_DELETE_MESSAGE("SOFT_DELETE_MESSAGE","Account Does not exist");
	private String tag;
	private String message;
	
	private RegistrationTags() {

	}

	RegistrationTags(String tag, String message) {
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
