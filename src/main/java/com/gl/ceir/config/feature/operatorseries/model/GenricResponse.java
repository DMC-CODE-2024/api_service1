package com.gl.ceir.config.feature.operatorseries.model;

public class GenricResponse {
    private String tag;
    private String message;
    private int statusCode;
    private Object data;
    private String seriesLength;
    private boolean isValid;

    public GenricResponse() {

    }

    public GenricResponse(String message, int statusCode) {

        this.message = message;
        this.statusCode = statusCode;
    }

    public String getSeriesLength() {
        return seriesLength;
    }

    public GenricResponse setSeriesLength(String seriesLength) {
        this.seriesLength = seriesLength;
        return this;
    }

    public String getTag() {
        return tag;
    }

    public GenricResponse setTag(String tag) {
        this.tag = tag;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public GenricResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public GenricResponse setStatusCode(int statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public Object getData() {
        return data;
    }

    public GenricResponse setData(Object data) {
        this.data = data;
        return this;
    }

    public boolean isValid() {
        return isValid;
    }

    public GenricResponse setValid(boolean valid) {
        isValid = valid;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("GenricResponse{");
        sb.append("tag='").append(tag).append('\'');
        sb.append(", message='").append(message).append('\'');
        sb.append(", statusCode=").append(statusCode);
        sb.append(", data=").append(data);
        sb.append(", seriesLength='").append(seriesLength).append('\'');
        sb.append(", isValid=").append(isValid);
        sb.append('}');
        return sb.toString();
    }
}
