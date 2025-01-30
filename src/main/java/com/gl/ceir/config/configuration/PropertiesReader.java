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


/*    @Value("${serverName}")
    public String serverName;

    @Value("${module_name}")
    public String moduleName;

    @Value("${dbNamesff}")
    public   String dbName;

    @Value("${tableNameuse}")
    public   String TABLE_NAME;*/

}
