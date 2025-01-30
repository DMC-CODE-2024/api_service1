package com.gl.ceir.config.model.file;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;

import jakarta.persistence.Column;

public class MSISDNFileModel {
    @CsvBindByName(column = "Created On")
    @CsvBindByPosition(position = 0)
    private String createdOn;
    @CsvBindByName(column = "Modified On")
    @CsvBindByPosition(position = 1)
    private String modifiedOn;
    @CsvBindByName(column = "Operator Name")
    @CsvBindByPosition(position = 2)
    private String operatorName;

    @CsvBindByName(column = "User ID")
    @CsvBindByPosition(position = 3)
    private String userId;

    @CsvBindByName(column = "Series Type")
    @CsvBindByPosition(position = 4)
    private String seriesType;

    @CsvBindByName(column = "Series Start")
    @CsvBindByPosition(position = 5)
    private long seriesStart;

   /* @CsvBindByName(column = "Series End")
    @CsvBindByPosition(position = 6)
    private long seriesEnd;*/

    public long getSeriesStart() {
        return seriesStart;
    }

    public MSISDNFileModel setSeriesStart(long seriesStart) {
        this.seriesStart = seriesStart;
        return this;
    }

    /*public long getSeriesEnd() {
        return seriesEnd;
    }

    public MSISDNFileModel setSeriesEnd(long seriesEnd) {
        this.seriesEnd = seriesEnd;
        return this;
    }*/

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

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSeriesType() {
        return seriesType;
    }

    public void setSeriesType(String seriesType) {
        this.seriesType = seriesType;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MSISDNFileModel{");
        sb.append("createdOn='").append(createdOn).append('\'');
        sb.append(", modifiedOn='").append(modifiedOn).append('\'');
        sb.append(", operatorName='").append(operatorName).append('\'');
        sb.append(", userId='").append(userId).append('\'');
        sb.append(", seriesType='").append(seriesType).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
