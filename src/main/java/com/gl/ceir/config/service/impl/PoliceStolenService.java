package com.gl.ceir.config.service.impl;

import com.gl.ceir.config.configuration.PropertiesReader;
import com.gl.ceir.config.feign.NotificationFeign;
import com.gl.ceir.config.model.app.*;
import com.gl.ceir.config.model.aud.AuditTrail;
import com.gl.ceir.config.model.oam.RequestHeaders;
import com.gl.ceir.config.repository.app.*;
import com.gl.ceir.config.repository.aud.AuditTrailRepository;
import com.gl.ceir.config.repository.oam.RequestHeadersRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class PoliceStolenService {
    private static final Logger logger = LogManager.getLogger(LostStolenServiceImpl.class);

    @Autowired
    LostStolenRepo lostStolenRepo;

    @Autowired
    NotificationFeign notificationFeign;


    @Autowired
    StolenLostDetailRepo stolenLostDetailRepo;


    @Autowired
    MDRRepo mdrRepo;

    @Autowired
    AuditTrailRepository auditTrailRepository;

    @Autowired
    RequestHeadersRepository requestHeadersRepository;

    @Autowired
    EirsResponseRepo eirsResponseRepo;

    @Autowired
    InvalidIMEIRepo invalidIMEIRepo;

    @Autowired
    WebActionDbRepository webActionDbRepository;

    @Autowired
    PropertiesReader propertiesReader;

    public GenricResponse saveLostStolenDevice(StolenLostModel stolenLostModel) {
        GenricResponse genricResponse = new GenricResponse();
        MDRModel mdrModel= new MDRModel();
        String OTPsms;
        logger.info("save stolen request in service impl = " + stolenLostModel);
        audiTrail(stolenLostModel, "Save-Device-Recovery");
        //String tac = stolenLostModel.getImei1();
        //String tac1=	tac.substring(0, 8);
        List<String> tacList=new ArrayList<String>();

        if (stolenLostModel.getImei1() != null) {String tac = stolenLostModel.getImei1().substring(0, 8);tacList.add(tac);}
        if (stolenLostModel.getImei2() != null) {String tac = stolenLostModel.getImei2().substring(0, 8);tacList.add(tac);}
        if (stolenLostModel.getImei3() != null) {String tac = stolenLostModel.getImei3().substring(0, 8);tacList.add(tac);}
        if (stolenLostModel.getImei4() != null) {String tac = stolenLostModel.getImei4().substring(0, 8);tacList.add(tac);}
        boolean tacResult=multipleTacValidation(tacList,stolenLostModel.getDeviceBrand(),stolenLostModel.getDeviceModel());
        //   checking TAC validation
        if (!tacResult) {
            logger.info("TAC is not exist in MDR");
            genricResponse.setStatusCode("201");
            genricResponse.setMessage("Invalid TAC");
            genricResponse.setRequestID(stolenLostModel.getRequestId());
            return genricResponse;

        }
        //checking duplicate IMEI in lost device mgmt  table

        if(!Objects.isNull(this.checkIMEIinMgmt(stolenLostModel.getImei1()))) {
            String failRemark=eirsResponseRepo.getByTag("fail_duplicate_message",stolenLostModel.getLanguage());
            logger.info("Check IMEI1 in  duplicate IMEI check in mgmt table");
            genricResponse.setStatusCode("502");
            genricResponse.setMessage("Duplicate IMEI Found");
            genricResponse.setRequestID(stolenLostModel.getRequestId());
            genricResponse.setTag("1");
            saveFailScenario(stolenLostModel,failRemark);
            return genricResponse;
        }
        if(Objects.nonNull(stolenLostModel.getImei2())) {
            if(!Objects.isNull(this.checkIMEIinMgmt(stolenLostModel.getImei2()))) {
                logger.info("Check IMEI2 in  duplicate IMEI check in mgmt table");
                String failRemark=eirsResponseRepo.getByTag("fail_duplicate_message",stolenLostModel.getLanguage());
                genricResponse.setStatusCode("502");
                genricResponse.setMessage("Duplicate IMEI Found");
                genricResponse.setRequestID(stolenLostModel.getRequestId());
                genricResponse.setTag("2");
                saveFailScenario(stolenLostModel,failRemark);
                return genricResponse;
            }
        }
        if(Objects.nonNull(stolenLostModel.getImei3())) {
            if(!Objects.isNull(this.checkIMEIinMgmt(stolenLostModel.getImei3()))) {
                logger.info("Check IMEI3 in  duplicate IMEI check in mgmt table");
                String failRemark=eirsResponseRepo.getByTag("fail_duplicate_message",stolenLostModel.getLanguage());
                genricResponse.setStatusCode("502");
                genricResponse.setMessage("Duplicate IMEI Found");
                genricResponse.setRequestID(stolenLostModel.getRequestId());
                genricResponse.setTag("3");
                saveFailScenario(stolenLostModel,failRemark);
                return genricResponse;
            }
        }
        if(Objects.nonNull(stolenLostModel.getImei4())) {
            if(!Objects.isNull(this.checkIMEIinMgmt(stolenLostModel.getImei4()))) {
                logger.info("Check IMEI4 in  duplicate IMEI check in mgmt table");
                String failRemark=eirsResponseRepo.getByTag("fail_duplicate_message",stolenLostModel.getLanguage());
                genricResponse.setStatusCode("502");
                genricResponse.setMessage("Duplicate IMEI Found");
                genricResponse.setRequestID(stolenLostModel.getRequestId());
                genricResponse.setTag("4");
                saveFailScenario(stolenLostModel,failRemark);
                return genricResponse;
            }
        }





        //checking duplicate IMEI in detail table
        LostStolenMgmt imeiExist1=stolenLostDetailRepo.findByImei(stolenLostModel.getImei1());
        LostStolenMgmt imeiExist2=stolenLostDetailRepo.findByImei(stolenLostModel.getImei2());
        LostStolenMgmt imeiExist3=stolenLostDetailRepo.findByImei(stolenLostModel.getImei3());
        LostStolenMgmt imeiExist4=stolenLostDetailRepo.findByImei(stolenLostModel.getImei4());
        if(!Objects.isNull(imeiExist1) || !Objects.isNull(imeiExist2) || !Objects.isNull(imeiExist3) || !Objects.isNull(imeiExist4) ) {
            logger.info("dupicate IMEI found");
            String failRemark=eirsResponseRepo.getByTag("fail_duplicate_message",stolenLostModel.getLanguage());
            genricResponse.setStatusCode("502");
            genricResponse.setMessage("Duplicate IMEI Found");
            saveFailScenario(stolenLostModel,failRemark);
            return genricResponse;
        }
        //check imei is valid or invalid
        boolean imei1IsValidOrInvalid= invalidIMEIRepo.existsByImei(stolenLostModel.getImei1().substring(0, 14));
        if (imei1IsValidOrInvalid){
            String failRemark=eirsResponseRepo.getByTag("fail_invalid_message",stolenLostModel.getLanguage());
            genricResponse.setStatusCode("503");
            genricResponse.setMessage("Invalid IMEI Found");
            saveFailScenario(stolenLostModel,failRemark);
            return genricResponse;
        }
        if(Objects.nonNull(stolenLostModel.getImei2())) {
            boolean imei2IsValidOrInvalid= invalidIMEIRepo.existsByImei(stolenLostModel.getImei2().substring(0, 14));
            if (imei2IsValidOrInvalid){
                String failRemark=eirsResponseRepo.getByTag("fail_invalid_message",stolenLostModel.getLanguage());
                genricResponse.setStatusCode("503");
                genricResponse.setMessage("Invalid IMEI Found");
                saveFailScenario(stolenLostModel,failRemark);
                return genricResponse;
            }
        }
        if(Objects.nonNull(stolenLostModel.getImei3())) {
            boolean imei3IsValidOrInvalid= invalidIMEIRepo.existsByImei(stolenLostModel.getImei3().substring(0, 14));
            if (imei3IsValidOrInvalid){
                String failRemark=eirsResponseRepo.getByTag("fail_invalid_message",stolenLostModel.getLanguage());
                genricResponse.setStatusCode("503");
                genricResponse.setMessage("Invalid IMEI Found");
                saveFailScenario(stolenLostModel,failRemark);
                return genricResponse;
            }
        }
        if(Objects.nonNull(stolenLostModel.getImei3())) {
            boolean imei4IsValidOrInvalid= invalidIMEIRepo.existsByImei(stolenLostModel.getImei4().substring(0, 14));
            if (imei4IsValidOrInvalid){
                String failRemark=eirsResponseRepo.getByTag("fail_invalid_message",stolenLostModel.getLanguage());
                genricResponse.setStatusCode("503");
                genricResponse.setMessage("Invalid IMEI Found");
                saveFailScenario(stolenLostModel,failRemark);
                return genricResponse;
            }
        }


        //generate OTP
        OTPService otp= new OTPService();
        OTPsms=otp.phoneOtp();

        stolenLostModel.setStatus("INIT_START");
        stolenLostModel.setUserStatus("INIT_START");
        stolenLostModel.setOtp(OTPsms);
        //saving in stolen_mgmt table
        lostStolenRepo.save(stolenLostModel);
        /// calling Notification API
        EirsResponse eirsResponse = new EirsResponse();
        EirsResponse eirsResponseEmail = new EirsResponse();
        eirsResponse=eirsResponseRepo.getByTagAndLanguage("police_stolen_notification_msg",stolenLostModel.getLanguage());
        eirsResponseEmail=eirsResponseRepo.getByTagAndLanguage("police_stolen_notification_msg_email",stolenLostModel.getLanguage());
        String message=eirsResponse.getValue().replace("<otp>", OTPsms);
        String messageEmail=eirsResponseEmail.getValue().replace("<otp>", OTPsms);
        String messageSubject=eirsResponseEmail.getSubject();
        NotificationAPI(stolenLostModel.getContactNumberForOtp(),stolenLostModel.getDeviceOwnerNationality(),stolenLostModel.getOtpEmail(),message,stolenLostModel.getRequestId(),propertiesReader.stolenFeatureName,stolenLostModel.getLanguage(),eirsResponse.getDescription(),"SMS_OTP","",messageEmail,messageSubject);
        genricResponse.setRequestID(stolenLostModel.getRequestId());
        genricResponse.setStatusCode("200");
        genricResponse.setRequestID(stolenLostModel.getRequestId());
        genricResponse.setTxnId(stolenLostModel.getDeviceOwnerNationality());
        logger.info("Nationality="+stolenLostModel.getDeviceOwnerNationality());
        if(stolenLostModel.getDeviceOwnerNationality().equalsIgnoreCase("0")) {
        genricResponse.setTag(stolenLostModel.getContactNumberForOtp());
        }
        else
        {
           genricResponse.setTag(stolenLostModel.getOtpEmail());
        }
        return genricResponse;
    }


    public GenricResponse saveBulkStolenDevice(StolenLostModel stolenLostModel) {
        GenricResponse genricResponse = new GenricResponse();

        String OTPsms;
        audiTrail(stolenLostModel, "Save-Device-Recovery");
        OTPService otp= new OTPService();
        OTPsms=otp.phoneOtp();
        logger.info("otp generated="+OTPsms);
        stolenLostModel.setStatus("INIT_START");
        stolenLostModel.setUserStatus("INIT_START");
        stolenLostModel.setOtp(OTPsms);
        //saving in stolen_mgmt table
        lostStolenRepo.save(stolenLostModel);
        /// calling Notification API
        EirsResponse eirsResponse = new EirsResponse();
        EirsResponse eirsResponseEmail = new EirsResponse();
        eirsResponse=eirsResponseRepo.getByTagAndLanguage("police_bulk_stolen_notification_msg",stolenLostModel.getLanguage());
        eirsResponseEmail=eirsResponseRepo.getByTagAndLanguage("police_bulk_stolen_notification_msg_email",stolenLostModel.getLanguage());
        String message=eirsResponse.getValue().replace("<otp>", OTPsms);
        String messageEmail=eirsResponseEmail.getValue().replace("<otp>", OTPsms);
        String messageSubject=eirsResponseEmail.getSubject();
        NotificationAPI(stolenLostModel.getContactNumberForOtp(),stolenLostModel.getDeviceOwnerNationality(),stolenLostModel.getOtpEmail(),message,stolenLostModel.getRequestId(),propertiesReader.stolenFeatureName,stolenLostModel.getLanguage(),eirsResponse.getDescription(),"SMS_OTP","",messageEmail,messageSubject);
        genricResponse.setRequestID(stolenLostModel.getRequestId());
        genricResponse.setStatusCode("200");
        genricResponse.setRequestID(stolenLostModel.getRequestId());
        genricResponse.setTxnId(stolenLostModel.getDeviceOwnerNationality());
        if(stolenLostModel.getDeviceOwnerNationality().equalsIgnoreCase("0")) {
         genricResponse.setTag(stolenLostModel.getContactNumberForOtp());
        }
        else
        {
            genricResponse.setTag(stolenLostModel.getOtpEmail());
        }
         return genricResponse;
    }

    @Transactional
    public GenricResponse verifyOTP(String requestID,String otp,String requestType,String oldrequestID) {
        GenricResponse genricResponse= new GenricResponse();
        StolenLostModel res=lostStolenRepo.findByRequestId(requestID);
        String  email="";
        if(res.getOtp().equals(otp)) {
            logger.info("otp verification successful");
            res.setStatus("VERIFY_MOI");
            res.setUserStatus("Pending MOI");
            if(res.getRequestMode().equalsIgnoreCase("bulk")){
                res.setStatus("INIT");
            }

            lostStolenRepo.save(res);
            //updating init  status in stolen_mgmt table
            EirsResponse eirsResponse = new EirsResponse();
            EirsResponse eirsResponseEmail = new EirsResponse();
            eirsResponse=eirsResponseRepo.getByTagAndLanguage("police_stolen_success",res.getLanguage());
            eirsResponseEmail=eirsResponseRepo.getByTagAndLanguage("police_stolen_success_email",res.getLanguage());
            String message=eirsResponse.getValue().replace("<requestId>", res.getRequestId()).replace("<contactNumber>",res.getContactNumberForOtp());
            String subject=eirsResponse.getDescription().replace("<requestId>", res.getRequestId()).replace("<contactNumber>",res.getContactNumberForOtp());

            String messageEmail=eirsResponseEmail.getValue().replace("<requestId>", res.getRequestId()).replace("<contactNumber>",res.getContactNumberForOtp());
            String messageSubject=eirsResponseEmail.getSubject().replace("<requestId>", res.getRequestId()).replace("<contactNumber>",res.getContactNumberForOtp());
            NotificationAPI(res.getContactNumberForOtp(),res.getDeviceOwnerNationality(),res.getOtpEmail(),message,res.getRequestId(),propertiesReader.stolenFeatureName,res.getLanguage(),subject,"SMS",res.getDeviceOwnerEmail(),messageEmail,messageSubject);
            if(res.getRequestMode().equalsIgnoreCase("bulk"))
            {
                WebActionDb webActionDb= new WebActionDb();
                webActionDb.setTxnId(res.getRequestId());
                webActionDb.setState(1);
                webActionDb.setFeature("MOI");
                webActionDb.setSubFeature("PENDING_VERIFICATION");
                webActionDbRepository.save(webActionDb);
            }
            genricResponse.setStatusCode("200");
            genricResponse.setMessage("verification successful");
            genricResponse.setRequestID(requestID);
            genricResponse.setTag(requestType);
        }
        else {
            logger.info("otp verification failed");
            genricResponse.setStatusCode("201");
            genricResponse.setMessage("verification failed");
            genricResponse.setRequestID(requestID);
            res.setOtpRetryCount(res.getOtpRetryCount()+1);
            //updating init  status in stolen_mgmt table
            lostStolenRepo.save(res);
        }
        return genricResponse;

    }

    public GenricResponse resendOTP(String requestID) {
        logger.info("inside resend OTP=" + requestID);
        OTPService otp = new OTPService();// generate OTP
        GenricResponse genricResponse = new GenricResponse();
        String OTPsms = otp.phoneOtp();
        StolenLostModel res = lostStolenRepo.findByRequestId(requestID);

        res.setOtp(OTPsms);
        logger.info("request to update  resend OTP=" + res);
        lostStolenRepo.save(res);// updating OTP in stolen_mgmt table
        EirsResponse eirsResponse = new EirsResponse();
        EirsResponse eirsResponseEmail = new EirsResponse();
        eirsResponse=eirsResponseRepo.getByTagAndLanguage("resend_notification_msg",res.getLanguage());
        eirsResponseEmail=eirsResponseRepo.getByTagAndLanguage("resend_notification_msg_email",res.getLanguage());
        String message=eirsResponse.getValue().replace("<otp>", OTPsms);
        String messageEmail=eirsResponseEmail.getValue().replace("<otp>", OTPsms);
        String messageSubject=eirsResponseEmail.getSubject();
        NotificationAPI(res.getContactNumberForOtp(),res.getDeviceOwnerNationality(),res.getOtpEmail(),message,requestID,propertiesReader.stolenFeatureName,res.getLanguage(),eirsResponse.getDescription(),"SMS_OTP","",messageEmail,messageSubject);// calling notification API
        genricResponse.setStatusCode("200");
        genricResponse.setMessage("Resend OTP is successful");
        return genricResponse;

    }
    public void audiTrail(StolenLostModel stolenLostModel, String subFeature)
    {
        //New File
        try {
            AuditTrail auditTrail = new AuditTrail();
            auditTrail.setFeatureName("Stolen-Recovery Portal");
            auditTrail.setSubFeature(subFeature);
            auditTrail.setFeatureId(stolenLostModel.getFeatureId());
            auditTrail.setPublicIp(stolenLostModel.getPublicIp());
            auditTrail.setBrowser(stolenLostModel.getBrowser());
            auditTrail.setUserId(stolenLostModel.getUserId());
            auditTrail.setUserName(stolenLostModel.getUserName());
            auditTrail.setUserType( stolenLostModel.getUserType() );
            auditTrail.setRoleType( stolenLostModel.getUserType() );
            auditTrail.setTxnId(stolenLostModel.getRequestId());
            logger.info(" going to save in auditTrail" + auditTrail);
            auditTrailRepository.save(auditTrail);
        } catch (Exception e) {
            // TODO: handle exception
        }
        try {
            RequestHeaders header=new RequestHeaders();
            header.setBrowser(stolenLostModel.getBrowser());
            header.setPublicIp(stolenLostModel.getPublicIp());
            header.setUserAgent(stolenLostModel.getUserAgent());
            header.setUsername(stolenLostModel.getUserName());
            header.setCreatedOn(LocalDateTime.now());
            header.setModifiedOn(LocalDateTime.now());
            logger.info(" going to save in portal access" + header);
            requestHeadersRepository.save(header);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public MDRModel tacValdation(String tac) {
        logger.info(" inside tac validation" + tac);
        MDRModel stolenLostModel= new MDRModel();
        stolenLostModel= mdrRepo.findByDeviceId(tac);
        return stolenLostModel;

    }

    /*public boolean multipleTacValidation(List<String> tacs, String brand, String model) {
        logger.info("Inside TAC validation for the following TACs: " + tacs);
        boolean tacExist=false;
        if (tacs == null || tacs.isEmpty()) {
            return tacExist;
        }
        // Iterate through the TACs and compare the brand and model names
        for (String tac : tacs) {
            logger.info("Validating TAC: " + tac);
        // Fetch the MDRModel by TAC
            MDRModel mdrModel = mdrRepo.findByDeviceId(tac);
            logger.info(" TAC details for " +tac +" all details = "+mdrModel);
            // If the model was found
            if (mdrModel != null) {
                 // Check if the current TAC's brand name or model name differs from the base
                if (mdrModel.getBrandName().equalsIgnoreCase(brand) && mdrModel.getModelName().equalsIgnoreCase(brand)) {
                    logger.info("Brand name not matched ");
                    return true;
                }
            } else {
                logger.info("Tac not exist");
            }
        }
        // If no mismatches are found
        return false;
    }*/

    public boolean multipleTacValidation(List<String> tacs, String brand, String model) {
        logger.info("Inside TAC validation for the following TACs: " + tacs);
        boolean tacExist=false;
        if (tacs == null || tacs.isEmpty()) {
            return tacExist;
        }
        // Iterate through the TACs and compare the brand and model names
        for (String tac : tacs) {
            logger.info("Validating TAC: " + tac);
            // Fetch the MDRModel by TAC
            MDRModel mdrModel = mdrRepo.findByDeviceId(tac);
            logger.info(" TAC details for " +tac +" all details = "+mdrModel);
            // If the model was found
            if (mdrModel != null) {
                // Check if the current TAC's brand name or model name differs from the base
                if (mdrModel.getBrandName().equalsIgnoreCase(brand) && mdrModel.getModelName().equalsIgnoreCase(model)) {
                    logger.info("Brand name and model matched ");
                    tacExist=true;
                }
                else {
                    logger.info("Brand name and model not  matched ");
                    tacExist=false;
                }
            } else {
                logger.info("Tac not exist");
            }
        }
        // If no mismatches are found
        return tacExist;
    }

    public StolenLostModel checkIMEIinMgmt(String imei) {
        logger.info(" duplicate imei check in mgmt table" + imei);
        StolenLostModel imeiExistMgmt=lostStolenRepo.findByImei1(imei);
        logger.info(" result from lost device mgmt table for duplicate imei " + imeiExistMgmt);
        return imeiExistMgmt;
    }

    public void saveFailScenario(StolenLostModel stolenLostModel,String remark){
        stolenLostModel.setStatus("Fail");
        stolenLostModel.setUserStatus("Fail");
        stolenLostModel.setRemarks(remark);
        logger.info("Data save in mgmt table with Fail status" + stolenLostModel);
        lostStolenRepo.save(stolenLostModel);
    }

    public void NotificationAPI(String msisdn ,  String nationality, String emailOTP,String msg,String requestId,String featureName,String lang,String subject,String channel,String email,String emailBody,String emailSubject) {
        NotificationModel notificationModel = new NotificationModel();
        GenricResponse genricResponse = new GenricResponse();

        notificationModel.setFeatureName(featureName);
        notificationModel.setFeatureTxnId(requestId);
        notificationModel.setMsgLang(lang);
        logger.info("msisdn="+msisdn);
        if (nationality.equalsIgnoreCase("0")) {
            notificationModel.setChannelType(channel);
            notificationModel.setMsisdn(msisdn);
            notificationModel.setMessage(msg);
            logger.info("request send to sms notification API=" + notificationModel);
            genricResponse = notificationFeign.addNotifications(notificationModel);
            if(!channel.contains("OTP")){

                notificationModel.setChannelType("EMAIL");
                notificationModel.setSubject(emailSubject);
                notificationModel.setMessage(emailBody);
                notificationModel.setEmail(email);
                logger.info("request send to email notification API=" + notificationModel);
                genricResponse = notificationFeign.addNotifications(notificationModel);
            }
        }
        else {
            if(channel.contains("OTP")){
                notificationModel.setChannelType("EMAIL_OTP");
            }
            else{
                notificationModel.setChannelType("EMAIL");
            }
            notificationModel.setMessage(emailBody);
            notificationModel.setEmail(emailOTP);
            notificationModel.setSubject(emailSubject);
            logger.info("request send to Non cambodian  notification API=" + notificationModel);
            genricResponse = notificationFeign.addNotifications(notificationModel);
        }


        logger.info("Notification API response=" + genricResponse);
    }

}
