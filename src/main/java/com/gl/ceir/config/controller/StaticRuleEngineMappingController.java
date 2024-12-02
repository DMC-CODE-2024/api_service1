package com.gl.ceir.config.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gl.ceir.config.model.app.GenricResponse;
import com.gl.ceir.config.model.app.RuleEngineMapping;
import com.gl.ceir.config.model.app.StaticRuleEngineMapping;
import com.gl.ceir.config.service.impl.StaticRuleEngineMappingServiceImpl;

//import io.swagger.annotations.ApiOperation;

@RestController
public class StaticRuleEngineMappingController {
	
	private static final Logger logger = LogManager.getLogger(StaticRuleEngineMappingController.class);
	
	@Autowired
	StaticRuleEngineMappingServiceImpl staticRuleEngineMappingServiceImpl;
	
	//@ApiOperation(value = "Save static-rule-engine-mapping", response = MappingJacksonValue.class)
	@PostMapping("/static-rule-engine-mapping")
	public MappingJacksonValue save(@RequestBody StaticRuleEngineMapping staticRuleEngineMapping) {

		logger.info("Save static rule engine mapping [" + staticRuleEngineMapping + "]");

		GenricResponse genricResponse = staticRuleEngineMappingServiceImpl.save(staticRuleEngineMapping);

		MappingJacksonValue mapping = new MappingJacksonValue(staticRuleEngineMapping);

		logger.info("Response to send= " + mapping);

		return mapping;
	}
	
	
	
	//@ApiOperation(value = "View By Id static-rule-engine-mapping", response = MappingJacksonValue.class)
	@GetMapping("/static-rule-engine-mapping/{id}")
	public MappingJacksonValue findById(@PathVariable long id) {

		logger.info("Get static rule engine mapping by id [" + id + "]");

		StaticRuleEngineMapping staticRuleEngineMapping = staticRuleEngineMappingServiceImpl.findById(id);

		MappingJacksonValue mapping = new MappingJacksonValue(staticRuleEngineMapping);

		logger.info("Response to send= " + mapping);

		return mapping;
	}
	
	
	//@ApiOperation(value = "Update By Id || static-rule-engine-mapping", response = MappingJacksonValue.class)
	@PutMapping("/static-rule-engine-mapping")
	public MappingJacksonValue updateById(@RequestBody StaticRuleEngineMapping staticRuleEngineMapping) {

		logger.info("Update static rule engine mapping [" + staticRuleEngineMapping + "]");

		GenricResponse genricResponse = staticRuleEngineMappingServiceImpl.updateById(staticRuleEngineMapping);

		MappingJacksonValue mapping = new MappingJacksonValue(staticRuleEngineMapping);

		logger.info("Response to send= " + mapping);

		return mapping;
	}
	
	
	//@ApiOperation(value = "Delete By Id || static-rule-engine-mapping", response = MappingJacksonValue.class)
	@DeleteMapping("/static-rule-engine-mapping/{id}")
	public MappingJacksonValue deleteById(@PathVariable(value = "id") Long id ) {

		logger.info("Delete static rule engine mapping [" + id + "]");

		GenricResponse genricResponse = staticRuleEngineMappingServiceImpl.deleteById(id);

		MappingJacksonValue mapping = new MappingJacksonValue(genricResponse);

		logger.info("Response to send = " + mapping);

		return mapping;
	}
}
