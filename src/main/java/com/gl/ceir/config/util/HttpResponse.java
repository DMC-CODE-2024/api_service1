package com.gl.ceir.config.util;
import com.gl.ceir.config.model.app.User;

public class HttpResponse {
	
	private String response;
	private Integer statusCode;
	private User user;
	
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public Integer getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("HttpResponse [response=");
		builder.append(response);
		builder.append(", statusCode=");
		builder.append(statusCode);
		builder.append(", user=");
		builder.append(user);
		builder.append("]");
		return builder.toString();
	}

}
