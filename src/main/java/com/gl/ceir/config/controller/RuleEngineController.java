package com.gl.ceir.config.controller;

import java.util.List;

import com.gl.ceir.config.externalproperties.FeatureNameMap;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gl.ceir.config.model.app.FileDetails;
import com.gl.ceir.config.model.app.FilterRequest;
import com.gl.ceir.config.model.app.GenricResponse;
import com.gl.ceir.config.model.app.RuleEngine;
import com.gl.ceir.config.model.aud.AuditTrail;
import com.gl.ceir.config.model.constants.Features;
import com.gl.ceir.config.model.constants.SubFeatures;
import com.gl.ceir.config.repository.aud.AuditTrailRepository;
import com.gl.ceir.config.service.impl.RuleEngineServiceImpl;

//import io.swagger.annotations.ApiOperation;
import java.util.Optional;

@RestController
public class RuleEngineController {

    private static final Logger logger = LogManager.getLogger(RuleEngineController.class);

    @Autowired
    RuleEngineServiceImpl ruleEngineServiceImpl;

    @Autowired
    AuditTrailRepository auditTrailRepository;

    @Autowired
    FeatureNameMap featureNameMap;


    @Tag(name = "Rule List", description = "System Configuration Module API")
    @Operation(
            summary = "Fetch and export all record",
            description = "fetch all entities from a data source and export the records into a CSV file.")
    @PostMapping("/filter/rule-engine")
    public MappingJacksonValue getFilteredData(@RequestBody FilterRequest filterRequest,
                                               @RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
                                               @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                               @RequestParam(value = "file", defaultValue = "0") Integer file) {

        MappingJacksonValue mapping = null;
        if (file == 0) {
            logger.info("Request to view filtered rule engine = " + filterRequest);
            Page<RuleEngine> ruleEngine = ruleEngineServiceImpl.filterRuleEngine(filterRequest, pageNo, pageSize, "view");
            mapping = new MappingJacksonValue(ruleEngine);

            logger.info("Response of view Request = " + mapping);
        } else {
            FileDetails fileDetails = ruleEngineServiceImpl.getFile(filterRequest);
            mapping = new MappingJacksonValue(fileDetails);


        }
        return mapping;
    }

    @Tag(name = "Rule List", description = "System Configuration Module API")
    @Operation(
            summary = "Fetch single record based on Id",
            description = "Fetches record based on Id from data source")
    @GetMapping("/rule-engine/{id}")
    public MappingJacksonValue findAuditTrailById(@PathVariable long id) {

        logger.info("Get rule engine by id [" + id + "]");

        Optional<RuleEngine> ruleEngine = ruleEngineServiceImpl.findById(id);

        MappingJacksonValue mapping = new MappingJacksonValue(ruleEngine);

        logger.info("Response to send= " + mapping);

        return mapping;
    }

    @Tag(name = "Rule List", description = "System Configuration Module API")
    @Operation(
            summary = "Fetch all rule list",
            description = "Fetches all rule list from data source")
    @GetMapping("/all/rule-engine")
    public MappingJacksonValue getFilteredData() {

        MappingJacksonValue mapping = null;

        logger.info("Request to view all rule engine");
        List<RuleEngine> ruleEngine = ruleEngineServiceImpl.allRuleNames();
        mapping = new MappingJacksonValue(ruleEngine);

        logger.info("Response of view Request = " + mapping);

        return mapping;
    }

    @Tag(name = "Rule List", description = "System Configuration Module API")
    @Operation(
            summary = "Update record to the data source",
            description = "Update the record based on the received request")
    @PutMapping("/rule-engine")
    public MappingJacksonValue updateAuditTrailById(@RequestBody RuleEngine ruleEngine) {
        String requestType = featureNameMap.get("RULE");
        logger.info("Update rule engine [" + ruleEngine + "]");

        GenricResponse genricResponse = ruleEngineServiceImpl.updateById(ruleEngine);

        MappingJacksonValue mapping = new MappingJacksonValue(ruleEngine);

        logger.info("Response to send= " + mapping);
        if (genricResponse.getErrorCode() == 0) {
            auditTrailRepository.save(new AuditTrail(Long.valueOf(ruleEngine.getUserId()),
                    ruleEngine.getUserName(), Long.valueOf(ruleEngine.getUserTypeId()),
                    "SystemAdmin", Long.valueOf(ruleEngine.getFeatureId()),
                    featureNameMap.get(requestType), featureNameMap.get("UPDATE"), "", "NA",
                    ruleEngine.getRoleType(), ruleEngine.getPublicIp(), ruleEngine.getBrowser()));

        }

        return mapping;
    }

}