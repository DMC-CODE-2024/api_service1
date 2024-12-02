package com.gl.ceir.config.model.file;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

public class AllowedTacFile {

	
	@CsvBindByName(column = "Created On")
	@CsvBindByPosition(position = 0)
	private String createdOn;
	
	@CsvBindByName(column = "Tac")
	@CsvBindByPosition(position = 1)
	private String tac;

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public String getTac() {
		return tac;
	}

	public void setTac(String tac) {
		this.tac = tac;
	}

	@Override
	public String toString() {
		return "AllowedTacFile [createdOn=" + createdOn + ", tac=" + tac + "]";
	}

	public AllowedTacFile() {
		
	}
	
	
	public AllowedTacFile(String createdOn, String tac) {
		super();
		this.createdOn = createdOn;
		this.tac = tac;
	}
	
	
}
