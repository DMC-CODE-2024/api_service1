package com.gl.ceir.config.feature.eirs_response_param.service;

import com.gl.ceir.config.feature.addressmanagement.FeaturesEnum;
import com.gl.ceir.config.feature.addressmanagement.audit_trail.AuditTrailService;
import com.gl.ceir.config.feature.eirs_response_param.export.EirsResponseParamExport;
import com.gl.ceir.config.model.app.EirsResponse;
import com.gl.ceir.config.model.app.FileDetails;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class EirsResponseParamExportService {
    private final Logger logger = LogManager.getLogger(this.getClass());
    @PersistenceContext
    private EntityManager em;
    private EirsResponseParamExport eirsResponseParamExport;
    private AuditTrailService auditTrailService;

    public EirsResponseParamExportService(EirsResponseParamExport eirsResponseParamExport, AuditTrailService auditTrailService) {
        this.eirsResponseParamExport = eirsResponseParamExport;
        this.auditTrailService = auditTrailService;
    }

    public MappingJacksonValue export(EirsResponse eirsResponse) {
        if (eirsResponse == null) {
            logger.error("EirsResponseParamEntity is null");
            throw new IllegalArgumentException("EirsResponseParamEntity cannot be null");
        }

        String requestType = "EIRS_RESPONSE_PARAM_EXPORT";
        FileDetails export = eirsResponseParamExport.export(eirsResponse, FeaturesEnum.getFeatureName(requestType).replace(" ", "_"));
        logger.info("requestType [" + requestType + "]");
        auditTrailService.auditTrailOperation(eirsResponse.getAuditTrailModel(), FeaturesEnum.getFeatureName(requestType), FeaturesEnum.getSubFeatureName(requestType));
        return new MappingJacksonValue(export);
    }
}