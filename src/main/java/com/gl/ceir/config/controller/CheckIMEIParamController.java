package com.gl.ceir.config.controller;

import java.util.List;
import java.util.Optional;

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
import com.gl.ceir.config.model.app.CheckIMEIResponseParam;
import com.gl.ceir.config.model.app.FileDetails;
import com.gl.ceir.config.model.app.FilterRequest;
import com.gl.ceir.config.model.app.GenricResponse;
import com.gl.ceir.config.model.app.SystemConfigurationDb;
import com.gl.ceir.config.model.aud.AuditTrail;
import com.gl.ceir.config.model.constants.Features;
import com.gl.ceir.config.model.constants.SubFeatures;
import com.gl.ceir.config.repository.app.CheckIMEIResponse;
import com.gl.ceir.config.repository.aud.AuditTrailRepository;
import com.gl.ceir.config.service.impl.CheckIMEIParamImpl;

//import io.swagger.annotations.ApiOperation;

@RestController
public class CheckIMEIParamController {
	
	@Autowired
	CheckIMEIResponse checkIMEIResponse;
	
	@Autowired
	CheckIMEIParamImpl checkIMEIParamImpl;
	
	@Autowired
	PropertiesReader propertiesReader;
	
	@Autowired
	AuditTrailRepository auditTrailRepository;
	
	private static final Logger logger = LogManager.getLogger(ConfigurationController.class);

	

	//@ApiOperation(value = "Paginated view of check IMEI param .", response = CheckIMEIResponseParam.class)
	@PostMapping("/filter/checkIMEIparam")
	public MappingJacksonValue paginatedViewOfSystemConfig(@RequestBody FilterRequest filterRequest,
			@RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
			@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
			@RequestParam(value = "file", defaultValue = "0") Integer file) {
		MappingJacksonValue mapping = null;
		logger.info("Paginated view of check IMEI param " + filterRequest);
		if(file == 0) {
			Page<CheckIMEIResponseParam> page = checkIMEIParamImpl.filterCheckIMEIConfiguration(filterRequest, pageNo, pageSize);
			mapping = new MappingJacksonValue(page);
			logger.info("Response to send -= " + mapping.getValue());
		}else {
			FileDetails fileDetails = checkIMEIParamImpl.exportFile_System(filterRequest);
			mapping = new MappingJacksonValue(fileDetails);
		}
		
		return mapping;
	}

	//@ApiOperation(value = "Check IMEI param  view Data using id", response = CheckIMEIResponseParam.class)
	@RequestMapping(path = "/checkIMEIParam/viewTag", method = RequestMethod.POST)
	public MappingJacksonValue findCheckIMEIParamDetailsByTag(@RequestBody CheckIMEIResponseParam checkIMEIResponseParam) {

		logger.info("request to  check IMEI params  id="+checkIMEIResponseParam);
		
		
		CheckIMEIResponseParam  checkIMEIParamResponse = checkIMEIParamImpl.findById(checkIMEIResponseParam);
		checkIMEIParamResponse.setValue(checkIMEIParamResponse.getValue().replace("$LOCAL_IP",propertiesReader.localIp) );
		MappingJacksonValue mapping = new MappingJacksonValue(checkIMEIParamResponse);
		/* mapping.setSerializationView(CheckIMEIResponseParam.class); */
		logger.info("Response to send="+checkIMEIParamResponse);
		logger.info("mapping response to send="+checkIMEIParamResponse);
		return mapping;
	}
	
	//@ApiOperation(value = "getDistinctIMEIFeatureName")
	@GetMapping("/getDistinctIMEIFeatureName")
	public ResponseEntity<?> getDistinctIMEIFeatureName() {
		Optional<List<String>> list = Optional.ofNullable(checkIMEIResponse.findDistinctFeatureName());
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	//@ApiOperation(value = "getDistinctTypeName")
	@GetMapping("/getDistinctTypeName")
	public ResponseEntity<?> getDistinctTypeName() {
		Optional<List<String>> list = Optional.ofNullable(checkIMEIResponse.findDistinctType());
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	//@ApiOperation(value = "check IMEI Param update Data using id", response = GenricResponse.class)
	@RequestMapping(path = "/checkIMEIParam/update", method = RequestMethod.PUT)
	public GenricResponse updateSytem(@RequestBody CheckIMEIResponseParam checkIMEIResponseParam) {

		logger.info("check IMEI Param update request="+checkIMEIResponseParam);


		GenricResponse GenricResponse =	checkIMEIParamImpl.updateCheckIMEIInfo(checkIMEIResponseParam);
		if(GenricResponse.getErrorCode()==200) {
			auditTrailRepository.save(new AuditTrail(checkIMEIResponseParam.getUserId(), checkIMEIResponseParam.getUserName(), 
					Long.valueOf(checkIMEIResponseParam.getUserTypeId()), checkIMEIResponseParam.getUserType(), Long.valueOf(checkIMEIResponseParam.getFeatureId()),
					Features.Check_IMEI_Messages, SubFeatures.UPDATE, "", "NA",checkIMEIResponseParam.getRoleType(),checkIMEIResponseParam.getPublicIp(),checkIMEIResponseParam.getBrowser()));
			logger.info("Check IMEI update : successully inserted in audit trail ");
		}
		logger.info("Update sytem config response="+GenricResponse);

		return GenricResponse;
	}
}
