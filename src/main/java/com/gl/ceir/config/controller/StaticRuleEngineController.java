package com.gl.ceir.config.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gl.ceir.config.model.app.GenricResponse;
import com.gl.ceir.config.model.app.StaticRuleEngineMapping;
import com.gl.ceir.config.service.impl.RuleEngineMappingServiceImpl;
import com.gl.ceir.config.service.impl.StaticRuleServiceImpl;

@RestController
@CrossOrigin
@RequestMapping("static_rule_engine")
public class StaticRuleEngineController {

	private static final Logger logger = LogManager.getLogger(StaticRuleEngineController.class);

	@Autowired
	StaticRuleServiceImpl staticRuleServiceImpl;
	
	@PostMapping
	public GenricResponse save(@RequestBody StaticRuleEngineMapping staticRuleEngineMapping) {
		logger.info("request::::"+staticRuleEngineMapping);
		GenricResponse genricResponse = staticRuleServiceImpl.save(staticRuleEngineMapping);
		return genricResponse;
	}
}
