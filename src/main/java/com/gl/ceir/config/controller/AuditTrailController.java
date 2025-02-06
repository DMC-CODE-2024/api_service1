package com.gl.ceir.config.controller;

import java.util.List;
import java.util.Optional;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.gl.ceir.config.model.app.FileDetails;
import com.gl.ceir.config.model.app.FilterRequest;
import com.gl.ceir.config.model.app.SystemConfigurationDb;
import com.gl.ceir.config.model.aud.AuditTrail;
import com.gl.ceir.config.repository.aud.AuditTrailRepository;
import com.gl.ceir.config.service.impl.AuditTrailServiceImpl;

//import io.swagger.annotations.ApiOperation;

@RestController
public class AuditTrailController {

	private static final Logger logger = LogManager.getLogger(AuditTrailController.class);

	@Autowired
	AuditTrailServiceImpl auditTrailServiceImpl;

	@Autowired
	AuditTrailRepository auditTrail;

	@Tag(name = "Audit Trail Management", description = "System Configuration Module API")
	@Operation(
			summary = "Fetch all record",
			description = "Fetches all address entities and their data from data source")
	@PostMapping("/filter/audit-trail")
	public MappingJacksonValue withPaginationConsignments(@RequestBody FilterRequest filterRequest,
			@RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
			@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
			@RequestParam(value = "file", defaultValue = "0") Integer file) {

		MappingJacksonValue mapping = null;
		if(file == 0) {
			logger.info("Request to view filtered audit trail = " + filterRequest);
			Page<AuditTrail> auditTrail =  auditTrailServiceImpl.filterAuditTrail(filterRequest, pageNo, pageSize);
			mapping = new MappingJacksonValue(auditTrail);
		}else {
			logger.info("Request to export filtered audit trail = " + filterRequest);
			FileDetails fileDetails = auditTrailServiceImpl.getFilteredAuditTrailInFile(filterRequest);
			mapping = new MappingJacksonValue(fileDetails);
		}

		logger.info("Response of view Request = " + mapping);

		return mapping;
	}


	@Tag(name = "Audit Trail Management", description = "System Configuration Module API")
	@Operation(
			summary = "Fetch single record based on Id",
			description = "Fetches record based on Id from data source")
	@GetMapping("/audit-trail/{id}")
	public MappingJacksonValue findAuditTrailById(@PathVariable long id) {

		logger.info("Get audit trail by id [" + id + "]");

		AuditTrail auditTrail = auditTrailServiceImpl.findById(id);

		MappingJacksonValue mapping = new MappingJacksonValue(auditTrail);

		logger.info("Response to send= " + mapping);

		return mapping;
	}

	@Tag(name = "Audit Trail Management", description = "System Configuration Module API")
	@Operation(
			summary = "Fetch distinct value from the data source",
			description = "Fetch distinct values of features based on the received request")
	@GetMapping("/getDistinctAuditFeatureName")
	public ResponseEntity<?> getDistinctAuditFeatureName() {
		Optional<List<String>> list = Optional.ofNullable(auditTrail.findDistinctFeatureName());
		return new ResponseEntity<>(list, HttpStatus.OK);
	}


	@Tag(name = "Audit Trail Management", description = "System Configuration Module API")
	@Operation(
			summary = "Fetch distinct value from the data source",
			description = "Fetch distinct values of usertype based on the received request")
    @PostMapping("/userRegistration/getUsertypes")
    public ResponseEntity<?> getUsertype() {
        Optional<List<String>> list = Optional.ofNullable(auditTrail.findDistinctUserType());
        return new ResponseEntity<>(list, HttpStatus.OK);
    }


}