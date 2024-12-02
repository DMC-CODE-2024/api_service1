package com.gl.ceir.config.feature.runningalert;

import com.gl.ceir.config.feature.alert.AllRequest;

public class RunningAlertFilter extends AllRequest {


    public String startDate;
    public String endDate;
    private String alertId;
    private String searchString;
    private Integer status;
    public String order, orderColumnName, description, featureName;


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getOrderColumnName() {
        return orderColumnName;
    }

    public void setOrderColumnName(String orderColumnName) {
        this.orderColumnName = orderColumnName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getAlertId() {
        return alertId;
    }

    public void setAlertId(String alertId) {
        this.alertId = alertId;
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getFeatureName() {
        return featureName;
    }

    public void setFeatureName(String featureName) {
        this.featureName = featureName;
    }

    @Override
    public String toString() {
        return "RunningAlertFilter{" +
                "startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", alertId='" + alertId + '\'' +
                ", searchString='" + searchString + '\'' +
                ", status=" + status +
                ", order='" + order + '\'' +
                ", orderColumnName='" + orderColumnName + '\'' +
                ", description='" + description + '\'' +
                ", featureName='" + featureName + '\'' +
                "} " + super.toString();
    }
}
