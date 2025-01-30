package com.gl.ceir.config.controller;


import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.gl.ceir.config.model.app.*;
import com.gl.ceir.config.repository.app.CountryCodeRepo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import com.gl.ceir.config.configuration.PropertiesReader;
import com.gl.ceir.config.repository.app.LostStolenRepo;
import com.gl.ceir.config.repository.app.SystemConfigurationDbRepository;
import com.gl.ceir.config.service.impl.LostStolenServiceImpl;

//import io.swagger.annotations.ApiOperation;

@RestController
public class LostStolenController {

	private static final Logger logger = LogManager.getLogger(LostStolenController.class);
	
	@Autowired
	LostStolenServiceImpl LostStolenServiceImpl;

	@Autowired
	PropertiesReader propertiesReader;

	@Autowired
	LostStolenRepo lostStolenRepo;
	
	@Autowired
	SystemConfigurationDbRepository systemConfigurationDbRepository;

	@Autowired
	CountryCodeRepo countryCodeRepo;
	
	
	//@ApiOperation(value = "Mark device stolen.", response = GenricResponse.class)
	@RequestMapping(path = "/lostStolen/save", method = RequestMethod.POST)
	public GenricResponse saveStolenDevice(@RequestBody StolenLostModel stolenLostModel) {

		logger.info("Lost stolen Request = " + stolenLostModel);
		GenricResponse genricResponse = LostStolenServiceImpl.saveLostStolenDevice(stolenLostModel);
		logger.info("Lost stolen Response = " + genricResponse);
		return genricResponse;
	}
	
	//@ApiOperation(value = "Mark device stolen.", response = GenricResponse.class)
	@RequestMapping(path = "/lostStolen/update", method = RequestMethod.POST)
	public GenricResponse updateStolenDevice(@RequestBody StolenLostModel stolenLostModel) {

		logger.info("update Lost stolen Request = " + stolenLostModel);
		GenricResponse genricResponse = LostStolenServiceImpl.updateLostStolenDevice(stolenLostModel);
		logger.info("Lost stolen Response = " + genricResponse);

		return genricResponse;
	}

	//@ApiOperation(value = "save cancellation reason ", response = GenricResponse.class)
	@RequestMapping(path = "/saveCancelRequest", method = RequestMethod.POST)
	public GenricResponse saveCancelRequest(@RequestBody StolenLostModel stolenLostModel) {
		logger.info("save cancellation request = " + stolenLostModel);
		GenricResponse genricResponse = LostStolenServiceImpl.saveCancelRequest(stolenLostModel);
		logger.info("save cancellation Response = " + genricResponse);
		return genricResponse;
	}
	
	 // //@ApiOperation(value = "verify OTP.", response = GenricResponse.class)
	  
	  @RequestMapping(path = "/verifyOTP", method = RequestMethod.POST) public
	  GenricResponse verifyOTP(@RequestBody OTPRequest otpRequest ) {
	  logger.info("verify OTP Request , requestID= " +
	  otpRequest.getRequestID()+" , otp ="+otpRequest.getOtp()); 
	  GenricResponse genricResponse = LostStolenServiceImpl.verifyOTP(otpRequest.getRequestID(),otpRequest.getOtp(),otpRequest.getRequestType(),otpRequest.getOldRequestID()); logger.info("verify OTP Response = " + genricResponse);
	  LostStolenServiceImpl.audiTrail( otpRequest.getPublicIp(), otpRequest.getBrowser(), otpRequest.getRequestID(), "OTP-Verification",otpRequest.getUserAgent());
	  return genricResponse; 
	  }
	  
 
	 // //@ApiOperation(value = "verify OTP for Update block form.", response = GenricResponse.class)
	  @RequestMapping(path = "/verifyOTPUpdateStolen", method = RequestMethod.POST) public
	  GenricResponse verifyOTPUpdateStolen(@RequestBody OTPRequest otpRequest ) {
	  logger.info("verify OTP Request  for update block form, requestID= " +
	  otpRequest.getRequestID()+" , otp ="+otpRequest.getOtp()); 
	  GenricResponse genricResponse = LostStolenServiceImpl.verifyOTPUpdateStolen(otpRequest.getRequestID(),otpRequest.getOtp(),otpRequest.getRequestType(),otpRequest.getOldRequestID()); logger.info("verify OTP Response = " + genricResponse);
	  LostStolenServiceImpl.audiTrail( otpRequest.getPublicIp(), otpRequest.getBrowser(), otpRequest.getRequestID(), "Update Stolen-OTP-Verification",otpRequest.getUserAgent());
	  return genricResponse;
	  }

