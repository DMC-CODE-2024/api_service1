package com.gl.ceir.config.dto;



public class AuditTraildDTO {
    private long userId;
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
    private FilterDTO filterDTO;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(Long userTypeId) {
        this.userTypeId = userTypeId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Long getFeatureId() {
        return featureId;
    }

    public void setFeatureId(Long featureId) {
        this.featureId = featureId;
    }

    public String getFeatureName() {
        return featureName;
    }

    public void setFeatureName(String featureName) {
        this.featureName = featureName;
    }

    public String getSubFeature() {
        return subFeature;
    }

    public void setSubFeature(String subFeature) {
        this.subFeature = subFeature;
    }

    public String getjSessionId() {
        return jSessionId;
    }

    public void setjSessionId(String jSessionId) {
        this.jSessionId = jSessionId;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public String getPublicIp() {
        return publicIp;
    }

    public void setPublicIp(String publicIp) {
        this.publicIp = publicIp;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getTxnId() {
        return txnId;
    }

    public void setTxnId(String txnId) {
        this.txnId = txnId;
    }

    public FilterDTO getFilterDTO() {
        return filterDTO;
    }

    public void setFilterDTO(FilterDTO filterDTO) {
        this.filterDTO = filterDTO;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AuditTraildDTO{");
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
        sb.append(", filterDTO=").append(filterDTO);
        sb.append('}');
        return sb.toString();
    }
}
