package com.gl.ceir.config.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gl.ceir.config.model.app.MessageConfigurationDb;
import com.gl.ceir.config.model.app.StateMgmtDb;
import com.gl.ceir.config.service.impl.StateMgmtServiceImpl;
import com.gl.ceir.config.util.Utility;

//import io.swagger.annotations.ApiOperation;

@RestController
public class StateMgmtController {

	private static final Logger logger = LogManager.getLogger(StateMgmtController.class);

	@Autowired
	StateMgmtServiceImpl stateMgmtServiceImpl;

	@Autowired
	Utility utility;

	//@ApiOperation(value = "Save || State Mgmt", response = MessageConfigurationDb.class)
	@RequestMapping(path = "/state-mgmt/", method = RequestMethod.POST)
	public MappingJacksonValue saveStateMgmt(@RequestBody StateMgmtDb stateMgmtDb) {

		logger.info("Save state mgmt = " + stateMgmtDb);

		StateMgmtDb pocessDetails = stateMgmtServiceImpl.saveStateMgmt(stateMgmtDb);

		MappingJacksonValue mapping = new MappingJacksonValue(pocessDetails);

		logger.info("Response to send = " + pocessDetails);

		return mapping;
	}
	
	//@ApiOperation(value = "View list of state by feature_id and user_type_id", response = StateMgmtDb.class)
	@GetMapping("/state-mgmt/{featureId}/{userTypeId}")
	public MappingJacksonValue getByFeatureIdAndUserTypeId(@PathVariable("featureId") Integer featureId, 
			@PathVariable("userTypeId") Integer userTypeId) {

		logger.info("Request TO view list of state by feature_id = " + featureId + " and userTypeId = "+ userTypeId);

		List<StateMgmtDb> states =  stateMgmtServiceImpl.getByFeatureIdAndUserTypeId(featureId, userTypeId);

		MappingJacksonValue mapping = new MappingJacksonValue(states);

		logger.info("state_mgmt_db ByFeatureIdAndUserTypeId = " + mapping);

		return mapping;
	}
	
	
	

}
