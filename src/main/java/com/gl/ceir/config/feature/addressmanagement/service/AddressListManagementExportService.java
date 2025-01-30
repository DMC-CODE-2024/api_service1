package com.gl.ceir.config.feature.addressmanagement.service;

import com.gl.ceir.config.externalproperties.FeatureNameMap;
import com.gl.ceir.config.feature.addressmanagement.FeaturesEnum;
import com.gl.ceir.config.feature.addressmanagement.audit_trail.AuditTrailService;
import com.gl.ceir.config.feature.addressmanagement.export.AddressListManagementExport;
import com.gl.ceir.config.model.app.AddressEntity;
import com.gl.ceir.config.model.app.FileDetails;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Service;


@Service
public class AddressListManagementExportService {
    private final Logger logger = LogManager.getLogger(this.getClass());
    @PersistenceContext
    private EntityManager em;
    private AddressListManagementExport addressListManagementExport;

    private AuditTrailService auditTrailService;

    public AddressListManagementExportService(AddressListManagementExport addressListManagementExport, AuditTrailService auditTrailService) {
        this.addressListManagementExport = addressListManagementExport;
        this.auditTrailService = auditTrailService;
    }

    @Autowired
    private FeatureNameMap featureNameMap;

    public MappingJacksonValue export(AddressEntity addressEntity) {

        String requestType = "ADDRESS_MGMT";
        FileDetails export = addressListManagementExport.export(addressEntity, featureNameMap.get(requestType).replace(" ", "_"));
        logger.info("requestType [" + requestType + "]");
        auditTrailService.auditTrailOperation(addressEntity.getAuditTrailModel(), featureNameMap.get(requestType), featureNameMap.get("EXPORT"));
        return new MappingJacksonValue(export);
    }
}
