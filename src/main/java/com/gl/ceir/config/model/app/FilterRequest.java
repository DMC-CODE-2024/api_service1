package com.gl.ceir.config.model.app;

import jakarta.persistence.Column;
import jakarta.persistence.Transient;

public class FilterRequest {

    private Long id;
    private Integer userId;
    private Long importerId;
    private String nid;
    private String txnId;
    private String startDate;
    private String endDate;
    private Integer consignmentStatus;
    private String roleType;
    private Integer requestType;
    private Integer sourceType;
    private String userType;
    private String filteredUserType;
    private Integer featureId;
    private String featureName;
    private String subFeatureName;
    private String userName, deviceId,moduleStatus;
    private Integer userTypeId;
    private String searchString;
    private Integer taxPaidStatus;
    private Integer deviceIdType;
    private Integer deviceType;
    private Integer type;
    private Integer channel;

    private Integer status;

    private Integer operatorTypeId;
    private String origin;

    private String tac;

    // Mapping for parent child tags.
    private String tag;
    private String childTag;
    private Integer parentValue;

    private String imei;
    private Long contactNumber;
    private Integer filteredUserId;

    private String state;

    private String ruleName;

    private String remark;

    private String displayName;

    private String quantity;

    public String deviceQuantity;

    private String subject;

    private String supplierName;
    private String fileName, nationality;
    private String columnName;
    private String sort, blockingTypeFilter;

    public String visaType, visaNumber, visaExpiryDate;
    public String order, orderColumnName;
    public String description, name;
    private String publicIp;
    private String browser;
    private String value, field, tagId;

    private String graceAction, postGraceAction, ruleOrder, failedRuleActionGrace, failedRuleActionPostGrace, output;
    private String identifierType, msisdn, label, englishName, khmerName, imsi, sno;

    private String seriesType;

    private String operatorName;
    private boolean filter;

    private Integer execution_time,statusCode,count,count2,failureCount;
    private String errorMessage,moduleName,action,serverName,info,language,province,commune,district;
    private String police;

    public Integer getExecution_time() {
        return execution_time;
    }

    public String getModuleStatus() {
        return moduleStatus;
    }

    public void setModuleStatus(String moduleStatus) {
        this.moduleStatus = moduleStatus;
    }

