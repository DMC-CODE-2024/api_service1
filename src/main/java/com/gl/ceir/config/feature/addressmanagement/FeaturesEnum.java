package com.gl.ceir.config.feature.addressmanagement;

public enum FeaturesEnum {
    TA("TA", "TRCManagement", "Type Approved"), TA_EXPORT("TA_EXPORT", "Type Approved", "Export"), TA_UPLOAD("TA_UPLOAD", "Type Approved", "Upload"), TA_VIEW("TA_VIEW", "Type Approved", "View"),TA_VIEWALL("TA_VIEWALL", "Type Approved", "View All"), TA_DATA_EXPORT("TA_DATA_EXPORT", "View TA Data", "Export"), TA_DATA_VIEWALL("TA_DATA_VIEWALL", "View TA Data", "View All"),

    QA(" QA", "TRCManagement", "Qualified Agents"), QA_EXPORT("QA_EXPORT", "Qualified Agents", "Export"), QA_UPLOAD("QA_UPLOAD", "Qualified Agents", "Upload"), QA_VIEW("QA_VIEW", "Qualified Agents", "View"), QA_VIEWALL("QA_VIEWALL", "Qualified Agents", "View All"), QA_DATA_EXPORT("QA_DATA_EXPORT", "View QA Data", "Export"), QA_DATA_VIEWALL("QA_DATA_VIEWALL", "View QA Data", "View All"),

    LM_EXPORT("LM_EXPORT", "Local Manufacture IMEI", "Export"), LM_UPLOAD("LM_UPLOAD", "Local Manufacture IMEI", "Upload"), LM_VIEW("LM_VIEW", "Local Manufacture IMEI", "View"),LM_VIEWALL("LM_VIEWALL", "Local Manufacture IMEI", "View All"), LM("LM", "TRCManagement", "Local Manufacture IMEI"),

    LM_DATA_EXPORT("LM_DATA_EXPORT", "View IMEI", "Export"), LM_DATA_VIEWALL("LM_DATA_VIEWALL", "View IMEI", "View All"),

    APPROVE_DEVICE_TAC_VIEWALL("APPROVE_DEVICE_TAC_VIEWALL", "Approve Device TAC", "View All"), APPROVE_DEVICE_TAC_EXPORT("APPROVE_DEVICE_TAC_EXPORT", "Approve Device TAC", "Export"),

