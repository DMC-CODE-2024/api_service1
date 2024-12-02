package com.gl.ceir.config.model.file;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

public class IMEIMessageMgmtFile {
	@CsvBindByName(column = "Created On")
	@CsvBindByPosition(position = 0)
	private String createdOn;
	
	@CsvBindByName(column = "Modified On")
	@CsvBindByPosition(position = 1)
	private String modifiedOn;
	
	@CsvBindByName(column = "Module Name")
	@CsvBindByPosition(position = 2)
	private String featureName;


	@CsvBindByName(column = "Tag")
	@CsvBindByPosition(position = 3)
	private String tag;

	@CsvBindByName(column = "Description")
	@CsvBindByPosition(position = 4)
	private String description;
	
	@CsvBindByName(column = "Value")
	@CsvBindByPosition(position = 5)
	private String value;
	
	@CsvBindByName(column = "Language")
	@CsvBindByPosition(position = 6)
	private String language;
	
	

	public IMEIMessageMgmtFile() {
		
	}
	
	
	public IMEIMessageMgmtFile(String createdOn, String modifiedOn, String description, String value) {
		super();
		this.createdOn = createdOn;
		this.modifiedOn = modifiedOn;
		this.description = description;
		this.value = value;
	}
	public IMEIMessageMgmtFile(String createdOn, String modifiedOn, String featureName, String tag,String description, String value,String language) {
		super();
		this.createdOn = createdOn;
		this.modifiedOn = modifiedOn;
		this.featureName=featureName;
		this.tag=tag;
		this.description = description;
		this.value = value;
		this.language=language;
	}



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


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getValue() {
		return value;
	}


	public void setValue(String value) {
		this.value = value;
	}




	


	public String getFeatureName() {
		return featureName;
	}


	public void setFeatureName(String featureName) {
		this.featureName = featureName;
	}


	public String getLanguage() {
		return language;
	}


	public void setLanguage(String language) {
		this.language = language;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	@Override
	public String toString() {
		return "IMEIMessageMgmtFile{" +
				"createdOn='" + createdOn + '\'' +
				", modifiedOn='" + modifiedOn + '\'' +
				", featureName='" + featureName + '\'' +
				", tag='" + tag + '\'' +
				", description='" + description + '\'' +
				", value='" + value + '\'' +
				", language='" + language + '\'' +
				'}';
	}
}
