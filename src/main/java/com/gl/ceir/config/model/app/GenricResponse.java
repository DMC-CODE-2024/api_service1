package com.gl.ceir.config.model.app;

public class GenricResponse {
    private int errorCode;
    private String tag;
    private String message;
    private String txnId;
    private String requestID;
    private String statusCode;
    private Object data;

    public GenricResponse(int errorCode) {
        this.errorCode = errorCode;
    }

    public GenricResponse() {

    }

    public GenricResponse(int errorCode, String message, String txnId) {
        this.errorCode = errorCode;
        this.message = message;
        this.txnId = txnId;
    }

    public GenricResponse(int errorCode, String tag, String message, String txnId) {
        this.errorCode = errorCode;
        this.tag = tag;
        this.message = message;
        this.txnId = txnId;
    }

    public GenricResponse(int errorCode, String message, String txnId, Object data) {
        this.errorCode = errorCode;
        this.message = message;
        this.txnId = txnId;
        this.data = data;
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

    public void setData(Object data) {
        this.data = data;
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

    public Object getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getRequestID() {
        return requestID;
    }

    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("GenricResponse{");
        sb.append("errorCode=").append(errorCode);
        sb.append(", tag='").append(tag).append('\'');
        sb.append(", message='").append(message).append('\'');
        sb.append(", txnId='").append(txnId).append('\'');
        sb.append(", requestID='").append(requestID).append('\'');
        sb.append(", statusCode='").append(statusCode).append('\'');
        sb.append(", data=").append(data);
        sb.append('}');
        return sb.toString();
    }
}
