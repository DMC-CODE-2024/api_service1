package com.gl.ceir.config.feature.eirs_response_param.csv_file_model;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

public class EirsResponseParamFileModel {
//    @CsvBindByName(column = "Id")
//    @CsvBindByPosition(position = 0)
//    private Integer id;
//
//    @CsvBindByName(column = "Created On")
//    @CsvBindByPosition(position = 1)
//    private String createdOn;

    @CsvBindByName(column = "Description")
    @CsvBindByPosition(position = 1)
    private String description;

    @CsvBindByName(column = "Modified On")
    @CsvBindByPosition(position = 0)
    private String modifiedOn;

//    @CsvBindByName(column = "Tag")
//    @CsvBindByPosition(position = 4)
//    private String tag;

//    @CsvBindByName(column = "Type")
//    @CsvBindByPosition(position = 5)
//    private Integer type;

    @CsvBindByName(column = "Value")
    @CsvBindByPosition(position = 2)
    private String value;

//    @CsvBindByName(column = "Active")
//    @CsvBindByPosition(position = 7)
//    private Integer active;

    @CsvBindByName(column = "Feature Name")
    @CsvBindByPosition(position = 3)
    private String featureName;
//
//    @CsvBindByName(column = "Remarks")
//    @CsvBindByPosition(position = 9)
//    private String remarks;
//
//    @CsvBindByName(column = "User Type")
//    @CsvBindByPosition(position = 10)
//    private String userType;

//    @CsvBindByName(column = "Modified By")
//    @CsvBindByPosition(position = 11)
//    private String modifiedBy;

    @CsvBindByName(column = "Language")
    @CsvBindByPosition(position =4)
    private String language;

    // Getters and Setters

//    public Integer getId() {
//        return id;
//    }
//
//    public EirsResponseParamFileModel setId(Integer id) {
//        this.id = id;
//        return this;
//    }
//
//    public String getCreatedOn() {
//        return createdOn;
//    }

//    public EirsResponseParamFileModel setCreatedOn(String createdOn) {
//        this.createdOn = createdOn;
//        return this;
//    }

    public String getDescription() {
        return description;
    }

    public EirsResponseParamFileModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getModifiedOn() {
        return modifiedOn;
    }

    public EirsResponseParamFileModel setModifiedOn(String modifiedOn) {
        this.modifiedOn = modifiedOn;
        return this;
    }

//    public String getTag() {
//        return tag;
//    }
//
//    public EirsResponseParamFileModel setTag(String tag) {
//        this.tag = tag;
//        return this;
//    }

//    public Integer getType() {
//        return type;
//    }
//
//    public EirsResponseParamFileModel setType(Integer type) {
//        this.type = type;
//        return this;
//    }

    public String getValue() {
        return value;
    }

    public EirsResponseParamFileModel setValue(String value) {
        this.value = value;
        return this;
    }

//    public Integer getActive() {
//        return active;
//    }
//
//    public EirsResponseParamFileModel setActive(Integer active) {
//        this.active = active;
//        return this;
//    }

    public String getFeatureName() {
        return featureName;
    }

    public EirsResponseParamFileModel setFeatureName(String featureName) {
        this.featureName = featureName;
        return this;
    }

//    public String getRemarks() {
//        return remarks;
//    }
//
//    public EirsResponseParamFileModel setRemarks(String remarks) {
//        this.remarks = remarks;
//        return this;
//    }
//
//    public String getUserType() {
//        return userType;
//    }
//
//    public EirsResponseParamFileModel setUserType(String userType) {
//        this.userType = userType;
//        return this;
//    }

//    public String getModifiedBy() {
//        return modifiedBy;
//    }
//
//    public EirsResponseParamFileModel setModifiedBy(String modifiedBy) {
//        this.modifiedBy = modifiedBy;
//        return this;
//    }

    public String getLanguage() {
        return language;
    }

    public EirsResponseParamFileModel setLanguage(String language) {
        this.language = language;
        return this;
    }
}