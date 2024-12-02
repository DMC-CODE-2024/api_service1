package com.gl.ceir.config.model.file;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

public class ModulesAuditFile {

	@CsvBindByName(column = "Created On")
	@CsvBindByPosition(position = 0)
	private String createdOn;
	
	@CsvBindByName(column = "Modified On")
	@CsvBindByPosition(position = 1)
	private String modifiedOn;
	

	
	@CsvBindByName(column = "Module Name")
	@CsvBindByPosition(position = 2)
	private String moduleName;

	@CsvBindByName(column = "Feature Name")
	@CsvBindByPosition(position = 3)
	private String featureName;
	@CsvBindByName(column = "Status")
	@CsvBindByPosition(position = 4)
	private String status;
	
	@CsvBindByName(column = "Status Code")
	@CsvBindByPosition(position = 5)
	private Integer statusCode;

	@CsvBindByName(column = "Execution Time")
	@CsvBindByPosition(position = 6)
	private String executionTime;
	
	@CsvBindByName(column = "Error Message")
	@CsvBindByPosition(position = 7)
	private String errorMessage;
	
	@CsvBindByName(column = "Action")
	@CsvBindByPosition(position = 8)
	private String action;
	
	@CsvBindByName(column = "Count")
	@CsvBindByPosition(position = 9)
	private Integer count;
	
	@CsvBindByName(column = "Count2")
	@CsvBindByPosition(position = 10)
	private Integer count2;
	
	@CsvBindByName(column = "Failure Count")
	@CsvBindByPosition(position = 11)
	private Integer failureCount;
	
	@CsvBindByName(column = "Info")
	@CsvBindByPosition(position = 12)
	private String info;
	
	@CsvBindByName(column = "Server Name")
	@CsvBindByPosition(position = 13)
	private String serverName;

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public String getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(String modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public String getFeatureName() {
		return featureName;
	}

	public void setFeatureName(String featureName) {
		this.featureName = featureName;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}

	

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getCount2() {
		return count2;
	}

	public void setCount2(Integer count2) {
		this.count2 = count2;
	}

	public Integer getFailureCount() {
		return failureCount;
	}

	public void setFailureCount(Integer failureCount) {
		this.failureCount = failureCount;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	


	public String getExecutionTime() {
		return executionTime;
	}

	public void setExecutionTime(String executionTime) {
		this.executionTime = executionTime;
	}

	@Override
	public String toString() {
		return "ModulesAuditFile [createdOn=" + createdOn + ", modifiedOn=" + modifiedOn + ", featureName="
				+ featureName + ", moduleName=" + moduleName + ", status=" + status + ", statusCode=" + statusCode
				+ ", executionTime=" + executionTime + ", errorMessage=" + errorMessage + ", action=" + action
				+ ", count=" + count + ", count2=" + count2 + ", failureCount=" + failureCount + ", info=" + info
				+ ", serverName=" + serverName + "]";
	}

	public ModulesAuditFile() {
	
	}

	public ModulesAuditFile(String createdOn, String modifiedOn, String featureName, String moduleName, String status,
                            Integer statusCode, String executionTime, String errorMessage, String action, Integer count,
                            Integer count2, Integer failureCount, String info, String serverName) {
		super();
		this.createdOn = createdOn;
		this.modifiedOn = modifiedOn;
		this.featureName = featureName;
		this.moduleName = moduleName;
		this.status = status;
		this.statusCode = statusCode;
		this.executionTime = executionTime;
		this.errorMessage = errorMessage;
		this.action = action;
		this.count = count;
		this.count2 = count2;
		this.failureCount = failureCount;
		this.info = info;
		this.serverName = serverName;
	}

	
}
