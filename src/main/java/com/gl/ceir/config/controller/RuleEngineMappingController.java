package com.gl.ceir.config.controller;

import com.gl.ceir.config.model.app.*;
import com.gl.ceir.config.repository.app.UsertypeRepo;
import com.gl.ceir.config.repository.aud.AuditTrailRepository;
import com.gl.ceir.config.service.impl.RuleEngineMappingServiceImpl;
import com.gl.ceir.config.service.impl.SystemConfigListServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

//import io.swagger.annotations.ApiOperation;

@RestController
public class RuleEngineMappingController {

    private static final Logger logger = LogManager.getLogger(RuleEngineMappingController.class);

    @Autowired
    RuleEngineMappingServiceImpl ruleEngineMappingServiceImpl;

    @Autowired
    AuditTrailRepository auditTrailRepository;

    @Autowired
    UsertypeRepo usertypeRepo;
    @Autowired
    SystemConfigListServiceImpl systemConfigListServiceImpl;

   // //@ApiOperation(value = "pagination View filtered rule-engine-mapping", response = RuleEngine.class)
    @PostMapping("/filter/rule-engine-mapping")
    public MappingJacksonValue getFilteredData(@RequestBody FilterRequest filterRequest,
                                               @RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
                                               @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                               @RequestParam(value = "file", defaultValue = "0") Integer file) {

        MappingJacksonValue mapping = null;
        if (file == 0) {
            logger.info("Request to view filtered rule engine mapping = " + filterRequest);
            Page<RuleEngineMapping> ruleEngineMapping = ruleEngineMappingServiceImpl.filterRuleEngineMapping(filterRequest, pageNo, pageSize, "view");
            mapping = new MappingJacksonValue(ruleEngineMapping);

            logger.info("Response of view Request = " + mapping);
        } else {
            FileDetails fileDetails = ruleEngineMappingServiceImpl.getFile(filterRequest);
            mapping = new MappingJacksonValue(fileDetails);
        }

        return mapping;
    }

   // //@ApiOperation(value = "View By Id || rule-engine-mapping", response = MappingJacksonValue.class)
    @GetMapping("/rule-engine-mapping/{id}")
    public MappingJacksonValue findAuditTrailById(@PathVariable long id) {

        logger.info("Get rule engine mapping by id [" + id + "]");

        Optional<RuleEngineMapping> ruleEngineMapping = ruleEngineMappingServiceImpl.findById(id);

        MappingJacksonValue mapping = new MappingJacksonValue(ruleEngineMapping);

        logger.info("Response to send= " + mapping);

        return mapping;
    }

   // //@ApiOperation(value = "Save || rule-engine-mapping", response = MappingJacksonValue.class)
    @PostMapping("/rule-engine-mapping")
    public GenricResponse save(@RequestBody RuleEngineMapping ruleEngineMapping) {


        logger.info("Save rule engine mapping [" + ruleEngineMapping + "]");

        GenricResponse genricResponse = ruleEngineMappingServiceImpl.saveNewRule(ruleEngineMapping);
//		MappingJacksonValue mapping = new MappingJacksonValue(ruleEngineMapping);

        if (genricResponse.getErrorCode() == 409) {
            return genricResponse;
        }

        return genricResponse;


    }

   // //@ApiOperation(value = "Update By Id || rule-engine-mapping", response = MappingJacksonValue.class)
    @PutMapping("/rule-engine-mapping")
    public GenricResponse updateById(@RequestBody RuleEngineMapping ruleEngineMapping) {

        logger.info("Update rule engine mapping [" + ruleEngineMapping + "]");

        GenricResponse genricResponse = ruleEngineMappingServiceImpl.updateByRuleOrder(ruleEngineMapping);


        MappingJacksonValue mapping = new MappingJacksonValue(ruleEngineMapping);

        logger.info("Response to send= " + mapping);

        return genricResponse;
    }

   // //@ApiOperation(value = "Delete By Id || rule-engine-mapping", response = MappingJacksonValue.class)
    @DeleteMapping("/rule-engine-mapping")
    public GenricResponse deleteById(@RequestBody RuleEngineMapping ruleEngineMapping) {

        logger.info("Delete rule engine mapping [" + ruleEngineMapping + "]");

        GenricResponse genricResponse = ruleEngineMappingServiceImpl.deleteRuleById(ruleEngineMapping);

        MappingJacksonValue mapping = new MappingJacksonValue(ruleEngineMapping);


        logger.info("Response to send = " + mapping);

        return genricResponse;
    }

    /*
     * //@ApiOperation(value = "user type list", response = MappingJacksonValue.class)
     *
     * @DeleteMapping("/rule-engine-mapping-userType") public @ResponseBody
     * List<FeatureMappingDb>
     * getUserTypeByFeatureID(@RequestParam(name="featureName") String featureName)
     * {
     *
     * logger.info("featureName for get userType API   [" + featureName + "]");
     *
     * List<FeatureMappingDb> genricResponse =
     * usertypeRepo.findDistinctByFeature(featureName);
     * logger.info("Response to send = " + genricResponse);
     *
     * return genricResponse; }
     */


   // //@ApiOperation(value = "user type list", response = MappingJacksonValue.class)
    @PostMapping("/rule-engine-mapping-userType")
    public @ResponseBody List<RuleEngineMapping> getUserTypeByFeatureID(@RequestParam(name = "featureName") String featureName, @RequestParam(name = "name") String name) {

        logger.info("Feature for get userType   [" + featureName + "]");

        List<RuleEngineMapping> genricResponse = ruleEngineMappingServiceImpl.getUserTypebyFeature(featureName, name);
        logger.info("Response Get userType by Feature = " + genricResponse);

        return genricResponse;
    }

   // //@ApiOperation(value = "  Get Feature by RuleName", response = RuleEngineMapping.class)
    @PostMapping(path = "GetfeaturebyRuleName")
    public List<RuleEngineMapping> getfeaturebyRuleName(@RequestParam(name = "ruleName", required = false) String ruleName) {
        logger.info("ruleName for get Feature   [" + ruleName + "]");
        List<RuleEngineMapping> genricResponse = ruleEngineMappingServiceImpl.getfeaturebyRuleName(ruleName);
        logger.info("Response Get Feature by RuleName = " + genricResponse);
        logger.info("Distinct features " + genricResponse.stream().filter(distinctByKey(p -> p.getFeature())).collect(Collectors.toList()));
        return genricResponse.stream().filter(distinctByKey(p -> p.getFeature())).collect(Collectors.toList());

        // return genricResponse;
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t),
                Boolean.TRUE) == null;
    }

   // //@ApiOperation(value = "Action in Grace/Post Grace Period", response = MappingJacksonValue.class)
    @PostMapping("/gracePostgraceActionMapping")
    public @ResponseBody List<SystemConfigListDb> getGracePostgraceActionMapping(@RequestParam(name = "tag") String tag) {

        logger.info("Tag for get Grace/postgrace Actions   [" + tag + "]");

        List<SystemConfigListDb> genricResponse = systemConfigListServiceImpl.getGracePostGraceActions(tag);
        logger.info("Response for get Grace/postgrace Actions = " + genricResponse);

        return genricResponse;
    }
}