package com.gl.ceir.config.model.app;

public class NotificationModel {

	private Long id;
	private String channelType;
	private String message;
	private String msisdn,msgLang,email,featureName,featureTxnId,subject;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getChannelType() {
		return channelType;
	}
	public void setChannelType(String channelType) {
		this.channelType = channelType;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	public String getMsgLang() {
		return msgLang;
	}
	public void setMsgLang(String msgLang) {
		this.msgLang = msgLang;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFeatureName() {
		return featureName;
	}
	public void setFeatureName(String featureName) {
		this.featureName = featureName;
	}
	public String getFeatureTxnId() {
		return featureTxnId;
	}
	public void setFeatureTxnId(String featureTxnId) {
		this.featureTxnId = featureTxnId;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	@Override
	public String toString() {
		return "NotificationModel [id=" + id + ", channelType=" + channelType + ", message=" + message + ", msisdn="
				+ msisdn + ", msgLang=" + msgLang + ", email=" + email + ", featureName=" + featureName
				+ ", featureTxnId=" + featureTxnId + ", subject=" + subject + "]";
	}
	
	
	
}
