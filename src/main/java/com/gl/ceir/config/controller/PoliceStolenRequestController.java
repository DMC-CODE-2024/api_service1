package com.gl.ceir.config.controller;

import com.gl.ceir.config.configuration.PropertiesReader;
import com.gl.ceir.config.model.app.*;
import com.gl.ceir.config.repository.app.CountryCodeRepo;
import com.gl.ceir.config.repository.app.LostStolenRepo;
import com.gl.ceir.config.repository.app.SystemConfigurationDbRepository;
import com.gl.ceir.config.service.impl.LostStolenServiceImpl;
import com.gl.ceir.config.service.impl.PoliceStolenService;
//import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/police/")
public class PoliceStolenRequestController {

    private static final Logger logger = LogManager.getLogger(PoliceStolenRequestController.class);

    @Autowired
    com.gl.ceir.config.service.impl.LostStolenServiceImpl LostStolenServiceImpl;

    @Autowired
    PoliceStolenService lostStolenRepo;


   // //@ApiOperation(value = "Mark device stolen.", response = GenricResponse.class)
    @RequestMapping(path = "/lostStolen/create", method = RequestMethod.POST)
    public GenricResponse saveStolenDevice(@RequestBody StolenLostModel stolenLostModel) {

        logger.info("Lost stolen Request = " + stolenLostModel);
        GenricResponse genricResponse = lostStolenRepo.saveLostStolenDevice(stolenLostModel);
        logger.info("Lost stolen Response = " + genricResponse);
        return genricResponse;
    }

   // //@ApiOperation(value = "verify OTP.", response = GenricResponse.class)

    @RequestMapping(path = "/verifyOTP", method = RequestMethod.POST) public
    GenricResponse verifyOTP(@RequestBody OTPRequest otpRequest ) {
        logger.info("verify OTP Request , requestID= " +
                otpRequest.getRequestID()+" , otp ="+otpRequest.getOtp());
        GenricResponse genricResponse = lostStolenRepo.verifyOTP(otpRequest.getRequestID(),otpRequest.getOtp(),otpRequest.getRequestType(),otpRequest.getOldRequestID()); logger.info("verify OTP Response = " + genricResponse);
        LostStolenServiceImpl.audiTrail( otpRequest.getPublicIp(), otpRequest.getBrowser(), otpRequest.getRequestID(), "OTP-Verification",otpRequest.getUserAgent());
        return genricResponse;
    }

   // //@ApiOperation(value = "resend OTP.", response = GenricResponse.class)
    @RequestMapping(path = "/resendOTP", method = RequestMethod.POST)
    public GenricResponse resendOTP(@RequestBody OTPRequest otpRequest ) {

        logger.info("resend Request , requestID= " + otpRequest.getRequestID()+" , otp ="+otpRequest.getOtp());
        GenricResponse genricResponse = lostStolenRepo.resendOTP(otpRequest.getRequestID());
        logger.info("resend OTP Response = " + genricResponse);
        LostStolenServiceImpl.audiTrail( otpRequest.getPublicIp(), otpRequest.getBrowser(), otpRequest.getRequestID(), "Resend-OTP",otpRequest.getUserAgent());
        return genricResponse;
    }


   // //@ApiOperation(value = "Mark Bulk device stolen.", response = GenricResponse.class)
    @RequestMapping(path = "/lostStolen/bulkSave", method = RequestMethod.POST)
    public GenricResponse bulkSave(@RequestBody StolenLostModel stolenLostModel) {

        logger.info("Bulk Lost stolen Request = " + stolenLostModel);
        GenricResponse genricResponse = lostStolenRepo.saveBulkStolenDevice(stolenLostModel);
        logger.info("Bulk Lost stolen Response = " + genricResponse);
        return genricResponse;
    }

   // //@ApiOperation(value = "Verify device", response = CountryCodeModel.class)
    @PostMapping("/verifyDevice")
    public GenricResponse verifyDevice(@RequestBody StolenLostModel stolenLostModel) {
        String tac=	stolenLostModel.getImei1().substring(0, 8);
        MDRModel mdrModel=lostStolenRepo.tacValdation(tac);
        GenricResponse genricResponse=new GenricResponse();
        genricResponse.setData(mdrModel);
        genricResponse.setTag(tac);
        logger.info("Response = " + genricResponse);
        return genricResponse;
    }

   // //@ApiOperation(value = "approve police request by  request id .", response = GenricResponse.class)
    @RequestMapping(path = "/approvePoliceRequest", method = RequestMethod.POST)
    public GenricResponse approvePoliceRequest(@RequestBody StolenLostModel stolenLostModel) {
        logger.info("approve request for request id = " + stolenLostModel.getRequestId());
        GenricResponse genricResponse=LostStolenServiceImpl.approvePoliceRequest(stolenLostModel);
        return genricResponse;
    }
}
