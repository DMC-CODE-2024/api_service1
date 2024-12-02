package com.gl.ceir.config.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gl.ceir.config.configuration.PropertiesReader;
import com.gl.ceir.config.model.app.AllowedTacModel;
import com.gl.ceir.config.model.app.BlockedTacModel;
import com.gl.ceir.config.model.app.CheckIMEIResponseParam;
import com.gl.ceir.config.model.app.FileDetails;
import com.gl.ceir.config.model.app.FilterRequest;
import com.gl.ceir.config.repository.aud.AuditTrailRepository;
import com.gl.ceir.config.service.impl.BlockedTacViewImpl;

//import io.swagger.annotations.ApiOperation;

@RestController
public class BlockedTacController {

	@Autowired
	PropertiesReader propertiesReader;
	
	@Autowired
	AuditTrailRepository auditTrailRepository;
	
	@Autowired
	BlockedTacViewImpl  blockedTacImpl;
	
	private static final Logger logger = LogManager.getLogger(BlockedTacController.class);

	//@ApiOperation(value = "Paginated view of check IMEI param .", response = BlockedTacController.class)
	@PostMapping("/filter/BlockedTacView")
	public MappingJacksonValue paginatedViewOfGreyList(@RequestBody FilterRequest filterRequest,
			@RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
			@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
			@RequestParam(value = "file", defaultValue = "0") Integer file) {
		MappingJacksonValue mapping = null;
		logger.info(" request  of Blocked view" + filterRequest);
		if(file == 0) {
			Page<BlockedTacModel> page = blockedTacImpl.filterBlockListIMEI(filterRequest, pageNo, pageSize);
			mapping = new MappingJacksonValue(page);
			logger.info("Response to send = " + page.toString());
		}else {
			FileDetails fileDetails = blockedTacImpl.exportFile_System(filterRequest);
			mapping = new MappingJacksonValue(fileDetails);
		}
		
		return mapping;
	}
	
	//@ApiOperation(value = "blocked tac  view Data using id", response = BlockedTacController.class)
	@RequestMapping(path = "/blockedTac/viewTac", method = RequestMethod.POST)
	public MappingJacksonValue findBlockedTacDetailsByTac(@RequestBody BlockedTacModel blockedTacModel) {

		logger.info("request to  check alblockedlowed Tac details by tac="+blockedTacModel);
		
		
		BlockedTacModel  blockedTacResponse = blockedTacImpl.findByTac(blockedTacModel);
		
		MappingJacksonValue mapping = new MappingJacksonValue(blockedTacResponse);
		/* mapping.setSerializationView(CheckIMEIResponseParam.class); */
		logger.info("Blocked Response to send="+blockedTacResponse);
		logger.info("mapping response to send="+blockedTacResponse);
		return mapping;
	}
}