    public void setExecution_time(Integer execution_time) {
        this.execution_time = execution_time;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getCount2() {
        return count2;
    }

    public void setCount2(Integer count2) {
        this.count2 = count2;
    }

    public Integer getFailureCount() {
        return failureCount;
    }

    public void setFailureCount(Integer failureCount) {
        this.failureCount = failureCount;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public boolean isFilter() {
        return filter;
    }

    public void setFilter(boolean filter) {
        this.filter = filter;
    }

    public String getFilteredUserType() {
        return filteredUserType;
    }

    public void setFilteredUserType(String filteredUserType) {
        this.filteredUserType = filteredUserType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getFilteredUserId() {
        return filteredUserId;
    }

    public void setFilteredUserId(Integer filteredUserId) {
        this.filteredUserId = filteredUserId;
    }

    public String getSubFeatureName() {
        return subFeatureName;
    }

    public void setSubFeatureName(String subFeatureName) {
        this.subFeatureName = subFeatureName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFeatureName() {
        return featureName;
    }

    public void setFeatureName(String featureName) {
        this.featureName = featureName;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public Long getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(Long contactNumber) {
        this.contactNumber = contactNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid;
    }

    public String getTxnId() {
        return txnId;
    }

    public FilterRequest setTxnId(String txnId) {
        this.txnId = txnId;
        return this;
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

    public Integer getTaxPaidStatus() {
        return taxPaidStatus;
    }

    public void setTaxPaidStatus(Integer taxPaidStatus) {
        this.taxPaidStatus = taxPaidStatus;
    }

    public Integer getSourceType() {
        return sourceType;
    }

    public void setSourceType(Integer sourceType) {
        this.sourceType = sourceType;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public Integer getConsignmentStatus() {
        return consignmentStatus;
    }

    public void setConsignmentStatus(Integer consignmentStatus) {
        this.consignmentStatus = consignmentStatus;
    }

    public Integer getRequestType() {
        return requestType;
    }

    public void setRequestType(Integer requestType) {
        this.requestType = requestType;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Integer getFeatureId() {
        return featureId;
    }

    public void setFeatureId(Integer featureId) {
        this.featureId = featureId;
    }

    public Integer getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(Integer userTypeId) {
        this.userTypeId = userTypeId;
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public Integer getDeviceIdType() {
        return deviceIdType;
    }

    public void setDeviceIdType(Integer deviceIdType) {
        this.deviceIdType = deviceIdType;
    }

    public Integer getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getChannel() {
        return channel;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getOperatorTypeId() {
        return operatorTypeId;
    }

    public void setOperatorTypeId(Integer operatorTypeId) {
        this.operatorTypeId = operatorTypeId;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getChildTag() {
        return childTag;
    }

    public void setChildTag(String childTag) {
        this.childTag = childTag;
    }

    public Integer getParentValue() {
        return parentValue;
    }

    public void setParentValue(Integer parentValue) {
        this.parentValue = parentValue;
    }

    public String getTac() {
        return tac;
    }

    public void setTac(String tac) {
        this.tac = tac;
    }

    public Long getImporterId() {
        return importerId;
    }

    public void setImporterId(Long importerId) {
        this.importerId = importerId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDeviceQuantity() {
        return deviceQuantity;
    }

    public void setDeviceQuantity(String deviceQuantity) {
        this.deviceQuantity = deviceQuantity;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }


    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getBlockingTypeFilter() {
        return blockingTypeFilter;
    }

    public void setBlockingTypeFilter(String blockingTypeFilter) {
        this.blockingTypeFilter = blockingTypeFilter;
    }

    public String getVisaType() {
        return visaType;
    }

    public void setVisaType(String visaType) {
        this.visaType = visaType;
    }

    public String getVisaNumber() {
        return visaNumber;
    }

    public void setVisaNumber(String visaNumber) {
        this.visaNumber = visaNumber;
    }

    public String getVisaExpiryDate() {
        return visaExpiryDate;
    }

    public void setVisaExpiryDate(String visaExpiryDate) {
        this.visaExpiryDate = visaExpiryDate;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public String getGraceAction() {
        return graceAction;
    }

    public void setGraceAction(String graceAction) {
        this.graceAction = graceAction;
    }

    public String getPostGraceAction() {
        return postGraceAction;
    }

    public void setPostGraceAction(String postGraceAction) {
        this.postGraceAction = postGraceAction;
    }

    public String getRuleOrder() {
        return ruleOrder;
    }

    public void setRuleOrder(String ruleOrder) {
        this.ruleOrder = ruleOrder;
    }

    public String getFailedRuleActionGrace() {
        return failedRuleActionGrace;
    }

    public void setFailedRuleActionGrace(String failedRuleActionGrace) {
        this.failedRuleActionGrace = failedRuleActionGrace;
    }

    public String getFailedRuleActionPostGrace() {
        return failedRuleActionPostGrace;
    }

    public void setFailedRuleActionPostGrace(String failedRuleActionPostGrace) {
        this.failedRuleActionPostGrace = failedRuleActionPostGrace;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getIdentifierType() {
        return identifierType;
    }

    public void setIdentifierType(String identifierType) {
        this.identifierType = identifierType;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
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

    public String getKhmerName() {
        return khmerName;
    }

    public void setKhmerName(String khmerName) {
        this.khmerName = khmerName;
    }

    public String getImsi() {
        return imsi;
    }

    public void setImsi(String imsi) {
        this.imsi = imsi;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public String getSeriesType() {
        return seriesType;
    }

    public void setSeriesType(String seriesType) {
        this.seriesType = seriesType;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

public String actionName;

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    
    
    public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

    public String getProvince() {
        return province;
    }

    public String getCommune() {
        return commune;
    }

    public String getDistrict() {
        return district;
    }

    public String getPolice() {
        return police;
    }

    @Override
    public String toString() {
        return "FilterRequest{" +
                "id=" + id +
                ", userId=" + userId +
                ", importerId=" + importerId +
                ", nid='" + nid + '\'' +
                ", txnId='" + txnId + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", consignmentStatus=" + consignmentStatus +
                ", roleType='" + roleType + '\'' +
                ", requestType=" + requestType +
                ", sourceType=" + sourceType +
                ", userType='" + userType + '\'' +
                ", filteredUserType='" + filteredUserType + '\'' +
                ", featureId=" + featureId +
                ", featureName='" + featureName + '\'' +
                ", subFeatureName='" + subFeatureName + '\'' +
                ", userName='" + userName + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", moduleStatus='" + moduleStatus + '\'' +
                ", userTypeId=" + userTypeId +
                ", searchString='" + searchString + '\'' +
                ", taxPaidStatus=" + taxPaidStatus +
                ", deviceIdType=" + deviceIdType +
                ", deviceType=" + deviceType +
                ", type=" + type +
                ", channel=" + channel +
                ", status=" + status +
                ", operatorTypeId=" + operatorTypeId +
                ", origin='" + origin + '\'' +
                ", tac='" + tac + '\'' +
                ", tag='" + tag + '\'' +
                ", childTag='" + childTag + '\'' +
                ", parentValue=" + parentValue +
                ", imei='" + imei + '\'' +
                ", contactNumber=" + contactNumber +
                ", filteredUserId=" + filteredUserId +
                ", state='" + state + '\'' +
                ", ruleName='" + ruleName + '\'' +
                ", remark='" + remark + '\'' +
                ", displayName='" + displayName + '\'' +
                ", quantity='" + quantity + '\'' +
                ", deviceQuantity='" + deviceQuantity + '\'' +
                ", subject='" + subject + '\'' +
                ", supplierName='" + supplierName + '\'' +
                ", fileName='" + fileName + '\'' +
                ", nationality='" + nationality + '\'' +
                ", columnName='" + columnName + '\'' +
                ", sort='" + sort + '\'' +
                ", blockingTypeFilter='" + blockingTypeFilter + '\'' +
                ", visaType='" + visaType + '\'' +
                ", visaNumber='" + visaNumber + '\'' +
                ", visaExpiryDate='" + visaExpiryDate + '\'' +
                ", order='" + order + '\'' +
                ", orderColumnName='" + orderColumnName + '\'' +
                ", description='" + description + '\'' +
                ", name='" + name + '\'' +
                ", publicIp='" + publicIp + '\'' +
                ", browser='" + browser + '\'' +
                ", value='" + value + '\'' +
                ", field='" + field + '\'' +
                ", tagId='" + tagId + '\'' +
                ", graceAction='" + graceAction + '\'' +
                ", postGraceAction='" + postGraceAction + '\'' +
                ", ruleOrder='" + ruleOrder + '\'' +
                ", failedRuleActionGrace='" + failedRuleActionGrace + '\'' +
                ", failedRuleActionPostGrace='" + failedRuleActionPostGrace + '\'' +
                ", output='" + output + '\'' +
                ", identifierType='" + identifierType + '\'' +
                ", msisdn='" + msisdn + '\'' +
                ", label='" + label + '\'' +
                ", englishName='" + englishName + '\'' +
                ", khmerName='" + khmerName + '\'' +
                ", imsi='" + imsi + '\'' +
                ", sno='" + sno + '\'' +
                ", seriesType='" + seriesType + '\'' +
                ", operatorName='" + operatorName + '\'' +
                ", filter=" + filter +
                ", execution_time=" + execution_time +
                ", statusCode=" + statusCode +
                ", count=" + count +
                ", count2=" + count2 +
                ", failureCount=" + failureCount +
                ", errorMessage='" + errorMessage + '\'' +
                ", moduleName='" + moduleName + '\'' +
                ", action='" + action + '\'' +
                ", serverName='" + serverName + '\'' +
                ", info='" + info + '\'' +
                ", language='" + language + '\'' +
                ", province='" + province + '\'' +
                ", commune='" + commune + '\'' +
                ", district='" + district + '\'' +
                ", police='" + police + '\'' +
                ", actionName='" + actionName + '\'' +
                '}';
    }
}