    APPROVE_DEVICE_TAC("APT", "TRC Management", "Approve Device TAC"), APPROVED("APPROVED", "Approve Device TAC", "Approved"), REJECT("REJECT", "Approve Device TAC", "Reject"), NULL("NULL", "Approve Device TAC", "Null"),
    EXCEPTION_LIST_WP("EXCEPTION_LIST_WP", "ListManagement", "exception_list"),
    BLOCK_LIST_WP("BLOCK_LIST_WP", "ListManagement", "black_list"),
    BLOCK_TAC_WP("BLOCK_TAC_WP", "ListManagement", "blocked_tac_list"),
    EXCEPTION_LIST("EXCEPTION_LIST", "ListManagement", "Exception IMEI List"), EXCEPTION_LIST_EXPORT("EXCEPTION_LIST_EXPORT", "Exception IMEI List", "Export"), EXCEPTION_LIST_UPLOAD("EXCEPTION_LIST_UPLOAD", "Exception IMEI List", "Upload"), EXCEPTION_LIST_VIEWALL("EXCEPTION_LIST_VIEWALL", "Exception IMEI List", "View All"), EXCEPTION_LIST_DATA_EXPORT("EXCEPTION_LIST_DATA_EXPORT", "View Exception IMEI List", "Export"), EXCEPTION_LIST_DATA_VIEWALL("EXCEPTION_LIST_DATA_VIEWALL", "View Exception IMEI List", "View All"),
    BLOCK_LIST("BLOCK_LIST", "ListManagement", "Blocked IMEI List"), BLOCK_LIST_EXPORT("BLOCK_LIST_LIST_EXPORT", "Blocked IMEI List", "Export"), BLOCK_LIST_UPLOAD("BLOCK_LIST_UPLOAD", "Blocked IMEI List", "Upload"), BLOCK_LIST_VIEWALL("BLOCK_LIST_VIEWALL", "Blocked IMEI List", "View All"),
    BLOCK_TAC("BLOCK_TAC", "ListManagement", "Blocked TAC List"), BLOCK_TAC_EXPORT("BLOCK_TAC_EXPORT", "Blocked TAC List", "Export"), BLOCK_TAC_UPLOAD("BLOCK_TAC_UPLOAD", "Blocked TAC List", "Upload"), BLOCK_TAC_VIEWALL("BLOCK_TAC_VIEWALL", "Blocked TAC List", "View All"),
    BLOCK_LIST_DATA_EXPORT("BLOCK_LIST_DATA_EXPORT", "View Blocked IMEI List", "Export"), BLOCK_LIST_DATA_VIEWALL("BLOCK_LIST_DATA_VIEWALL", "View Blocked IMEI List", "View All"),
    BLOCK_TAC_DATA_EXPORT("BLOCK_TAC_DATA_EXPORT", "View Blocked TAC List", "Export"), BLOCK_TAC_DATA_VIEWALL("BLOCK_TAC_DATA_VIEWALL", "View Blocked TAC List", "View All"),
    GRAY_LIST_DATA_EXPORT("GRAY_LIST_DATA_EXPORT", "Tracked IMEI List", "Export"), GRAY_LIST_DATA_VIEWALL("GRAY_LIST_DATA_VIEWALL", "Tracked IMEI List", "View All"),
    EIRS_LIST_MANAGEMENT("EIRS_LIST_MANAGEMENT", "ListManagement", "Exception IMEI List"), EIRS_LIST_MANAGEMENT_EXPORT("EIRS_LIST_MANAGEMENT_EXPORT", "Exception IMEI List", "Export"), EIRS_LIST_MANAGEMENT_UPLOAD("EIRS_LIST_MANAGEMENT_UPLOAD", "Exception IMEI List", "Upload"), EIRS_LIST_MANAGEMENT_VIEWALL("EIRS_LIST_MANAGEMENT_VIEWALL", "Exception IMEI List", "View All"),

    EXCEPTION_LIST_VIEW("EXCEPTION_LIST_VIEW", "Exception IMEI List", "View"),
    BLOCK_LIST_VIEW("BLOCK_LIST_VIEW", "Blocked IMEI List", "View"),
    BLOCK_TAC_VIEW("BLOCK_TAC_VIEW", "Blocked TAC List ", "View"),
    NOTIFICATION("NOTIFICATION", "View Notification", "View"), NOTIFICATION_EXPORT("NOTIFICATION_EXPORT", "View Notification", "Export"), NOTIFICATION_VIEWALL("NOTIFICATION_VIEWALL", "View Notification", "View All"),
    ADDRESS_MGMT_EXPORT("ADDRESS_MGMT_EXPORT", "Address Management", "Export"), ADDRESS_MGMT_VIEWALL("ADDRESS_MGMT_VIEWALL", "Address Management", "View All"),ADDRESS_MGMT_DELETE("ADDRESS_MGMT_DELETE", "Address Management", "Delete"),ADDRESS_MGMT_UPDATE("ADDRESS_MGMT_UPDATE", "Address Management", "Update");
    /* BLOCK_LIST, GRAY_LIST, BLOCK_TAC*/
    private String requestType;
    private String feature;

    private String subFeature;

    FeaturesEnum(String requestType, String feature, String subFeature) {
        this.feature = feature;
        this.subFeature = subFeature;
        this.requestType = requestType;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public String getSubFeature() {
        return subFeature;
    }

    public void setSubFeature(String subFeature) {
        this.subFeature = subFeature;
    }

    public static String getFeatureName(String requestType) {
        for (FeaturesEnum names : FeaturesEnum.values()) {
            if (requestType.equals(names.toString())) return names.getFeature();
        }
        return null;
    }

    public static String getSubFeatureName(String requestType) {
        for (FeaturesEnum names : FeaturesEnum.values()) {
            if (requestType.equals(names.toString())) return names.getSubFeature();
        }
        return null;
    }
}
