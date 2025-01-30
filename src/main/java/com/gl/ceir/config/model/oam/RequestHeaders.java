package com.gl.ceir.config.model.oam;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Table;

@Entity
 @Table(name = "portal_access_log")

public class RequestHeaders {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String userAgent;
	private String publicIp;
	@Column(nullable =false)
	@CreationTimestamp
	@JsonFormat(pattern="dd-MM-yyyy HH:mm")
	@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	private LocalDateTime createdOn;
	
	@Column(nullable =false)
	@UpdateTimestamp
	@JsonFormat(pattern="dd-MM-yyyy HH:mm")
	@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	private LocalDateTime modifiedOn;
        @Column(name="user_name")
        private String username;
	private String browser;
	
	@Transient
	public String filterPublicIp;
	
	@Transient
	public String filterBrowser;
	
	@Transient
	public String filteredUsername;
	
	
	
	public String getFilterPublicIp() {
		return filterPublicIp;
	}
	public void setFilterPublicIp(String filterPublicIp) {
		this.filterPublicIp = filterPublicIp;
	}
	public String getFilterBrowser() {
		return filterBrowser;
	}
	public void setFilterBrowser(String filterBrowser) {
		this.filterBrowser = filterBrowser;
	}
	public String getFilteredUsername() {
		return filteredUsername;
	}
	public void setFilteredUsername(String filteredUsername) {
		this.filteredUsername = filteredUsername;
	}
	public String getBrowser() {
		return browser;
	}
	public void setBrowser(String browser) {
		this.browser = browser;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUserAgent() {
		return userAgent;
	}
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}
	public String getPublicIp() {
		return publicIp;
	}
	public void setPublicIp(String publicIp) {
		this.publicIp = publicIp;
	}
	public LocalDateTime getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}
	public LocalDateTime getModifiedOn() {
		return modifiedOn;
	}
	public void setModifiedOn(LocalDateTime modifiedOn) {
		this.modifiedOn = modifiedOn;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("RequestHeaders [id=");
		builder.append(id);
		builder.append(", userAgent=");
		builder.append(userAgent);
		builder.append(", publicIp=");
		builder.append(publicIp);
		builder.append(", createdOn=");
		builder.append(createdOn);
		builder.append(", modifiedOn=");
		builder.append(modifiedOn);
		builder.append(", username=");
		builder.append(username);
		builder.append(", browser=");
		builder.append(browser);
		builder.append(", filterPublicIp=");
		builder.append(filterPublicIp);
		builder.append(", filterBrowser=");
		builder.append(filterBrowser);
		builder.append(", filteredUsername=");
		builder.append(filteredUsername);
		builder.append("]");
		return builder.toString();
	}
	public RequestHeaders(String userAgent, String publicIp,String username) {
		super();
		this.userAgent = userAgent;
		this.publicIp = publicIp;
		this.username=username;
	}
	public RequestHeaders(String userAgent, String publicIp, String username, String browser) {
		super();
		this.userAgent = userAgent;
		this.publicIp = publicIp;
		this.username = username;
		this.browser = browser;
	}
	public RequestHeaders() {
		super();
	}
}