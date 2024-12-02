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
import com.gl.ceir.config.model.app.BlackListModel;
import com.gl.ceir.config.model.app.CheckIMEIResponseParam;
import com.gl.ceir.config.model.app.FileDetails;
import com.gl.ceir.config.model.app.FilterRequest;
import com.gl.ceir.config.model.app.GreyListView;
import com.gl.ceir.config.model.app.VIPListModel;
import com.gl.ceir.config.repository.aud.AuditTrailRepository;
import com.gl.ceir.config.service.impl.VipListViewImpl;

//import io.swagger.annotations.ApiOperation;

@RestController
public class VipListViewController {

	@Autowired
	PropertiesReader propertiesReader;
	
	@Autowired
	AuditTrailRepository auditTrailRepository;
	
	@Autowired
	VipListViewImpl vipListViewImpl;
	
	private static final Logger logger = LogManager.getLogger(VipListViewController.class);

	//@ApiOperation(value = "Paginated view of check IMEI param .", response = VipListViewController.class)
	@PostMapping("/filter/vipListView")
	public MappingJacksonValue paginatedViewOfVipList(@RequestBody FilterRequest filterRequest,
			@RequestParam(value = "pageNo", defaultValue = "0") Integer pageNo,
			@RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
			@RequestParam(value = "file", defaultValue = "0") Integer file) {
		MappingJacksonValue mapping = null;
		logger.info(" request  of VIP list IMEI view" + filterRequest);
		if(file == 0) {
			Page<VIPListModel> page = vipListViewImpl.filterVIPListIMEI(filterRequest, pageNo, pageSize);
			mapping = new MappingJacksonValue(page);
			logger.info("Response to send = " + page.toString());
		}else {
			FileDetails fileDetails = vipListViewImpl.exportFile_System(filterRequest);
			mapping = new MappingJacksonValue(fileDetails);
		}
		
		return mapping;
	}
	
	//@ApiOperation(value = "vip list  view Data using id", response = VipListViewController.class)
	@RequestMapping(path = "/viplist/viewById", method = RequestMethod.POST)
	public MappingJacksonValue findVIPListDetailsById(@RequestBody VIPListModel vipListModel) {

		logger.info("request to  check vip list  details by id="+vipListModel);
		
		
		VIPListModel  vipListresponse = vipListViewImpl.findBySno(vipListModel);
		
		MappingJacksonValue mapping = new MappingJacksonValue(vipListresponse);
		/* mapping.setSerializationView(CheckIMEIResponseParam.class); */
		logger.info(" vip List Response to send="+vipListresponse);
		logger.info("mapping response to send="+vipListresponse);
		return mapping;
	}
}

