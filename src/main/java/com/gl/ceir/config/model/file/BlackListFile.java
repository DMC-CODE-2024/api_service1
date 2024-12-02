package com.gl.ceir.config.model.file;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

public class BlackListFile {

	@CsvBindByName(column = "Created On")
	@CsvBindByPosition(position = 0)
	private String createdOn;
	
	@CsvBindByName(column = "IMEI")
	@CsvBindByPosition(position = 1)
	private String imei;
	
	@CsvBindByName(column = "IMSI")
	@CsvBindByPosition(position = 2)
	private String imsi;
	
	@CsvBindByName(column = "MSISDN")
	@CsvBindByPosition(position = 3)
	private String msisdn;

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	
	
	public BlackListFile() {
		
	}
			
	public BlackListFile(String createdOn, String imei, String imsi, String msisdn) {
		super();
		this.createdOn = createdOn;
		this.imei = imei;
		this.imsi = imsi;
		this.msisdn = msisdn;
	}
	

	@Override
	public String toString() {
		return "BlackListFile [createdOn=" + createdOn + ", imei=" + imei + ", imsi=" + imsi + ", msisdn=" + msisdn
				+ "]";
	}
	

	
	
}
