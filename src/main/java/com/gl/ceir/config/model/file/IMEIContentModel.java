package com.gl.ceir.config.model.file;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

public class IMEIContentModel {

	
	@CsvBindByName(column = "Created On")
	@CsvBindByPosition(position = 0)
	private String createdOn;
	
	@CsvBindByName(column = "Module Name")
	@CsvBindByPosition(position = 1)
	private String description;
	
	@CsvBindByName(column = "Label")
	@CsvBindByPosition(position = 2)
	private String label;
	
	@CsvBindByName(column = "English Name")
	@CsvBindByPosition(position = 3)
	private String englishName;
	
	@CsvBindByName(column = "Khmer Name")
	@CsvBindByPosition(position = 4)
	private String kherName;

	
public IMEIContentModel() {
		
	}

	
	public IMEIContentModel(String createdOn, String description, String label, String englishName, String kherName) {
		super();
		this.createdOn = createdOn;
		this.description = description;
		this.label = label;
		this.englishName = englishName;
		this.kherName = kherName;
	}

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getEnglishName() {
		return englishName;
	}

	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}

	public String getKherName() {
		return kherName;
	}

	public void setKherName(String kherName) {
		this.kherName = kherName;
	}

	@Override
	public String toString() {
		return "IMEIContentModel [createdOn=" + createdOn + ", description=" + description + ", label=" + label
				+ ", englishName=" + englishName + ", kherName=" + kherName + "]";
	}
	
	
}


