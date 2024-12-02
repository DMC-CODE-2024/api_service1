package com.gl.ceir.config.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gl.ceir.config.config.ConfigTags;
import com.gl.ceir.config.configuration.PropertiesReader;
import com.gl.ceir.config.model.app.AllRequest;
import com.gl.ceir.config.model.app.FileDetails;
import com.gl.ceir.config.model.app.SystemConfigurationDb;
import com.gl.ceir.config.model.aud.AuditTrail;
import com.gl.ceir.config.model.constants.Features;
import com.gl.ceir.config.repository.aud.AuditTrailRepository;
import com.gl.ceir.config.util.InterpSetter;
import com.gl.ceir.config.util.Utility;

@Service
public class FileServiceImpl {

    private static final Logger logger = LogManager.getLogger(FileServiceImpl.class);

    @Autowired
    AuditTrailRepository auditTrailRepository;

    @Autowired
    PropertiesReader propertiesReader;

    @Autowired
    Utility utility;

    @Autowired
    StateMgmtServiceImpl stateMgmtServiceImpl;

    @Autowired
    ConfigurationManagementServiceImpl configurationManagementServiceImpl;

    @Autowired
    InterpSetter interpSetter;

    public FileDetails getSampleFile(int featureId, AllRequest request) {

        String fileName = null;
        SystemConfigurationDb systemConfigurationDb = configurationManagementServiceImpl.findByTag(ConfigTags.sample_file_link);
        String featureName = null;
        if (featureId == 3) {
            featureName = Features.CONSIGNMENT;
        } else if (featureId == 4) {
            featureName = Features.STOCK;
        } else if (featureId == 12) {
            featureName = Features.REGISTER_DEVICE;
        } else if (featureId == 5) {
            featureName = Features.STOLEN_RECOVERY;
        } else if (featureId == 7) {
            featureName = Features.BLOCK_UNBLOCK;
        } else if (featureId == 21) {
            featureName = Features.TYPE_APPROVE;
        } else if (featureId == 9) {
            featureName = Features.GREY_LIST;
        } else if (featureId == 10) {
            featureName = Features.BLACK_LIST;
        } else if (featureId == 82) {
            featureName = Features.TYPE_APPROVED;
        } else if (featureId == 83) {
            featureName = Features.QUALIFIED_AGENTS;
        } else if (featureId == 85) {
            featureName = Features.LOCAL_MANUFACTURER;
        } else if (featureId == 86) {
            featureName = Features.EXCEPTION_LIST;
        }
        else if (featureId == 87) {
            featureName = Features.BLOCK_LIST;
        }
        else if (featureId == 89) {
            featureName = Features.BLOCK_TAC;
        }
        else if (featureId == 101) {
            featureName = Features.STOLEN_BULK;
        }
        else if (featureId == 112) {
            featureName = Features.STOLEN_STATUS;
        }
        AuditTrail auditTrail = new AuditTrail(request.getUserId(),
                request.getUsername(),
                request.getUserTypeId(),
                request.getUserType(),
                featureId,
                featureName,
                Features.SAMPLE_FILE,
                "", "NA", request.getUserType(), request.getPublicIp(), request.getBrowser());
        logger.info("AUDIT : View in audit_trail. " + auditTrail);
        auditTrailRepository.save(auditTrail);
        switch (featureId) {
            case 3:
                fileName = "Consignment.csv";
                break;
            case 4:
                fileName = "Stock.csv";
                break;
            case 5:
                fileName = "StolenAndRecovery.csv";
                break;
            case 7:
                fileName = "Blockunblock.csv";
                break;
            case 82:
                fileName = "Type_Approved.txt";
                break;
            case 83:
                fileName = "Qualified_Agent.txt";
                break;
            case 85:
                fileName = "Local_Manufacturer_IMEI.txt";
                break;
            case 86:
                fileName = "Exception_List.csv";
                break;
            case 87:
                fileName = "Block_IMEI.csv";
                break;
            case 89:
                fileName = "Blocked_TAC_List.csv";
                break;
            case 101:
                fileName="Bulk-Stolen-Sample.csv";
                break;
            case 112:
                fileName="Stolen_Status_Sample_File.csv";
            default:
                break;
        }

        return new FileDetails("", "" , systemConfigurationDb.getValue().replace("$LOCAL_IP",
                propertiesReader.localIp) + fileName);
    }