	//@ApiOperation(value = "verify OTP.", response = GenricResponse.class)

	@RequestMapping(path = "/verifyCancelRequestOTP", method = RequestMethod.POST) public
	GenricResponse verifyCancelRequestOTP(@RequestBody OTPRequest otpRequest ) {
		logger.info("verify cancel request OTP, requestID= " +
				otpRequest.getRequestID()+" , otp ="+otpRequest.getOtp());
		GenricResponse genricResponse = LostStolenServiceImpl.verifyCancelRequestOTP(otpRequest.getRequestID(),otpRequest.getOtp(),otpRequest.getRequestType(),otpRequest.getOldRequestID()); logger.info("verify OTP Response = " + genricResponse);
		LostStolenServiceImpl.audiTrail( otpRequest.getPublicIp(), otpRequest.getBrowser(), otpRequest.getRequestID(), "OTP-Verification",otpRequest.getUserAgent());
		return genricResponse;
	}
	 
	//@ApiOperation(value = "resend OTP.", response = GenricResponse.class)
	@RequestMapping(path = "/resendOTP", method = RequestMethod.POST)
	public GenricResponse resendOTP(@RequestBody OTPRequest otpRequest ) {
		
		logger.info("resend Request , requestID= " + otpRequest.getRequestID()+" , otp ="+otpRequest.getOtp());
		GenricResponse genricResponse = LostStolenServiceImpl.resendOTP(otpRequest.getRequestID());
		logger.info("resend OTP Response = " + genricResponse);
		LostStolenServiceImpl.audiTrail( otpRequest.getPublicIp(), otpRequest.getBrowser(), otpRequest.getRequestID(), "Resend-OTP",otpRequest.getUserAgent());
		return genricResponse;
	}
	
	//save recovery Request
	//@ApiOperation(value = "Mark device recovered.", response = GenricResponse.class)
	@RequestMapping(path = "/recoveryFound/save", method = RequestMethod.POST)
	public GenricResponse saveRecoveryFound(@RequestBody StolenLostModel stolenLostModel) {

		logger.info("Fetch data for recovery by  Request  ---= " + stolenLostModel);
		GenricResponse genricResponse = LostStolenServiceImpl.saveRecoveryDevice1(stolenLostModel);
		logger.info("Lost stolen Response = " + genricResponse);

		return genricResponse;
	}
	//check recovery or stolen  Request
	
		//@ApiOperation(value = "check request status.", response = GenricResponse.class)
		@RequestMapping(path = "/checkRequestStatus", method = RequestMethod.POST)
		public GenricResponse checkRequestStatus(@RequestBody StolenLostModel stolenLostModel) {
			logger.info("check Recovery or stolen save  Request = " + stolenLostModel.getRequestId());
			GenricResponse genricResponse = LostStolenServiceImpl.checkStatus1(stolenLostModel);
			logger.info("Lost stolen Response = " + genricResponse);
			return genricResponse;
		}

		//@ApiOperation(value = "get otp request .", response = GenricResponse.class)
		@RequestMapping(path = "/getOTP", method = RequestMethod.POST)
		public GenricResponse getOTP(@RequestBody StolenLostModel stolenLostModel) {
			logger.info("get otp Request = " + stolenLostModel.getRequestId());
			GenricResponse genricResponse = LostStolenServiceImpl.getOtp(stolenLostModel);
			logger.info("Lost stolen Response = " + genricResponse);
			return genricResponse;
		}
		
		//@ApiOperation(value = "get otp request .", response = GenricResponse.class)
		@RequestMapping(path = "/getOTPForCheckRequest", method = RequestMethod.POST)
		public GenricResponse getOTPForCheckRequest(@RequestBody StolenLostModel stolenLostModel) {
			logger.info("get otp Request for check status = " + stolenLostModel.getRequestId());
			GenricResponse genricResponse = LostStolenServiceImpl.getOtpForCheckStatus(stolenLostModel);
			logger.info("Lost stolen Response fr check status = " + genricResponse);
			return genricResponse;
		}


