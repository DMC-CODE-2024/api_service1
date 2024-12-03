package com.gl.ceir.config.model.file;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

public class AuditTrailFileModel {
	
	@CsvBindByName(column = "Created On")
	@CsvBindByPosition(position = 0)
	private String createdOn;
	
	/*
	 * @CsvBindByName(column = "Transaction ID")
	 * 
	 * @CsvBindByPosition(position = 1) private String txnId;
	 */
	@CsvBindByName(column = "Transaction ID ")
	@CsvBindByPosition(position = 1)
	private String txnId;

	@CsvBindByName(column = "User Name")
	@CsvBindByPosition(position = 2)
	private String userName;

	/*
	 * @CsvBindByName(column = "Role Type")
	 * 
	 * @CsvBindByPosition(position = 4) private String roleType;
	 */
	@CsvBindByName(column = "Feature")
	@CsvBindByPosition(position = 3)
	private String featureName;
	
	@CsvBindByName(column = "Sub Feature")
	@CsvBindByPosition(position = 4)
	private String subFeatureName;
	
	@CsvBindByName(column = "Public IP")
	@CsvBindByPosition(position = 5)
	private String publicIP;
	
	@CsvBindByName(column = "Browser")
	@CsvBindByPosition(position = 6)
	private String browser;

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	
	public String getFeatureName() {
		return featureName;
	}

	public void setFeatureName(String featureName) {
		this.featureName = featureName;
	}

	public String getSubFeatureName() {
		return subFeatureName;
	}

	public void setSubFeatureName(String subFeatureName) {
		this.subFeatureName = subFeatureName;
	}


	public String getPublicIP() {
		return publicIP;
	}

	public void setPublicIP(String publicIP) {
		this.publicIP = publicIP;
	}

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	public String getTxnId() {
		return txnId;
	}

	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}


	@Override
	public String toString() {
		return "AuditTrailFileModel{" +
				"createdOn='" + createdOn + '\'' +
				", txnId='" + txnId + '\'' +
				", userName='" + userName + '\'' +
				", featureName='" + featureName + '\'' +
				", subFeatureName='" + subFeatureName + '\'' +
				", publicIP='" + publicIP + '\'' +
				", browser='" + browser + '\'' +
				'}';
	}
}
