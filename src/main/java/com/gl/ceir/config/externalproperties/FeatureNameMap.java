package com.gl.ceir.config.externalproperties;

import com.gl.ceir.config.configuration.PropertiesReader;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class FeatureNameMap {
    @Autowired
    PropertiesReader propertiesReader;

    private final Logger log = LoggerFactory.getLogger(FeatureNameMap.class);
    public Map<String, String> globalMap = new HashMap<>();

    public String get(String key) {
        return globalMap.get(key);
    }

    public void remove(String key) {
        globalMap.remove(key);
    }

    public boolean containsKey(String key) {
        return globalMap.containsKey(key);
    }

    public void forEach() {
        globalMap.forEach((key, value) -> log.info("globalMap [" + key + ": " + value + "]"));
    }

    @PostConstruct
    public void init() {
        globalMap.put("UPLOAD", propertiesReader.upload);
        globalMap.put("VIEWALL", propertiesReader.viewAll);
        globalMap.put("VIEW", propertiesReader.view);
        globalMap.put("EXPORT", propertiesReader.export);
        globalMap.put("UPDATE", propertiesReader.update);
        globalMap.put("ADD", propertiesReader.add);
        globalMap.put("FILTER", propertiesReader.filter);
        globalMap.put("OPERATOR_SERIES", propertiesReader.operatorSeriesTitle);
        globalMap.put("ADDRESS_MGMT", propertiesReader.addressMgmtTitle);
        globalMap.put("SYSTEM_MGMT", propertiesReader.systemMgmtTitle);
        globalMap.put("ALERT_MGMT", propertiesReader.alertMgmtTitle);
        globalMap.put("RUNNING_ALERT_MGMT", propertiesReader.runningAlertMgmtTitle);
        globalMap.put("AUDIT_MGMT", propertiesReader.auditMgmtTitle);
        globalMap.put("RULE", propertiesReader.ruleTitle);
        globalMap.put("RULE_FEATURE", propertiesReader.ruleFeatureTitle);
        globalMap.put("IMEI_MGMT", propertiesReader.imeiMgmtTitle);
        globalMap.put("IMEI_CONTENT_MGMT", propertiesReader.imeiContentMgmtTitle);


    }
}