	//@ApiOperation(value = "get otp request for cancel request .", response = GenricResponse.class)
	@RequestMapping(path = "/getOTPForCancelRequest", method = RequestMethod.POST)
	public GenricResponse getOTPForCancelRequest(@RequestBody StolenLostModel stolenLostModel) {
		logger.info("get otp Request for cancel request = " + stolenLostModel.getRequestId());
		GenricResponse genricResponse = LostStolenServiceImpl.getOtpForCancelRequest(stolenLostModel);
		logger.info("Otp Response for cancel request = " + genricResponse);
		return genricResponse;
	}
		
		//@ApiOperation(value = "get  By request id .", response = GenricResponse.class)
		@RequestMapping(path = "/getByRequestID", method = RequestMethod.POST)
		public StolenLostModel getByRequestID(@RequestBody StolenLostModel stolenLostModel) {
			logger.info("get by Request id = " + stolenLostModel.getRequestId());
			StolenLostModel res = lostStolenRepo.findByRequestId2(stolenLostModel.getRequestId());
			SystemConfigurationDb ss=new SystemConfigurationDb ();
			ss=systemConfigurationDbRepository.getByTag("upload_file_link");
			
			res.setFirCopyUrl(ss.getValue().replace("$LOCAL_IP",propertiesReader.localIp));
			
			logger.info("Lost stolen Response  by id  = " + res);
			return res;
		}
		
		//@ApiOperation(value = "get lost device details by request id .", response = GenricResponse.class)
		@RequestMapping(path = "/getLostDeviceByRequestID", method = RequestMethod.POST)
		public StolenLostModel getByRequestIDForViews(@RequestBody StolenLostModel stolenLostModel) {
			logger.info("get lost device details by request id = " + stolenLostModel.getRequestId());
			StolenLostModel res = lostStolenRepo.findByRequestId3(stolenLostModel.getRequestId());
			SystemConfigurationDb ss=new SystemConfigurationDb ();
			ss=systemConfigurationDbRepository.getByTag("upload_file_link");

			if(res!=null) {
				res.setDevicePurchaseInvoiceUrl(res.getFirCopyUrl());
				res.setFirCopyUrl(ss.getValue().replace("$LOCAL_IP",propertiesReader.localIp));
				try {
					String dist = lostStolenRepo.getDistrict(res.getDistrict());
					String commune = lostStolenRepo.getCommune(res.getCommune());
					String province = lostStolenRepo.getProvince(res.getProvince());
					String police = lostStolenRepo.getPolice(res.getPoliceStation());
					String nationality = lostStolenRepo.getNationality(res.getDeviceOwnerNationality());
					//String categ=lostStolenRepo.getDistrict(stolenLostModel.getCategory());
					res.setDistrict(dist);
					res.setCommune(commune);
					res.setPoliceStation(police);
					res.setProvince(province);
					res.setDeviceOwnerNationality(nationality);

				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			
			logger.info("Lost stolen Response  by id  = " + res);
			return res;
		}

	//@ApiOperation(value = "get Bulk device details by request id .", response = GenricResponse.class)
	@RequestMapping(path = "/getBulkDeviceByRequestID", method = RequestMethod.POST)
	public StolenLostModel getBulkDeviceByRequestID(@RequestBody StolenLostModel stolenLostModel) {
		logger.info("get bulk lost device details by request id = " + stolenLostModel.getRequestId());
		StolenLostModel res = lostStolenRepo.findByRequestId3(stolenLostModel.getRequestId());
		SystemConfigurationDb systemConfigurationDb=new SystemConfigurationDb ();
		systemConfigurationDb=systemConfigurationDbRepository.getByTag("upload_file_link");

		if(res!=null) {
			res.setDevicePurchaseInvoiceUrl(res.getFirCopyUrl());
			res.setFirCopyUrl(systemConfigurationDb.getValue().replace("$LOCAL_IP",propertiesReader.localIp));

		}

		logger.info("Bulk lost stolen Response  by id  = " + res);
		return res;
	}




	//@ApiOperation(value = "County code list", response = CountryCodeModel.class)
	@PostMapping("/countryCodeList")
	public List<CountryCodeModel> countryCodeList() {
		List<CountryCodeModel> countryCodeModel=countryCodeRepo.fetchCountryCode();
		logger.info("Response to send for"+countryCodeModel);
		return countryCodeModel;
	}




}
