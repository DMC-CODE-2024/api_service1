package com.gl.ceir.config.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gl.ceir.config.model.app.AllRequest;
import com.gl.ceir.config.model.app.FileDetails;
import com.gl.ceir.config.service.impl.FileServiceImpl;

//import io.swagger.annotations.ApiOperation;

@RestController
public class FileController {

	private static final Logger logger = LogManager.getLogger(FileController.class);

	

	@Autowired
	private FileServiceImpl fileServiceImpl;

	//@ApiOperation(value = "Download Sample Stoke File.", response = String.class)
	@RequestMapping(path = "/Download/SampleFile", method = RequestMethod.POST)
	public FileDetails downloadSampleFile(int featureId,@RequestBody AllRequest request) {		
		
		logger.info("Request sample file link with featureId [" + featureId + "]");
		FileDetails fileDetails = fileServiceImpl.getSampleFile(featureId,request);
		logger.info("Response for featureId [" + featureId + "] sample file " + fileDetails);
		
		return fileDetails;
	}

	//@ApiOperation(value = "Download Stoke upload File.", response = String.class)
	@RequestMapping(path = "/Download/uploadFile", method = RequestMethod.POST)
	public FileDetails downloadUploadedFile(String fileName, String txnId,String fileType,@RequestParam(name = "tag", required = false) String tag,@RequestBody AllRequest request) {
		
		logger.info("Request to download uploded file link with txnId [" + txnId + "]"+" ip and browser="+request);
		FileDetails fileDetails = fileServiceImpl.downloadUploadedFile(fileName, txnId, fileType, tag,request);
		logger.info("Response for txnId [" + txnId + "] sample file " + fileDetails);
		
		return fileDetails;
	}
	
	//@ApiOperation(value = "Download manuals.", response = String.class)
	@PostMapping("/Download/manuals")
	public FileDetails downloadManuals(@RequestBody AllRequest auditRequest) {		
		
		logger.info("Request manuals=="+auditRequest);
		FileDetails fileDetails = fileServiceImpl.getManuals(auditRequest);
		logger.info("Response for manuals " + fileDetails);
		
		return fileDetails;
	}
}
