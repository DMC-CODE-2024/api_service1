package com.gl.ceir.config.controller;

import com.gl.ceir.config.externalproperties.FeatureNameMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gl.ceir.config.model.app.FileDetails;
import com.gl.ceir.config.model.app.FilterRequest;
import com.gl.ceir.config.model.app.GenricResponse;
import com.gl.ceir.config.model.app.SystemConfigListDb;
import com.gl.ceir.config.model.aud.AuditTrail;
import com.gl.ceir.config.model.constants.Features;
import com.gl.ceir.config.model.constants.SubFeatures;
import com.gl.ceir.config.repository.aud.AuditTrailRepository;
import com.gl.ceir.config.service.impl.SystemConfigListServiceImpl;

//import io.swagger.annotations.ApiOperation;

@RestController
public class SystemConfigListController {

    private static final Logger logger = LogManager.getLogger(SystemConfigListController.class);

    @Autowired
    SystemConfigListServiceImpl systemConfigListServiceImpl;

    @Autowired
    AuditTrailRepository auditTrailRepository;
    @Autowired
    private FeatureNameMap featureNameMap;

    //@ApiOperation(value = "Save || system-config-list", response = SystemConfigListDb.class)
    @PostMapping("/save/system-config-list")
    public MappingJacksonValue save(@RequestBody SystemConfigListDb systemConfigListDb) {

        logger.info("Save system-config-list request [" + systemConfigListDb + "]");

        GenricResponse genricResponse = systemConfigListServiceImpl.saveSystemConfigList(systemConfigListDb);

        MappingJacksonValue mapping = new MappingJacksonValue(systemConfigListDb);

        logger.info("Response to send for save on system-config-list [ " + genricResponse + "] = " + mapping);

        return mapping;
    }

    //@ApiOperation(value = "Update || system-config-list", response = SystemConfigListDb.class)
    @PutMapping("/system-config-list")
    public MappingJacksonValue update(@RequestBody SystemConfigListDb systemConfigListDb) {

        logger.info("Get system-config-list request [" + systemConfigListDb + "]");

        GenricResponse genricResponse = systemConfigListServiceImpl.updateSystemConfigList(systemConfigListDb);

        MappingJacksonValue mapping = new MappingJacksonValue(systemConfigListDb);

        logger.info("Response to send for save on system-config-list [ " + genricResponse + "] = " + mapping);

        return mapping;
    }


    //@ApiOperation(value = "pagination View filtered system-config-list", response = SystemConfigListDb.class)
    @PostMapping("/filter/system-config-list")
    public MappingJacksonValue withPaginationConsignments(@RequestBody FilterRequest filterRequest,
                                                          @RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
                                                          @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                          @RequestParam(value = "file", defaultValue = "0") Integer file) {

        MappingJacksonValue mapping = null;
        if (file == 0) {
            logger.info("Request to view filtered audit trail = " + filterRequest);
            Page<SystemConfigListDb> auditTrail = systemConfigListServiceImpl.filter(filterRequest, pageNo, pageSize);
            mapping = new MappingJacksonValue(auditTrail);
        } else {
            logger.info("Request to export filtered audit trail = " + filterRequest);
            FileDetails fileDetails = systemConfigListServiceImpl.getFilteredAuditTrailInFile(filterRequest);
            mapping = new MappingJacksonValue(fileDetails);
            auditTrailRepository.save(new AuditTrail(filterRequest.getUserId(), filterRequest.getUserName(), 0L, filterRequest.getUserType(), 0L,
                    featureNameMap.get("SYSTEM_MGMT"), featureNameMap.get("EXPORT"), "", "NA", filterRequest.getUserType(), filterRequest.getPublicIp(), filterRequest.getBrowser()));
        }

        logger.info("Response of view Request = " + mapping);

        return mapping;
    }

    //@ApiOperation(value = "View By Id || system-config-list", response = String.class)
    @PostMapping("/get/system-config-list")
    public MappingJacksonValue findAuditTrailById(@RequestBody FilterRequest filterRequest) {

        logger.info("Get system-config-list request [" + filterRequest + "]");

        //GenricResponse systemConfigListDb = systemConfigListServiceImpl.findById(filterRequest);

        MappingJacksonValue mapping = new MappingJacksonValue(systemConfigListServiceImpl.findById(filterRequest));

        logger.info("Response to send on system-config-list [ " + mapping + "]");

        return mapping;
    }

    //@ApiOperation(value = "All tags list || system-config-list", response = String.class)
    @PostMapping("/tags/system-config-list")
    public MappingJacksonValue getTagsList(@RequestBody FilterRequest filterRequest) {

        logger.info("Get system-config-list." + filterRequest);

        GenricResponse uniqueTags = systemConfigListServiceImpl.getTagsList(filterRequest);

        MappingJacksonValue mapping = new MappingJacksonValue(uniqueTags);

        logger.info("Response to send for all tags of system-config-list = " + mapping);

        return mapping;
    }

    //@ApiOperation(value = "All tags list with display name|| system-config-list", response = String.class)
    @PostMapping("/projection/tags/system-config-list")
    public MappingJacksonValue findDistinctTagsWithDescription(@RequestBody FilterRequest filterRequest) {

        logger.info("Get findDistinctTagsWithDescription ." + filterRequest);

        GenricResponse uniqueTags = systemConfigListServiceImpl.findDistinctTagsWithDescription(filterRequest);

        MappingJacksonValue mapping = new MappingJacksonValue(uniqueTags);

        logger.info("Response to send for all tags of system-config-list = " + mapping);

        return mapping;
    }

    //@ApiOperation(value = "All tags list || system-config-list", response = String.class)
    @DeleteMapping("/tags/system-config-list")
    public MappingJacksonValue deleteValue(@RequestBody FilterRequest filterRequest) {

        logger.info("Delete system-config-list. " + filterRequest);

        GenricResponse uniqueTags = systemConfigListServiceImpl.deleteValue(filterRequest);

        MappingJacksonValue mapping = new MappingJacksonValue(uniqueTags);

        logger.info("Response to send for all tags of system-config-list = " + mapping);

        return mapping;
    }

}