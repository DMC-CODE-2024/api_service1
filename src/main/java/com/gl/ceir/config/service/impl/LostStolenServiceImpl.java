package com.gl.ceir.config.service.impl;

import com.gl.ceir.config.configuration.PropertiesReader;
import com.gl.ceir.config.feign.NotificationFeign;
import com.gl.ceir.config.model.app.*;
import com.gl.ceir.config.model.aud.AuditTrail;
import com.gl.ceir.config.model.oam.RequestHeaders;
import com.gl.ceir.config.repository.app.*;
import com.gl.ceir.config.repository.aud.AuditTrailRepository;
import com.gl.ceir.config.repository.oam.RequestHeadersRepository;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class LostStolenServiceImpl {

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
    SystemConfigurationDbRepository systemConfigurationDbRepository;

    @Autowired
    AuditTrailRepository auditTrailRepository;

    @Autowired
    RequestHeadersRepository requestHeadersRepository;

    @Autowired
    EirsResponseRepo eirsResponseRepo;

    @Autowired
    InvalidIMEIRepo invalidIMEIRepo;
    @Autowired
    PropertiesReader propertiesReader;

    @Autowired
    WebActionDbRepository webActionDbRepository;

    public GenricResponse saveLostStolenDevice(StolenLostModel stolenLostModel) {
        GenricResponse genricResponse = new GenricResponse();
        MDRModel mdrModel = new MDRModel();
        String OTPsms;
        logger.info("save stolen request in service impl = " + stolenLostModel);
        audiTrail(stolenLostModel.getPublicIp(), stolenLostModel.getBrowser(), stolenLostModel.getRequestId(), "Save-Device-Recovery", stolenLostModel.getUserAgent());
        List<String> tacList = new ArrayList<String>();

        if (stolenLostModel.getImei1() != null) {
            String tac = stolenLostModel.getImei1().substring(0, 8);
            tacList.add(tac);
        }
        if (stolenLostModel.getImei2() != null) {
            String tac = stolenLostModel.getImei2().substring(0, 8);
            tacList.add(tac);
        }
        if (stolenLostModel.getImei3() != null) {
            String tac = stolenLostModel.getImei3().substring(0, 8);
            tacList.add(tac);
        }
        if (stolenLostModel.getImei4() != null) {
            String tac = stolenLostModel.getImei4().substring(0, 8);
            tacList.add(tac);
        }
        boolean tacResult = multipleTacValidation(tacList, stolenLostModel.getDeviceBrand(), stolenLostModel.getDeviceModel());
        //   checking TAC validation
        if (!tacResult) {
            logger.info("TAC details invalid ");
            genricResponse.setStatusCode("201");
            genricResponse.setMessage("Invalid TAC");
            genricResponse.setRequestID(stolenLostModel.getRequestId());
            return genricResponse;

        }
        //checking duplicate IMEI in lost device mgmt  table

        if (!Objects.isNull(this.checkIMEIinMgmt(stolenLostModel.getImei1()))) {
            String failRemark = eirsResponseRepo.getByTag("fail_duplicate_message", stolenLostModel.getLanguage());
            logger.info("Check IMEI1 in  duplicate IMEI check in mgmt table");
            genricResponse.setStatusCode("502");
            genricResponse.setMessage("Duplicate IMEI Found");
            genricResponse.setRequestID(stolenLostModel.getRequestId());
            genricResponse.setTag("1");
            saveFailScenario(stolenLostModel, failRemark);
            return genricResponse;
        }
        if (Objects.nonNull(stolenLostModel.getImei2())) {
            if (!Objects.isNull(this.checkIMEIinMgmt(stolenLostModel.getImei2()))) {
                logger.info("Check IMEI2 in  duplicate IMEI check in mgmt table");
                String failRemark = eirsResponseRepo.getByTag("fail_duplicate_message", stolenLostModel.getLanguage());
                genricResponse.setStatusCode("502");
                genricResponse.setMessage("Duplicate IMEI Found");
                genricResponse.setRequestID(stolenLostModel.getRequestId());
                genricResponse.setTag("2");
                saveFailScenario(stolenLostModel, failRemark);
                return genricResponse;
            }
        }
        if (Objects.nonNull(stolenLostModel.getImei3())) {
            if (!Objects.isNull(this.checkIMEIinMgmt(stolenLostModel.getImei3()))) {
                logger.info("Check IMEI3 in  duplicate IMEI check in mgmt table");
                String failRemark = eirsResponseRepo.getByTag("fail_duplicate_message", stolenLostModel.getLanguage());
                genricResponse.setStatusCode("502");
                genricResponse.setMessage("Duplicate IMEI Found");
                genricResponse.setRequestID(stolenLostModel.getRequestId());
                genricResponse.setTag("3");
                saveFailScenario(stolenLostModel, failRemark);
                return genricResponse;
            }
        }
        if (Objects.nonNull(stolenLostModel.getImei4())) {
            if (!Objects.isNull(this.checkIMEIinMgmt(stolenLostModel.getImei4()))) {
                logger.info("Check IMEI4 in  duplicate IMEI check in mgmt table");
                String failRemark = eirsResponseRepo.getByTag("fail_duplicate_message", stolenLostModel.getLanguage());
                genricResponse.setStatusCode("502");
                genricResponse.setMessage("Duplicate IMEI Found");
                genricResponse.setRequestID(stolenLostModel.getRequestId());
                genricResponse.setTag("4");
                saveFailScenario(stolenLostModel, failRemark);
                return genricResponse;
            }
        }


        //checking duplicate IMEI in detail table
        LostStolenMgmt imeiExist1 = stolenLostDetailRepo.findByImei(stolenLostModel.getImei1());
        LostStolenMgmt imeiExist2 = stolenLostDetailRepo.findByImei(stolenLostModel.getImei2());
        LostStolenMgmt imeiExist3 = stolenLostDetailRepo.findByImei(stolenLostModel.getImei3());
        LostStolenMgmt imeiExist4 = stolenLostDetailRepo.findByImei(stolenLostModel.getImei4());
        if (!Objects.isNull(imeiExist1) || !Objects.isNull(imeiExist2) || !Objects.isNull(imeiExist3) || !Objects.isNull(imeiExist4)) {
            logger.info("dupicate IMEI found");
            String failRemark = eirsResponseRepo.getByTag("fail_duplicate_message", stolenLostModel.getLanguage());
            genricResponse.setStatusCode("502");
            genricResponse.setMessage("Duplicate IMEI Found");
            saveFailScenario(stolenLostModel, failRemark);
            return genricResponse;
        }
        //check imei is valid or invalid
        boolean imei1IsValidOrInvalid = invalidIMEIRepo.existsByImei(stolenLostModel.getImei1().substring(0, 14));
        if (imei1IsValidOrInvalid) {
            String failRemark = eirsResponseRepo.getByTag("fail_invalid_message", stolenLostModel.getLanguage());
            genricResponse.setStatusCode("503");
            genricResponse.setMessage("Invalid IMEI Found");
            saveFailScenario(stolenLostModel, failRemark);
            return genricResponse;
        }
        if (Objects.nonNull(stolenLostModel.getImei2())) {
            boolean imei2IsValidOrInvalid = invalidIMEIRepo.existsByImei(stolenLostModel.getImei2().substring(0, 14));
            if (imei2IsValidOrInvalid) {
                String failRemark = eirsResponseRepo.getByTag("fail_invalid_message", stolenLostModel.getLanguage());
                genricResponse.setStatusCode("503");
                genricResponse.setMessage("Invalid IMEI Found");
                saveFailScenario(stolenLostModel, failRemark);
                return genricResponse;
            }
        }
        if (Objects.nonNull(stolenLostModel.getImei3())) {
            boolean imei3IsValidOrInvalid = invalidIMEIRepo.existsByImei(stolenLostModel.getImei3().substring(0, 14));
            if (imei3IsValidOrInvalid) {
                String failRemark = eirsResponseRepo.getByTag("fail_invalid_message", stolenLostModel.getLanguage());
                genricResponse.setStatusCode("503");
                genricResponse.setMessage("Invalid IMEI Found");
                saveFailScenario(stolenLostModel, failRemark);
                return genricResponse;
            }
        }
        if (Objects.nonNull(stolenLostModel.getImei3())) {
            boolean imei4IsValidOrInvalid = invalidIMEIRepo.existsByImei(stolenLostModel.getImei4().substring(0, 14));
            if (imei4IsValidOrInvalid) {
                String failRemark = eirsResponseRepo.getByTag("fail_invalid_message", stolenLostModel.getLanguage());
                genricResponse.setStatusCode("503");
                genricResponse.setMessage("Invalid IMEI Found");
                saveFailScenario(stolenLostModel, failRemark);
                return genricResponse;
            }
        }


        //generate OTP
        OTPService otp = new OTPService();
        OTPsms = otp.phoneOtp();
        stolenLostModel.setStatus("INIT_START");
        stolenLostModel.setUserStatus("INIT_START");
        stolenLostModel.setOtp(OTPsms);
        //saving in stolen_mgmt table
        lostStolenRepo.save(stolenLostModel);
        /// calling Notification API
        EirsResponse eirsResponse = new EirsResponse();
        EirsResponse eirsResponseEmail = new EirsResponse();
        eirsResponse = eirsResponseRepo.getByTagAndLanguage("stolen_notification_msg", stolenLostModel.getLanguage());
        eirsResponseEmail = eirsResponseRepo.getByTagAndLanguage("stolen_notification_msg_email", stolenLostModel.getLanguage());

        String message = eirsResponse.getValue().replace("<otp>", OTPsms);
        String messageEmail = eirsResponseEmail.getValue().replace("<otp>", OTPsms);
        String messageSubject = eirsResponseEmail.getSubject();
        NotificationAPI(stolenLostModel.getContactNumberForOtp(), stolenLostModel.getDeviceOwnerNationality(), stolenLostModel.getOtpEmail(), message, stolenLostModel.getRequestId(),propertiesReader.stolenFeatureName, stolenLostModel.getLanguage(), eirsResponse.getDescription(), "SMS_OTP","",messageEmail,messageSubject);
        genricResponse.setRequestID(stolenLostModel.getRequestId());
        genricResponse.setStatusCode("200");
        genricResponse.setRequestID(stolenLostModel.getRequestId());
        genricResponse.setTxnId(stolenLostModel.getDeviceOwnerNationality());
        logger.info("Nationality=" + stolenLostModel.getDeviceOwnerNationality());
        if (stolenLostModel.getDeviceOwnerNationality().equalsIgnoreCase("0")) {
                genricResponse.setTag(stolenLostModel.getContactNumberForOtp());
        } else {
            genricResponse.setTag(stolenLostModel.getOtpEmail());
        }

        logger.info("response return=" + genricResponse);
        return genricResponse;
    }

    public GenricResponse updateLostStolenDevice(StolenLostModel stolenLostModel) {
        GenricResponse genricResponse = new GenricResponse();


        StolenLostModel res = lostStolenRepo.findByRequestId(stolenLostModel.getRequestId());
        //Entry in audit tail
        audiTrail(stolenLostModel.getPublicIp(), stolenLostModel.getBrowser(), stolenLostModel.getRequestId(), "Update-Device-Stolen", stolenLostModel.getUserAgent());
        stolenLostModel.setId(res.getId());
        stolenLostModel.setStatus("INIT");
        stolenLostModel.setUserStatus("Started");
        logger.info("update stolen request in service impl = " + stolenLostModel);
        //update in stolen_mgmt table
        lostStolenRepo.save(stolenLostModel);
        /// calling Notification API
        EirsResponse eirsResponse = new EirsResponse();
        EirsResponse eirsResponseEmail = new EirsResponse();
        eirsResponse = eirsResponseRepo.getByTagAndLanguage("stolenUpdate_success", stolenLostModel.getLanguage());
        eirsResponseEmail = eirsResponseRepo.getByTagAndLanguage("stolenUpdate_success_email", stolenLostModel.getLanguage());
        String message = eirsResponse.getValue().replace("<requestId>", stolenLostModel.getRequestId());
        String subject = eirsResponse.getDescription().replace("<requestId>", stolenLostModel.getRequestId());
        String messageEmail = eirsResponseEmail.getValue().replace("<requestId>", stolenLostModel.getRequestId());
        String subjectEmail = eirsResponseEmail.getSubject().replace("<requestId>", stolenLostModel.getRequestId());
        NotificationAPI(stolenLostModel.getContactNumberForOtp(), stolenLostModel.getDeviceOwnerNationality(), stolenLostModel.getOtpEmail(), message, stolenLostModel.getRequestId(), propertiesReader.stolenFeatureName, stolenLostModel.getLanguage(), subject, "SMS",stolenLostModel.getDeviceOwnerEmail(),messageEmail,subjectEmail);

        genricResponse.setStatusCode("200");
        genricResponse.setRequestID(stolenLostModel.getRequestId());
        logger.info("-------response returned=" + genricResponse);
        return genricResponse;
    }


    public void NotificationAPI(String msisdn, String nationality, String emailOTP, String msg, String requestId, String featureName, String lang, String subject, String channel,String email ,String emailBody,String emailSubject) {
        NotificationModel notificationModel = new NotificationModel();
        GenricResponse genricResponse = new GenricResponse();

        notificationModel.setFeatureName(featureName);
        notificationModel.setFeatureTxnId(requestId);
        notificationModel.setMsgLang(lang);
        logger.info("msisdn=" + msisdn);
        if (nationality.equalsIgnoreCase("0")) {
            notificationModel.setChannelType(channel);
            notificationModel.setMsisdn(msisdn);
            notificationModel.setMessage(msg);
            logger.info("request send to sms notification API=" + notificationModel);
            genricResponse = notificationFeign.addNotifications(notificationModel);
            if (!channel.contains("OTP")) {
                notificationModel.setChannelType("EMAIL");
                notificationModel.setEmail(email);
                notificationModel.setSubject(emailSubject);
                notificationModel.setMessage(emailBody);
                logger.info("request send to email notification API=" + notificationModel);
                genricResponse = notificationFeign.addNotifications(notificationModel);
            }
        } else {
            if (channel.contains("OTP")) {
                notificationModel.setChannelType("EMAIL_OTP");
            } else {
                notificationModel.setChannelType("EMAIL");
            }
            notificationModel.setMessage(emailBody);
            notificationModel.setEmail(emailOTP);
            notificationModel.setSubject(emailSubject);
            logger.info("request send to Non cambodian notification API=" + notificationModel);
            genricResponse = notificationFeign.addNotifications(notificationModel);
        }

        logger.info("Notification API response=" + genricResponse);
    }


    public GenricResponse saveRecoveryDevice1(StolenLostModel stolenLostModel) {
        GenricResponse genricResponse = new GenricResponse();
        logger.info("save recovery request in service impl = " + stolenLostModel);
        audiTrail(stolenLostModel.getPublicIp(), stolenLostModel.getBrowser(), stolenLostModel.getRequestId(), "Search-Device-Recovery", stolenLostModel.getUserAgent());
        List<StolenLostModel> res = new ArrayList<StolenLostModel>();
        List<StolenLostModel> res1 = new ArrayList<StolenLostModel>();
        if (!stolenLostModel.getRequestId().isEmpty()) {
            logger.info("save recovery 1");
            res = lostStolenRepo.findByRequestId4(stolenLostModel.getRequestId());
        } else if (!stolenLostModel.getContactNumberForOtp().isEmpty()) {
            logger.info("save recovery 2");
            String acctualContactNo = stolenLostModel.getContactNumberForOtp().substring(stolenLostModel.getContactNumberForOtp().length() - 9);
            res = lostStolenRepo.findByContactNumberForOtp(stolenLostModel.getContactNumberForOtp(), acctualContactNo);
        }

        if (Objects.isNull(res)) {
            logger.info("request id is not found");
            genricResponse.setStatusCode("201");
            genricResponse.setMessage("request id not found");
            return genricResponse;
        }
        genricResponse.setStatusCode("200");
        StolenLostModel stolen1 = null;
        for (StolenLostModel stolen : res) {
            stolen1 = new StolenLostModel();
            BeanUtils.copyProperties(stolen, stolen1);
            logger.info("data set for user status   " + stolen1);
            if (stolen.getStatus().equalsIgnoreCase("INIT")) {
                stolen1.setUserStatus("Started");
            } else if (stolen.getStatus().equalsIgnoreCase("VERIFY_MOI")) {
                stolen1.setUserStatus("Pending");
            } else if (stolen.getStatus().equalsIgnoreCase("REJECT_MOI")) {
                stolen1.setUserStatus("Rejected");
            } else if (stolen.getStatus().equalsIgnoreCase("START")) {
                stolen1.setUserStatus("Pending");
            } else if (stolen.getStatus().equalsIgnoreCase("DONE") && stolen.getRequestType().equalsIgnoreCase("Stolen")) {
                stolen1.setUserStatus("Blocked");
            } else if (stolen.getStatus().equalsIgnoreCase("DONE") && stolen.getRequestType().equalsIgnoreCase("Recover")) {
                stolen1.setUserStatus("Unblocked");
            } else if (stolen.getStatus().equalsIgnoreCase("INIT") && stolen.getRequestType().equalsIgnoreCase("Recover")) {
                stolen1.setUserStatus("Started");
            }
            res1.add(stolen1);
        }
        genricResponse.setData(res1);
        return genricResponse;

    }

    public GenricResponse checkStatus1(StolenLostModel stolenLostModel) {
        GenricResponse genricResponse = new GenricResponse();
        logger.info("check status record = " + stolenLostModel);
        audiTrail(stolenLostModel.getPublicIp(), stolenLostModel.getBrowser(), stolenLostModel.getRequestId(), "Check-Device-Status", stolenLostModel.getUserAgent());
        List<StolenLostModel> res = new ArrayList<StolenLostModel>();
        List<StolenLostModel> res1 = new ArrayList<StolenLostModel>();
        if (!stolenLostModel.getRequestId().isEmpty()) {
            logger.info("save recovery 1");
            res = lostStolenRepo.findByRequestId1(stolenLostModel.getRequestId());
        } else if (!stolenLostModel.getContactNumberForOtp().isEmpty()) {
            logger.info("save recovery 2");
            String acctualContactNo = stolenLostModel.getContactNumberForOtp().substring(stolenLostModel.getContactNumberForOtp().length() - 9);
            res = lostStolenRepo.findByContactNumberForOtp(stolenLostModel.getContactNumberForOtp(), acctualContactNo);


            if (Objects.isNull(res)) {
                logger.info("request id is not found");
                genricResponse.setStatusCode("201");
                genricResponse.setMessage("request id not found");
                return genricResponse;
            }
        }
        genricResponse.setStatusCode("200");
        StolenLostModel stolen1 = null;
        for (StolenLostModel stolen : res) {
            stolen1 = new StolenLostModel();
            BeanUtils.copyProperties(stolen, stolen1);
            logger.info("data set for user status   " + stolen1);
            if (stolen.getStatus().equalsIgnoreCase("INIT")) {
                stolen1.setUserStatus("Started");
            } else if (stolen.getStatus().equalsIgnoreCase("VERIFY_MOI")) {
                stolen1.setUserStatus("Pending");
            } else if (stolen.getStatus().equalsIgnoreCase("REJECT_MOI")) {
                stolen1.setUserStatus("Rejected");
            } else if (stolen.getStatus().equalsIgnoreCase("START")) {
                stolen1.setUserStatus("Pending");
            } else if (stolen.getStatus().equalsIgnoreCase("DONE") && stolen.getRequestType().equalsIgnoreCase("Stolen")) {
                stolen1.setUserStatus("Blocked");
            } else if (stolen.getStatus().equalsIgnoreCase("DONE") && stolen.getRequestType().equalsIgnoreCase("Recover")) {
                stolen1.setUserStatus("Unblocked");
            } else if (stolen.getStatus().equalsIgnoreCase("INIT") && stolen.getRequestType().equalsIgnoreCase("Recover")) {
                stolen1.setUserStatus("Started");
            }
            else if (stolen.getStatus().equalsIgnoreCase("Fail")) {
                stolen1.setUserStatus("Failed");
            }

            res1.add(stolen1);
        }


        genricResponse.setData(res1);
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
        eirsResponse = eirsResponseRepo.getByTagAndLanguage("resend_notification_msg", res.getLanguage());
        eirsResponseEmail = eirsResponseRepo.getByTagAndLanguage("resend_notification_msg_email", res.getLanguage());
        String message = eirsResponse.getValue().replace("<otp>", OTPsms);
        String messageEmail = eirsResponseEmail.getValue().replace("<otp>", OTPsms);
        String messageSubject = eirsResponseEmail.getSubject();

        NotificationAPI(res.getContactNumberForOtp(), res.getDeviceOwnerNationality(), res.getOtpEmail(), message, requestID, propertiesReader.stolenFeatureName, res.getLanguage(), eirsResponse.getDescription(), "SMS_OTP","",messageEmail,messageSubject);// calling notification API
        genricResponse.setStatusCode("200");
        genricResponse.setMessage("Resend OTP is succesfull");
        return genricResponse;

    }

    @Transactional
    public GenricResponse verifyOTP(String requestID, String otp, String requestType, String oldrequestID) {
        GenricResponse genricResponse = new GenricResponse();
        logger.info("inside Otp verification block , request Id =" + requestID + " , OTP is =" + otp + " , old request id = " + oldrequestID + " , requestType =" + requestType);
        StolenLostModel res = lostStolenRepo.findByRequestId(requestID);
        String email = "";
        //StolenLostModel previousdata=lostStolenRepo.findByRequestId(requestID);
        LocalDateTime dateTime2 = LocalDateTime.now();
        Duration duration = Duration.between(res.getModified_on(), dateTime2);
        long differenceInSeconds = duration.getSeconds();
        logger.info("seconds===++===" + differenceInSeconds);

        if (differenceInSeconds >= 120) {
            logger.info("OTP enter  time is expired");
            genricResponse.setStatusCode("202");
            genricResponse.setMessage("OTP enter  time is expired");
            genricResponse.setRequestID(requestID);
            return genricResponse;
        }
        logger.info(" response from DB=" + res);
        if (res.getOtp().equals(otp)) {
            logger.info("verification successfull");
            res.setStatus("INIT");
            res.setUserStatus("Started");
            if (requestType.equalsIgnoreCase("Recover")) {
                res.setLostId(oldrequestID);
            }
            lostStolenRepo.save(res);//updating init  status in stolen_mgmt table
            //lostStolenRepo.updateByRequestId(oldrequestID,requestType);


            EirsResponse eirsResponse = new EirsResponse();
            EirsResponse eirsResponseEmail = new EirsResponse();
            if (requestType.equalsIgnoreCase("Stolen")) {
                eirsResponse = eirsResponseRepo.getByTagAndLanguage("stolen_success", res.getLanguage());
                eirsResponseEmail = eirsResponseRepo.getByTagAndLanguage("stolen_success_email", res.getLanguage());
                //res.setDeviceOwnerNationality("1");
            } else if (requestType.equalsIgnoreCase("Recover")) {
                eirsResponse = eirsResponseRepo.getByTagAndLanguage("recovery_success", res.getLanguage());
                eirsResponseEmail = eirsResponseRepo.getByTagAndLanguage("recovery_success_email", res.getLanguage());
                WebActionDb webActionDb = new WebActionDb();
                webActionDb.setTxnId(res.getRequestId());
                webActionDb.setState(1);
                webActionDb.setFeature("MOI");
                webActionDb.setSubFeature("Recover");
                webActionDbRepository.save(webActionDb);
                //res.setDeviceOwnerNationality("1");
            }

            String message = eirsResponse.getValue().replace("<requestId>", res.getRequestId()).replace("<contactNumber>", res.getContactNumberForOtp());
            String subject = eirsResponse.getDescription().replace("<requestId>", res.getRequestId()).replace("<contactNumber>", res.getContactNumberForOtp());
            String emailMessage = eirsResponseEmail.getValue().replace("<requestId>", res.getRequestId()).replace("<contactNumber>", res.getContactNumberForOtp());
            String emailSubject = eirsResponseEmail.getSubject().replace("<requestId>", res.getRequestId()).replace("<contactNumber>", res.getContactNumberForOtp());
            NotificationAPI(res.getContactNumberForOtp(), res.getDeviceOwnerNationality(), res.getOtpEmail(), message, res.getRequestId(), propertiesReader.stolenFeatureName, res.getLanguage(), subject, "SMS",res.getDeviceOwnerEmail(),emailMessage,emailSubject);

            genricResponse.setStatusCode("200");
            genricResponse.setMessage("verification successful");
            genricResponse.setRequestID(requestID);
            genricResponse.setTag(requestType);
        } else {
            logger.info("verification failed");
            genricResponse.setStatusCode("201");
            genricResponse.setMessage("verification failed");
            genricResponse.setRequestID(requestID);
            res.setOtpRetryCount(res.getOtpRetryCount() + 1);
            lostStolenRepo.save(res);//updating init  status in stolen_mgmt table
        }
        return genricResponse;

    }


    @Transactional
    public GenricResponse verifyOTPUpdateStolen(String requestID, String otp, String requestType, String oldrequestID) {
        GenricResponse genricResponse = new GenricResponse();
        logger.info("inside Otp verification block for check Status request , request Id =" + requestID + " , OTP is =" + otp + " , old request id = " + oldrequestID + " , requestType =" + requestType);
        StolenLostModel res = lostStolenRepo.findByRequestId(requestID);
        String email = "";
        //StolenLostModel previousdata=lostStolenRepo.findByRequestId(requestID);
        logger.info(" response from DB=" + res);
        LocalDateTime dateTime2 = LocalDateTime.now();
        Duration duration = Duration.between(res.getModified_on(), dateTime2);
        long differenceInSeconds = duration.getSeconds();
        logger.info("seconds===++===" + differenceInSeconds);

        if (differenceInSeconds >= 120) {
            logger.info("OTP enter  time is expired");
            genricResponse.setStatusCode("202");
            genricResponse.setMessage("OTP enter  time is expired");
            genricResponse.setRequestID(requestID);
            return genricResponse;
        }
        if (res.getOtp().equals(otp)) {
            logger.info("verification successfull");
            genricResponse.setStatusCode("200");
            genricResponse.setMessage("verification successfull");
            SystemConfigurationDb filesUrl = new SystemConfigurationDb();
            filesUrl = systemConfigurationDbRepository.getByTag("upload_file_link");
            res.setFirCopyUrl(filesUrl.getValue().replace("$LOCAL_IP", propertiesReader.localIp));
            genricResponse.setData(res);
        } else {
            logger.info("verification failed");
            genricResponse.setStatusCode("201");
            genricResponse.setMessage("verification failed");
            genricResponse.setRequestID(requestID);
        }
        return genricResponse;

    }


    public StolenLostModel setLostStolenMgmt(StolenLostModel stolenLostModel) {
        StolenLostModel lostStolenMgmt = new StolenLostModel();
        lostStolenMgmt.setContactNumber(stolenLostModel.getContactNumber());
        lostStolenMgmt.setDeviceBrand(stolenLostModel.getDeviceBrand());
        lostStolenMgmt.setDeviceModel(stolenLostModel.getDeviceModel());
        lostStolenMgmt.setImei1(stolenLostModel.getImei1());
        lostStolenMgmt.setImei2(stolenLostModel.getImei2());
        lostStolenMgmt.setImei3(stolenLostModel.getImei3());
        lostStolenMgmt.setImei4(stolenLostModel.getImei4());
        lostStolenMgmt.setDeviceLostDdateTime(stolenLostModel.getDeviceLostDdateTime());
        lostStolenMgmt.setProvince(stolenLostModel.getProvince());
        lostStolenMgmt.setDistrict(stolenLostModel.getDistrict());
        lostStolenMgmt.setCommune(stolenLostModel.getCommune());
        lostStolenMgmt.setPoliceStation(stolenLostModel.getPoliceStation());
        lostStolenMgmt.setDeviceOwnerNationality(stolenLostModel.getDeviceOwnerNationality());
        lostStolenMgmt.setDeviceOwnerName(stolenLostModel.getDeviceOwnerName());
        lostStolenMgmt.setDeviceOwnerEmail(stolenLostModel.getDeviceOwnerEmail());
        lostStolenMgmt.setOwnerDOB(stolenLostModel.getOwnerDOB());
        lostStolenMgmt.setDeviceOwnerAddress(stolenLostModel.getDeviceOwnerAddress());
        lostStolenMgmt.setDeviceOwnerAddress2(stolenLostModel.getDeviceOwnerAddress2());
        lostStolenMgmt.setDeviceOwnerNationalID(stolenLostModel.getDeviceOwnerNationalID());
        lostStolenMgmt.setDeviceOwnerNationalIdUrl(stolenLostModel.getDeviceOwnerNationalIdUrl());
        lostStolenMgmt.setPassportNumber(stolenLostModel.getPassportNumber());
        lostStolenMgmt.setOtpEmail(stolenLostModel.getOtpEmail());
        lostStolenMgmt.setContactNumberForOtp(stolenLostModel.getContactNumberForOtp());
        lostStolenMgmt.setOldRequestId(stolenLostModel.getOldRequestId());
        lostStolenMgmt.setMobileInvoiceBill(stolenLostModel.getMobileInvoiceBill());
        //lostStolenMgmt.setRequestId(stolenLostModel.getRequestId());
        lostStolenMgmt.setRequestType("Recover");
        lostStolenMgmt.setStatus("INIT_START");
        lostStolenMgmt.setUserStatus("INIT_START");
        lostStolenMgmt.setSerialNumber(stolenLostModel.getSerialNumber());
        lostStolenMgmt.setLostId(stolenLostModel.getLostId());
        lostStolenMgmt.setLanguage(stolenLostModel.getLanguage());
        lostStolenMgmt.setCreatedBy(stolenLostModel.getCreatedBy());
        lostStolenMgmt.setRequestMode(stolenLostModel.getRequestMode());
        lostStolenMgmt.setCategory(stolenLostModel.getCategory());
        lostStolenMgmt.setDeviceType(stolenLostModel.getDeviceType());
        return lostStolenMgmt;

    }

    public GenricResponse getOtp(StolenLostModel stolenLostModel) {
        logger.info("inside OTP request =" + stolenLostModel);
        audiTrail(stolenLostModel.getPublicIp(), stolenLostModel.getBrowser(), stolenLostModel.getRequestId(), "Recovery-OTP", stolenLostModel.getUserAgent());
        GenricResponse genricResponse = new GenricResponse();
        String OTPsms;
        StolenLostModel existingData = new StolenLostModel();
        StolenLostModel saveNewData = new StolenLostModel();
        existingData = lostStolenRepo.findByRequestId(stolenLostModel.getOldRequestId());
        if (Objects.isNull(existingData)) {
            logger.info("request id is not found");
            genricResponse.setStatusCode("201");
            genricResponse.setMessage("request id not found");
            return genricResponse;
        }
        //checking duplicate IMEI in mgmt table
        StolenLostModel imeiExist1 = lostStolenRepo.checkduplicateRecovery(existingData.getImei1());
        StolenLostModel imeiExist2 = lostStolenRepo.checkduplicateRecovery(existingData.getImei2());
        StolenLostModel imeiExist3 = lostStolenRepo.checkduplicateRecovery(existingData.getImei3());
        StolenLostModel imeiExist4 = lostStolenRepo.checkduplicateRecovery(existingData.getImei4());
        if (!Objects.isNull(imeiExist1) || !Objects.isNull(imeiExist2) || !Objects.isNull(imeiExist3) || !Objects.isNull(imeiExist4)) {
            logger.info("duplicate IMEI found in mgmt table for recovery request");
            String failRemark = eirsResponseRepo.getByTag("fail_duplicate_message", stolenLostModel.getLanguage());
            genricResponse.setStatusCode("502");
            genricResponse.setMessage("Duplicate IMEI Found");
            saveNewData = setLostStolenMgmt(existingData);
            saveFailScenario(stolenLostModel, failRemark);
            return genricResponse;
        }

        OTPService otp = new OTPService();
        // generate OTP
        OTPsms = otp.phoneOtp();
        logger.info(" OTP=" + OTPsms);
        saveNewData = setLostStolenMgmt(existingData);
        logger.info("data set from set method==*****=======" + saveNewData);
        saveNewData.setRequestId(stolenLostModel.getRequestId());
        saveNewData.setOtp(OTPsms);
        saveNewData.setRecoveryReason(stolenLostModel.getRecoveryReason());
        saveNewData.setProvince(stolenLostModel.getProvince());
        saveNewData.setDistrict(stolenLostModel.getDistrict());
        saveNewData.setCommune(stolenLostModel.getCommune());
        logger.info("add new  record in mgmt table=" + saveNewData);
        lostStolenRepo.save(saveNewData);
        // updating in stolen_mgmt table
        EirsResponse eirsResponse = new EirsResponse();
        EirsResponse eirsResponseEmail = new EirsResponse();
        eirsResponse = eirsResponseRepo.getByTagAndLanguage("recovery_notification_msg", stolenLostModel.getLanguage());
        eirsResponseEmail = eirsResponseRepo.getByTagAndLanguage("recovery_notification_msg_email", stolenLostModel.getLanguage());
        String message = eirsResponse.getValue().replace("<otp>", OTPsms);
        String messageEmail = eirsResponseEmail.getValue().replace("<otp>", OTPsms);
        String messageSubject = eirsResponseEmail.getSubject();
        NotificationAPI(saveNewData.getContactNumberForOtp(), saveNewData.getDeviceOwnerNationality(), saveNewData.getOtpEmail(), message, stolenLostModel.getRequestId(),propertiesReader.stolenFeatureName, stolenLostModel.getLanguage(), eirsResponse.getDescription(), "SMS_OTP","",messageEmail,messageSubject);
        // calling notification API
        genricResponse.setStatusCode("200");
        genricResponse.setMessage(OTPsms);
        genricResponse.setData(saveNewData);
        return genricResponse;
    }

    @Transactional
    public GenricResponse getOtpForCheckStatus(StolenLostModel stolenLostModel) {
        logger.info("inside OTP request for check status =" + stolenLostModel);
        audiTrail(stolenLostModel.getPublicIp(), stolenLostModel.getBrowser(), stolenLostModel.getRequestId(), "Check Status-OTP", stolenLostModel.getUserAgent());
        GenricResponse genricResponse = new GenricResponse();
        String OTPsms;
        StolenLostModel existingData = new StolenLostModel();
        //StolenLostModel saveNewData= new StolenLostModel();
        existingData = lostStolenRepo.findByRequestId(stolenLostModel.getRequestId());
        logger.info("existing data in old request id=========" + existingData);
        if (Objects.isNull(existingData)) {
            logger.info("request id is not found");
            genricResponse.setStatusCode("201");
            genricResponse.setMessage("request id not found");
            return genricResponse;
        }
        OTPService otp = new OTPService();
        // generate OTP
        OTPsms = otp.phoneOtp();
        logger.info(" OTP=" + OTPsms);
        lostStolenRepo.updateOtp(OTPsms, stolenLostModel.getRequestId());
        // updating in stolen_mgmt table
        EirsResponse eirsResponse = new EirsResponse();
        EirsResponse eirsResponseEmail = new EirsResponse();
        eirsResponse = eirsResponseRepo.getByTagAndLanguage("checkStatus_notification_msg", stolenLostModel.getLanguage());
        eirsResponseEmail = eirsResponseRepo.getByTagAndLanguage("checkStatus_notification_msg_email", stolenLostModel.getLanguage());
        String message = eirsResponse.getValue().replace("<otp>", OTPsms);
        String messageEmail = eirsResponseEmail.getValue().replace("<otp>", OTPsms);
        String messageSubject = eirsResponseEmail.getSubject();

        NotificationAPI(existingData.getContactNumberForOtp(), existingData.getDeviceOwnerNationality(), existingData.getOtpEmail(), message, stolenLostModel.getRequestId(),propertiesReader.stolenFeatureName, stolenLostModel.getLanguage(), eirsResponse.getDescription(), "SMS_OTP","",messageEmail,messageSubject);
        // calling notification API
        genricResponse.setStatusCode("200");
        //genricResponse.setMessage(OTPsms);
        genricResponse.setData(existingData);
        return genricResponse;
    }

    @Transactional
    public GenricResponse getOtpForCancelRequest(StolenLostModel stolenLostModel) {
        audiTrail(stolenLostModel.getPublicIp(), stolenLostModel.getBrowser(), stolenLostModel.getRequestId(), "Check Status-OTP", stolenLostModel.getUserAgent());
        GenricResponse genricResponse = new GenricResponse();
        String OTPsms;
        StolenLostModel existingData = new StolenLostModel();
        existingData = lostStolenRepo.checkForCancelRequest(stolenLostModel.getRequestId());
        if (Objects.isNull(existingData)) {
            genricResponse.setStatusCode("201");
            genricResponse.setMessage("request can not be cancelled");
            return genricResponse;
        }
        // generate OTP
        OTPService otp = new OTPService();
        OTPsms = otp.phoneOtp();
        existingData.setOtp(OTPsms);
        // updating otp in stolen_mgmt table
        lostStolenRepo.save(existingData);
        // Fetching messages from config tables
        EirsResponse eirsResponse = new EirsResponse();
        EirsResponse eirsResponseEmail = new EirsResponse();
        eirsResponse = eirsResponseRepo.getByTagAndLanguage("cancelRequest_otp_msg", stolenLostModel.getLanguage());
        eirsResponseEmail = eirsResponseRepo.getByTagAndLanguage("cancelRequest_otp_msg_email", stolenLostModel.getLanguage());

        String message = eirsResponse.getValue().replace("<otp>", OTPsms);
        String messageEmail = eirsResponseEmail.getValue().replace("<otp>", OTPsms);
        String messageSubject = eirsResponseEmail.getSubject();
        // calling notification API
        NotificationAPI(existingData.getContactNumberForOtp(), existingData.getDeviceOwnerNationality(), existingData.getOtpEmail(), message, stolenLostModel.getRequestId(),propertiesReader.stolenFeatureName, stolenLostModel.getLanguage(), eirsResponse.getDescription(), "SMS_OTP","",messageEmail,messageSubject);
        genricResponse.setStatusCode("200");
       // genricResponse.setMessage(OTPsms);
        genricResponse.setData(existingData);
        return genricResponse;
    }

    @Transactional
    public GenricResponse verifyCancelRequestOTP(String requestID, String otp, String requestType, String oldrequestID) {
        GenricResponse genricResponse = new GenricResponse();
        logger.info("inside cancel request Otp verification block , request Id =" + requestID + " , OTP is =" + otp + " , old request id = " + oldrequestID + " , requestType =" + requestType);
        StolenLostModel res = lostStolenRepo.findByRequestId(requestID);
        String email = "";
        LocalDateTime dateTime2 = LocalDateTime.now();
        Duration duration = Duration.between(res.getModified_on(), dateTime2);
        long differenceInSeconds = duration.getSeconds();
        logger.info("seconds===++===" + differenceInSeconds);

        if (differenceInSeconds >= 120) {
            logger.info("OTP enter  time is expired");
            genricResponse.setStatusCode("202");
            genricResponse.setMessage("OTP enter  time is expired");
            genricResponse.setRequestID(requestID);
            return genricResponse;
        }
        if (res.getOtp().equals(otp)) {
            logger.info("verification successfull");
            res.setStatus("INIT");
            res.setUserStatus("Started");
            lostStolenRepo.save(res);//updating init  status in stolen_mgmt table
            genricResponse.setStatusCode("200");
            genricResponse.setMessage("verification successful");
            genricResponse.setRequestID(requestID);
            genricResponse.setTag(requestType);
        } else {
            logger.info("verification failed");
            genricResponse.setStatusCode("201");
            genricResponse.setMessage("verification failed");
            genricResponse.setRequestID(requestID);
            res.setOtpRetryCount(res.getOtpRetryCount() + 1);
            lostStolenRepo.save(res);//updating init  status in stolen_mgmt table
        }
        return genricResponse;

    }

    public GenricResponse checkStatus(String requestID) {
        logger.info("inside check request id =" + requestID);
        GenricResponse genricResponse = new GenricResponse();
        String OTPsms;
        StolenLostModel res = lostStolenRepo.findByRequestId(requestID);
        audiTrail(res.getPublicIp(), res.getBrowser(), res.getRequestId(), "Check-Status", res.getUserAgent());
        logger.info("request to update  resend OTP=" + res);
        if (Objects.isNull(res)) {
            logger.info("request id is not found");
            genricResponse.setStatusCode("201");
            genricResponse.setMessage("request id not found");
            return genricResponse;
        }
        OTPService otp = new OTPService();// generate OTP
        OTPsms = otp.phoneOtp();
        logger.info(" OTP=" + OTPsms);
        res.setOtp(OTPsms);
        lostStolenRepo.save(res);// updating in stolen_mgmt table
        EirsResponse eirsResponse = new EirsResponse();
        eirsResponse = eirsResponseRepo.getByTagAndLanguage("checkStatus_notification_msg", res.getLanguage());
        String message = eirsResponse.getValue().replace("<otp>", OTPsms);
        NotificationAPI(res.getContactNumberForOtp(), res.getDeviceOwnerNationality(), res.getOtpEmail(), message, requestID,propertiesReader.stolenFeatureName, res.getLanguage(), eirsResponse.getDescription(), "SMS_OTP","","","");// calling notification API
        genricResponse.setStatusCode("200");
        genricResponse.setMessage(OTPsms);
        genricResponse.setData(res);
        return genricResponse;
    }

    public GenricResponse saveCancelRequest(StolenLostModel stolenLostModel) {
        GenricResponse genricResponse = new GenricResponse();
        StolenLostModel res = lostStolenRepo.findByRequestId(stolenLostModel.getRequestId());
        if (Objects.isNull(res)) {
            logger.info("request id is not found");
            genricResponse.setStatusCode("201");
            genricResponse.setMessage("request id not found");
            return genricResponse;
        }
        res.setStatus("Cancel");
        res.setUserStatus("Canceled");
        res.setRemarks(stolenLostModel.getRemarks());
        lostStolenRepo.save(res);// updating in stolen_mgmt table
        EirsResponse eirsResponse = new EirsResponse();
        EirsResponse eirsResponseEmail = new EirsResponse();

        eirsResponse = eirsResponseRepo.getByTagAndLanguage("cancelRequest_success_msg", res.getLanguage());
        eirsResponseEmail = eirsResponseRepo.getByTagAndLanguage("cancelRequest_success_msg_email", res.getLanguage());
        String message = eirsResponse.getValue().replace("<requestId>", res.getRequestId()).replace("<contactNumber>", res.getContactNumberForOtp());
        String subject = eirsResponse.getDescription().replace("<requestId>", res.getRequestId()).replace("<contactNumber>", res.getContactNumberForOtp());
        String emailMessage = eirsResponseEmail.getValue().replace("<requestId>", res.getRequestId()).replace("<contactNumber>", res.getContactNumberForOtp());
        String emailSubject = eirsResponseEmail.getSubject().replace("<requestId>", res.getRequestId()).replace("<contactNumber>", res.getContactNumberForOtp());
        NotificationAPI(res.getContactNumberForOtp(), res.getDeviceOwnerNationality(), res.getOtpEmail(), message, res.getRequestId(),propertiesReader.stolenFeatureName, res.getLanguage(), subject, "SMS",res.getDeviceOwnerEmail(),emailMessage,emailSubject);
        genricResponse.setStatusCode("200");
        genricResponse.setData(res);
        return genricResponse;
    }

    public boolean multipleTacValidation(List<String> tacs, String brand, String model) {
        logger.info("Inside TAC validation for the following TACs: " + tacs);
        boolean tacExist = false;
        if (tacs == null || tacs.isEmpty()) {
            return tacExist;
        }
        // Iterate through the TACs and compare the brand and model names
        for (String tac : tacs) {
            logger.info("Validating TAC: " + tac);
            // Fetch the MDRModel by TAC
            MDRModel mdrModel = mdrRepo.findByDeviceId(tac);
            logger.info(" TAC details for " + tac + " all details = " + mdrModel);
            // If the model was found
            if (mdrModel != null) {
                // Check if the current TAC's brand name or model name differs from the base
                if (mdrModel.getBrandName().equalsIgnoreCase(brand) && mdrModel.getModelName().equalsIgnoreCase(model)) {
                    logger.info("Brand name and model matched ");
                    tacExist = true;
                } else {
                    logger.info("Brand name and model not  matched ");
                    tacExist = false;
                }
            } else {
                logger.info("Tac not exist");
            }
        }
        // If no mismatches are found
        return tacExist;
    }

    public MDRModel tacValdation(String tac) {
        logger.info(" inside tac validation" + tac);
        MDRModel stolenLostModel = new MDRModel();
        stolenLostModel = mdrRepo.findByDeviceId(tac);
        return stolenLostModel;

    }


    public void audiTrail(String ip, String browser, String txnId, String subFeature, String userAgent) {
        //New File
        try {
            AuditTrail auditTrail = new AuditTrail();
            auditTrail.setFeatureName("Stolen-Recovery Portal");
            auditTrail.setSubFeature(subFeature);
            auditTrail.setFeatureId(1000001L);
            auditTrail.setPublicIp(ip);
            auditTrail.setBrowser(browser);
            auditTrail.setUserId(1000001L);
            auditTrail.setUserName("Public Portal");
            auditTrail.setUserType("NA");
            auditTrail.setRoleType("NA");
            auditTrail.setTxnId(txnId);
            logger.info(" going to save in auditTrail" + auditTrail);
            auditTrailRepository.save(auditTrail);
        } catch (Exception e) {
            // TODO: handle exception
        }
        try {
            RequestHeaders header = new RequestHeaders();
            header.setBrowser(browser);
            header.setPublicIp(ip);
            header.setUserAgent(userAgent);
            header.setUsername("Public Portal");
            header.setCreatedOn(LocalDateTime.now());
            header.setModifiedOn(LocalDateTime.now());
            logger.info(" going to save in portal access" + header);
            requestHeadersRepository.save(header);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }


    public StolenLostModel checkIMEIinMgmt(String imei) {
        logger.info(" duplicate imei check in mgmt table" + imei);
        StolenLostModel imeiExistMgmt = lostStolenRepo.findByImei1(imei);
        logger.info(" result from lost device mgmt table for duplicate imei " + imeiExistMgmt);
        return imeiExistMgmt;
    }

    public void saveFailScenario(StolenLostModel stolenLostModel, String remark) {
        stolenLostModel.setStatus("Fail");
        stolenLostModel.setUserStatus("Fail");
        stolenLostModel.setRemarks(remark);
        logger.info("Data save in mgmt table with Fail status" + stolenLostModel);
        lostStolenRepo.save(stolenLostModel);
    }

    public GenricResponse approvePoliceRequest(StolenLostModel stolenLostModel) {
        audiTrail(stolenLostModel.getPublicIp(), stolenLostModel.getBrowser(), stolenLostModel.getRequestId(), "Approve", stolenLostModel.getUserAgent());
        //lostStolenRepo.approveStolenRequest(stolenLostModel.getRemarks(),stolenLostModel.getFirCopyUrl(),stolenLostModel.getRequestId());
        StolenLostModel res = lostStolenRepo.findByRequestId(stolenLostModel.getRequestId());
        stolenLostModel.setId(res.getId());
        logger.info("Request to approve=" + stolenLostModel);
        stolenLostModel.setStatus("VERIFY_MOI");
        stolenLostModel.setCreatedOn(res.getCreatedOn());
        stolenLostModel.setOtp(res.getOtp());
        lostStolenRepo.save(stolenLostModel);
        GenricResponse genricResponse = new GenricResponse();
        genricResponse.setStatusCode("200");
        genricResponse.setTxnId(stolenLostModel.getRequestId());
        genricResponse.setMessage("Approve successful");
        return genricResponse;
    }


}