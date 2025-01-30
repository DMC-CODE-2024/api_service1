package com.gl.ceir.config.controller;


import java.util.List;
import java.util.Optional;

import com.gl.ceir.config.externalproperties.FeatureNameMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gl.ceir.config.configuration.PropertiesReader;
import com.gl.ceir.config.model.app.ChecKIMEIContent;
import com.gl.ceir.config.model.app.CheckIMEIResponseParam;
import com.gl.ceir.config.model.app.FileDetails;
import com.gl.ceir.config.model.app.FilterRequest;
import com.gl.ceir.config.model.app.GenricResponse;
import com.gl.ceir.config.model.aud.AuditTrail;
import com.gl.ceir.config.model.constants.Features;
import com.gl.ceir.config.model.constants.SubFeatures;
import com.gl.ceir.config.repository.app.CheckIMEIContentRepo;
import com.gl.ceir.config.repository.aud.AuditTrailRepository;
import com.gl.ceir.config.service.impl.CheckMEIContentImpl;

//import io.swagger.annotations.ApiOperation;

@RestController
public class CheckIMEIContext {

	@Autowired
	PropertiesReader propertiesReader;

	@Autowired
	AuditTrailRepository auditTrailRepository;

	@Autowired
	CheckMEIContentImpl checkMEIContentImpl;



	@Autowired
	CheckIMEIContentRepo checkIMEIContentRepo;

	private static final Logger logger = LogManager.getLogger(ConfigurationController.class);

	@Autowired
	private FeatureNameMap featureNameMap;
	String requestType = "IMEI_CONTENT_MGMT";

	//@ApiOperation(value = "Paginated view of check IMEI param .", response = CheckIMEIResponseParam.class)
	@PostMapping("/filter/checkIMEIContent")
	public MappingJacksonValue paginatedViewOfSystemConfig(@RequestBody FilterRequest filterRequest,
														   @RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
														   @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
														   @RequestParam(value = "file", defaultValue = "0") Integer file) {
		MappingJacksonValue mapping = null;
		logger.info(" request  of check IMEI content view" + filterRequest);
		if(file == 0) {
			Page<ChecKIMEIContent> page = checkMEIContentImpl.filterCheckIMEIConfiguration(filterRequest, pageNo, pageSize);
			mapping = new MappingJacksonValue(page);
			logger.info("Response to send = " + page.toString());
		}else {
			FileDetails fileDetails = checkMEIContentImpl.exportFile_System(filterRequest);
			mapping = new MappingJacksonValue(fileDetails);
		}

		return mapping;
	}

	//@ApiOperation(value = "getDistinctIMEIContentFeatureName")
	@GetMapping("/getDistinctIMEIContentFeatureName")
	public ResponseEntity<?> getDistinctIMEIContentFeatureName() {
		Optional<List<String>> list;
		if(propertiesReader.dialect.toLowerCase().contains("mysql")) {
			list = Optional.ofNullable(checkIMEIContentRepo.findDistinctFeatureName());
		}
		else {
			list = Optional.ofNullable(checkIMEIContentRepo.findDistinctFeatureName());
		}


		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	//@ApiOperation(value = "Check IMEI Content  view Data using id", response = CheckIMEIResponseParam.class)
	@RequestMapping(path = "/checkIMEIContent/viewTag", method = RequestMethod.POST)
	public MappingJacksonValue findCheckIMEIContentDetailsByTag(@RequestBody ChecKIMEIContent checKIMEIContentRequest) {

		logger.info("request to  check IMEI content  id="+checKIMEIContentRequest);
		ChecKIMEIContent  checkIMEIParamResponse = checkMEIContentImpl.findById(checKIMEIContentRequest);
		MappingJacksonValue mapping = new MappingJacksonValue(checkIMEIParamResponse);
		logger.info("Response to send="+checkIMEIParamResponse);
		logger.info("mapping response to send="+checkIMEIParamResponse);
		return mapping;
	}

	//@ApiOperation(value = "check IMEI Content update Data using id", response = GenricResponse.class)
	@RequestMapping(path = "/checkIMEIContent/update", method = RequestMethod.PUT)
	public GenricResponse updateSytem(@RequestBody ChecKIMEIContent checkIMEIResponseParam) {

		logger.info("check IMEI Content update request="+checkIMEIResponseParam);


		GenricResponse GenricResponse =	checkMEIContentImpl.updateCheckIMEIInfo(checkIMEIResponseParam);
		if(GenricResponse.getErrorCode()==200) {
			auditTrailRepository.save(new AuditTrail(checkIMEIResponseParam.getUserId(), checkIMEIResponseParam.getUserName(),
					Long.valueOf(checkIMEIResponseParam.getUserTypeId()), "SystemAdmin", Long.valueOf(checkIMEIResponseParam.getFeatureId()),
					featureNameMap.get(requestType), featureNameMap.get("UPDATE"), "", "NA",checkIMEIResponseParam.getRoleType(),checkIMEIResponseParam.getPublicIp(),checkIMEIResponseParam.getBrowser()));
			logger.info("Check IMEI content update : successfully inserted in audit trail ");
		}
		logger.info("Update Check IMEI content  response="+GenricResponse);

		return GenricResponse;
	}
}
