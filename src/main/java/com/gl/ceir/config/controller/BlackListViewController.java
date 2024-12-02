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
import com.gl.ceir.config.model.app.BlackListModel;
import com.gl.ceir.config.model.app.ChecKIMEIContent;
import com.gl.ceir.config.model.app.CheckIMEIResponseParam;
import com.gl.ceir.config.model.app.FileDetails;
import com.gl.ceir.config.model.app.FilterRequest;
import com.gl.ceir.config.repository.aud.AuditTrailRepository;
import com.gl.ceir.config.service.impl.BlackListViewImpl;

//import io.swagger.annotations.ApiOperation;

@RestController
public class BlackListViewController {

	@Autowired
	PropertiesReader propertiesReader;
	
	@Autowired
	AuditTrailRepository auditTrailRepository;

	@Autowired
	BlackListViewImpl blackListViewImpl;
	
	private static final Logger logger = LogManager.getLogger(BlackListViewController.class);

	//@ApiOperation(value = "Paginated view of check IMEI param .", response = BlackListViewController.class)
	@PostMapping("/filter/blackListView")
	public MappingJacksonValue paginatedViewOfSystemConfig(@RequestBody FilterRequest filterRequest,
			@RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
			@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
			@RequestParam(value = "file", defaultValue = "0") Integer file) {
		MappingJacksonValue mapping = null;
		logger.info(" request  of Black list IMEI view" + filterRequest);
		if(file == 0) {
			Page<BlackListModel> page = blackListViewImpl.filterBlackListIMEI(filterRequest, pageNo, pageSize);
			mapping = new MappingJacksonValue(page);
			logger.info("Response to send = " + page.toString());
		}else {
			FileDetails fileDetails = blackListViewImpl.exportFile_System(filterRequest);
			mapping = new MappingJacksonValue(fileDetails);
		}
		
		return mapping;
	}
	
	//@ApiOperation(value = "black list  view Data using id", response = BlackListViewController.class)
	@RequestMapping(path = "/blacklist/viewid", method = RequestMethod.POST)
	public MappingJacksonValue findBlackListDetailsById(@RequestBody BlackListModel blackListModel) {

		logger.info("request to  check black list  details by id="+blackListModel);
		
		
		BlackListModel  blackListresponse = blackListViewImpl.findBySno(blackListModel);
		
		MappingJacksonValue mapping = new MappingJacksonValue(blackListresponse);
		/* mapping.setSerializationView(CheckIMEIResponseParam.class); */
		logger.info(" black List Response to send="+blackListresponse);
		logger.info("mapping response to send="+blackListresponse);
		return mapping;
	}
}
