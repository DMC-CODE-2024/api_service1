package com.gl.ceir.config.model.file;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

public class PendingTacApprovedFileModel {
	
	@CsvBindByName(column = "Create On")
	@CsvBindByPosition(position = 0)
	private String createdOn;
	
	@CsvBindByName(column = "Modified On")
	@CsvBindByPosition(position = 1)
	private String modifiedOn;
	
	@CsvBindByName(column = "Transaction ID")
	@CsvBindByPosition(position = 2)
	private String txnId;
	
	@CsvBindByName(column = "TAC")
	@CsvBindByPosition(position = 3)
	private String tac;
	
//	@CsvBindByName(column = "User Type")
//	@CsvBindByPosition(position = 4)
//	private String userType;
	
	@CsvBindByName(column = "Feature")
	@CsvBindByPosition(position = 4)
	private String featureName;
	

	public String getCreatedOn() {
		return createdOn;
	}


	public String getModifiedOn() {
		return modifiedOn;
	}


	public void setModifiedOn(String modifiedOn) {
		this.modifiedOn = modifiedOn;
	}


	public String getTxnId() {
		return txnId;
	}


	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}


	public String getTac() {
		return tac;
	}


	public void setTac(String tac) {
		this.tac = tac;
	}


//	public String getUserType() {
//		return userType;
//	}
//
//
//	public void setUserType(String userType) {
//		this.userType = userType;
//	}


	public String getFeatureName() {
		return featureName;
	}


	public void setFeatureName(String featureName) {
		this.featureName = featureName;
	}


	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}


	@Override
	public String toString() {
		return "PendingTacApprovedFileModel [createdOn=" + createdOn + ", modifiedOn=" + modifiedOn + ", txnId=" + txnId
				+ ", tac=" + tac + ", featureName=" + featureName + "]";
	}
}