    public FileDetails getManuals(AllRequest auditRequest) {

        String fileName = null;
        SystemConfigurationDb systemConfigurationDb1 = configurationManagementServiceImpl.findByTag(ConfigTags.manuals_file_name + "_" + auditRequest.getUserTypeId());

        SystemConfigurationDb systemConfigurationDb = configurationManagementServiceImpl.findByTag(ConfigTags.manuals_link + "_" + auditRequest.getUserTypeId());

        AuditTrail auditTrail = new AuditTrail(auditRequest.getUserId(),
                auditRequest.getUsername(),
                auditRequest.getUserTypeId(),
                auditRequest.getUserType(),
                Features.USER_MGMT,
                Features.MANUAL_FILE,
                "", "NA", auditRequest.getUserType(), auditRequest.getPublicIp(), auditRequest.getBrowser());
        logger.info("AUDIT : View in audit_trail. " + auditTrail);
        auditTrailRepository.save(auditTrail);
		
		/*switch (userTypeId) {
		case 1:
			fileName = "";
			break;
		case 4:
			fileName = "CEIR_User Manual (Importer)_v1.1.pdf";
			break;
		case 5:
			fileName = "CEIR_User Manual (Distributor)_v1.0.pdf";
			break;
		case 6:
			fileName = "CEIR_User Manual (Retailer)_v1.0.pdf";
			break;
		case 7:
			fileName = "";
			break;
		case 8:
			fileName = "";
			break;
		case 9:
			fileName = "CEIR_User Manual (Operator)_v1.0.pdf";
			break;
		case 10:
			fileName = "CEIR_User Manual TRC_v1.0.pdf";
			break;
		case 12:
			fileName = "CEIR_User Manual (Manufacturer)_v1.0.pdf";
			break;
		case 13:
			fileName = "";
			break;
		case 14:
			fileName = "CEIR_User Manual (Lawful Agency)_v1.0.pdf";
			break;
		case 17:
			fileName = "CEIRv1.0_User Manual (Importer)_v1.0.pdf";
			break;
		case 18:
			fileName = "CEIRv1.0_User Manual (Importer)_v1.0.pdf";
			break;
		case 19:
			fileName = "CEIRv1.0_User Manual (Importer)_v1.0.pdf";
			break;
		case 20:
			fileName = "CEIRv1.0_User Manual (Importer)_v1.0.pdf";
			break;
		default:
			break;
		}*/

//		fileName = ;
//		return new FileDetails("", "", systemConfigurationDb.getValue().replace("$LOCAL_IP",
//				propertiesReader.localIp) + fileName);
        return new FileDetails(systemConfigurationDb1.getValue(), "", systemConfigurationDb.getValue().replace("$LOCAL_IP",
                propertiesReader.localIp));
    }

    public FileDetails downloadUploadedFile(String fileName, String txnId, String fileType, String tag, AllRequest auditRequest) {

        String fileLink = null;
        SystemConfigurationDb systemConfigurationDb = configurationManagementServiceImpl.findByTag(ConfigTags.upload_file_link);
        String featureName = null;
        if (auditRequest.getFeatureId() == 3) {
            featureName = Features.CONSIGNMENT;
        } else if (auditRequest.getFeatureId() == 4) {
            featureName = Features.STOCK;
        } else if (auditRequest.getFeatureId() == 12) {
            featureName = Features.REGISTER_DEVICE;
        } else if (auditRequest.getFeatureId() == 5) {
            featureName = Features.STOLEN_RECOVERY;
        } else if (auditRequest.getFeatureId() == 7) {
            featureName = Features.BLOCK_UNBLOCK;
        } else if (auditRequest.getFeatureId() == 21) {
            featureName = Features.TYPE_APPROVE;
        } else if (auditRequest.getFeatureId() == 9) {
            featureName = Features.GREY_LIST;
        } else if (auditRequest.getFeatureId() == 10) {
            featureName = Features.BLACK_LIST;
        }
        if (fileType.equalsIgnoreCase("error")) {
            AuditTrail auditTrail = new AuditTrail(auditRequest.getUserId(),
                    auditRequest.getUsername(),
                    auditRequest.getUserTypeId(),
                    auditRequest.getUserType(),
                    auditRequest.getFeatureId(),
                    featureName,
                    Features.ERROR_FILE,
                    "", txnId, auditRequest.getUserType(), auditRequest.getPublicIp(), auditRequest.getBrowser());
            logger.info("AUDIT : View in audit_trail. error " + auditTrail);
            auditTrailRepository.save(auditTrail);
        } else if (fileType.equalsIgnoreCase("actual")) {
            AuditTrail auditTrail = new AuditTrail(auditRequest.getUserId(),
                    auditRequest.getUsername(),
                    auditRequest.getUserTypeId(),
                    auditRequest.getUserType(),
                    auditRequest.getFeatureId(),
                    featureName,
                    Features.UPLOADED_FILE,
                    "", txnId, auditRequest.getUserType(), auditRequest.getPublicIp(), auditRequest.getBrowser());
            logger.info("AUDIT : View in audit_trail. actual file " + auditTrail);
            auditTrailRepository.save(auditTrail);
        }
        if ("actual".equalsIgnoreCase(fileType)) {
            if ("DEFAULT".equalsIgnoreCase(tag)) {
                fileLink = systemConfigurationDb.getValue().replace("$LOCAL_IP",
                        propertiesReader.localIp) + txnId + "/" + fileName;
            } else {
                fileLink = systemConfigurationDb.getValue().replace("$LOCAL_IP",
                        propertiesReader.localIp) + txnId + "/" + tag + "/" + fileName;
            }
        } else if ("error".equalsIgnoreCase(fileType)) {
            systemConfigurationDb = configurationManagementServiceImpl.findByTag(ConfigTags.system_error_file_link);
            fileLink = systemConfigurationDb.getValue().replace("$LOCAL_IP",
                    propertiesReader.localIp) + txnId + "/" + txnId + "_error.csv";
        }

        return new FileDetails("", "", fileLink);
    }
}
