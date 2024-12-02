package com.gl.ceir.config.feature.alert;

public class AlertDbFilter extends AllRequest {

    public String startDate;
    public String endDate;
    private String alertId;
    private String searchString;
    public String order, orderColumnName, feature, description;
    private String sort, columnName;


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
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

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    @Override
    public String toString() {
        return "AlertDbFilter [startDate=" + startDate + ", endDate=" + endDate + ", alertId=" + alertId
                + ", searchString=" + searchString + ", order=" + order + ", orderColumnName=" + orderColumnName
                + ", feature=" + feature + ", description=" + description + ", sort=" + sort + ", columnName="
                + columnName + "]";
    }


}
