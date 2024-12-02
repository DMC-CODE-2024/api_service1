package com.gl.ceir.config.feature.addressmanagement;

public class AuditTrailModel {
    private Long userId;
    private String userName;
    private Long userTypeId;
    private String userType;
    private Long featureId;
    private String featureName;
    private String subFeature;
    private String jSessionId;
    private String roleType;
    private String publicIp;
    private String browser;
    private String details;
    private String txnId;
    private String startDate;
    private String endDate;
    private String columnName;
    private String sort;

    public Long getUserId() {
        return userId;
    }

    public AuditTrailModel setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public AuditTrailModel setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public Long getUserTypeId() {
        return userTypeId;
    }

    public AuditTrailModel setUserTypeId(Long userTypeId) {
        this.userTypeId = userTypeId;
        return this;
    }

    public String getUserType() {
        return userType;
    }

    public AuditTrailModel setUserType(String userType) {
        this.userType = userType;
        return this;
    }

    public Long getFeatureId() {
        return featureId;
    }

    public AuditTrailModel setFeatureId(Long featureId) {
        this.featureId = featureId;
        return this;
    }

    public String getFeatureName() {
        return featureName;
    }

    public AuditTrailModel setFeatureName(String featureName) {
        this.featureName = featureName;
        return this;
    }

    public String getSubFeature() {
        return subFeature;
    }

    public AuditTrailModel setSubFeature(String subFeature) {
        this.subFeature = subFeature;
        return this;
    }

    public String getjSessionId() {
        return jSessionId;
    }

    public AuditTrailModel setjSessionId(String jSessionId) {
        this.jSessionId = jSessionId;
        return this;
    }

    public String getRoleType() {
        return roleType;
    }

    public AuditTrailModel setRoleType(String roleType) {
        this.roleType = roleType;
        return this;
    }

    public String getPublicIp() {
        return publicIp;
    }

    public AuditTrailModel setPublicIp(String publicIp) {
        this.publicIp = publicIp;
        return this;
    }

    public String getBrowser() {
        return browser;
    }

    public AuditTrailModel setBrowser(String browser) {
        this.browser = browser;
        return this;
    }

    public String getDetails() {
        return details;
    }

    public AuditTrailModel setDetails(String details) {
        this.details = details;
        return this;
    }

    public String getTxnId() {
        return txnId;
    }

    public AuditTrailModel setTxnId(String txnId) {
        this.txnId = txnId;
        return this;
    }

    public String getStartDate() {
        return startDate;
    }

    public AuditTrailModel setStartDate(String startDate) {
        this.startDate = startDate;
        return this;
    }

    public String getEndDate() {
        return endDate;
    }

    public AuditTrailModel setEndDate(String endDate) {
        this.endDate = endDate;
        return this;
    }

    public String getColumnName() {
        return columnName;
    }

    public AuditTrailModel setColumnName(String columnName) {
        this.columnName = columnName;
        return this;
    }

    public String getSort() {
        return sort;
    }

    public AuditTrailModel setSort(String sort) {
        this.sort = sort;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AuditTrailModel{");
        sb.append("userId=").append(userId);
        sb.append(", userName='").append(userName).append('\'');
        sb.append(", userTypeId=").append(userTypeId);
        sb.append(", userType='").append(userType).append('\'');
        sb.append(", featureId=").append(featureId);
        sb.append(", featureName='").append(featureName).append('\'');
        sb.append(", subFeature='").append(subFeature).append('\'');
        sb.append(", jSessionId='").append(jSessionId).append('\'');
        sb.append(", roleType='").append(roleType).append('\'');
        sb.append(", publicIp='").append(publicIp).append('\'');
        sb.append(", browser='").append(browser).append('\'');
        sb.append(", details='").append(details).append('\'');
        sb.append(", txnId='").append(txnId).append('\'');
        sb.append(", startDate='").append(startDate).append('\'');
        sb.append(", endDate='").append(endDate).append('\'');
        sb.append(", columnName='").append(columnName).append('\'');
        sb.append(", sort='").append(sort).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
