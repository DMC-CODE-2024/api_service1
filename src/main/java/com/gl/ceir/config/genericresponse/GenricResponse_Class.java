package com.gl.ceir.config.genericresponse;

public class GenricResponse_Class {

	private int errorCode;
	private String tag;
	private String message;
	private String txnId;
	private DataClass data;
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GenricResponse_Class [errorCode=");
		builder.append(errorCode);
		builder.append(", tag=");
		builder.append(tag);
		builder.append(", message=");
		builder.append(message);
		builder.append(", txnId=");
		builder.append(txnId);
		builder.append(", data=");
		builder.append(data);
		builder.append("]");
		return builder.toString();
	}
	public int getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getTxnId() {
		return txnId;
	}
	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}
	public DataClass getData() {
		return data;
	}
	public void setData(DataClass data) {
		this.data = data;
	}

	
}
