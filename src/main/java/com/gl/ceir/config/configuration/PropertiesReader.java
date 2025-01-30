package com.gl.ceir.config.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PropertiesReader {

    @Value("${spring.jpa.properties.hibernate.dialect}")
    public String dialect;

    @Value("${local-ip}")
    public String localIp;

    @Value("${appdbName}")
    public String appdbName;
    @Value("${repdbName}")
    public String repdbName;
    @Value("${auddbName}")
    public String auddbName;
    @Value("${oamdbName}")
    public String oamdbName;

    @Value("${groupId}")
    public Long groupId;

    @Value("${stolenFeatureName}")
    public String stolenFeatureName;


    @Value("${audit.trail.upload}")
    public String upload;

    @Value("${audit.trail.filter}")
    public String filter;

    @Value("${audit.trail.viewAll}")
    public String viewAll;

    @Value("${audit.trail.view}")
    public String view;

    @Value("${audit.trail.export}")
    public String export;

    @Value("${audit.trail.update}")
    public String update;

    @Value("${audit.trail.delete}")
    public String delete;

    @Value("${audit.trail.add}")
    public String add;

    @Value("${sidebar.Operator_Series_Configuration}")
    public String operatorSeriesTitle;

    @Value("${sidebar.Address_Management}")
    public String addressMgmtTitle;

    @Value("${sidebar.System_Management}")
    public String systemMgmtTitle;

    @Value("${sidebar.Alert_Management}")
    public String alertMgmtTitle;

    @Value("${sidebar.Running_Alert_Management}")
    public String runningAlertMgmtTitle;

    @Value("${sidebar.Audit_Management}")
    public String auditMgmtTitle;


    @Value("${sidebar.Rule_List}")
    public String ruleTitle;

    @Value("${sidebar.Rule_Feature_Mapping}")
    public String ruleFeatureTitle;

    @Value("${sidebar.IMEI_Management}")
    public String imeiMgmtTitle;

    @Value("${sidebar.IMEI_Content_Management}")
    public String imeiContentMgmtTitle;

/*
  @Value("${serverName}")
    public String serverName;

    @Value("${module_name}")
    public String moduleName;

    @Value("${dbNamesff}")
    public   String dbName;

    @Value("${tableNameuse}")
    public   String TABLE_NAME;
    */

}
