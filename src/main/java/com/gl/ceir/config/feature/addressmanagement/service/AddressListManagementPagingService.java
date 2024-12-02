package com.gl.ceir.config.feature.addressmanagement.service;

import com.gl.ceir.config.feature.addressmanagement.FeaturesEnum;
import com.gl.ceir.config.feature.addressmanagement.audit_trail.AuditTrailService;
import com.gl.ceir.config.feature.addressmanagement.paging.AddressListManagementPaging;
import com.gl.ceir.config.model.app.AddressEntity;
import com.gl.ceir.config.repository.app.AddressListMgmtRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddressListManagementPagingService {
    private final Logger logger = LogManager.getLogger(this.getClass());
    @PersistenceContext
    private EntityManager em;
    private AddressListManagementPaging addressListManagementPaging;

    private AuditTrailService auditTrailService;
    private AddressListMgmtRepository addressListMgmtRepository;

    public AddressListManagementPagingService(AddressListManagementPaging addressListManagementPaging, AuditTrailService auditTrailService, AddressListMgmtRepository addressListMgmtRepository) {
        this.addressListManagementPaging = addressListManagementPaging;
        this.auditTrailService = auditTrailService;
        this.addressListMgmtRepository = addressListMgmtRepository;
    }

    public MappingJacksonValue paging(AddressEntity addressEntity, int pageNo, int pageSize) {
        Page<AddressEntity> page = addressListManagementPaging.findAll(addressEntity, pageNo, pageSize);
        String requestType = "ADDRESS_MGMT_VIEWALL";
        logger.info("requestType [" + requestType + "]");
        auditTrailService.auditTrailOperation(addressEntity.getAuditTrailModel(), FeaturesEnum.getFeatureName(requestType), FeaturesEnum.getSubFeatureName(requestType));
        return new MappingJacksonValue(page);
    }


    public MappingJacksonValue find(AddressEntity addressEntity) {
        Optional<AddressEntity> optional = addressListMgmtRepository.findById(addressEntity.getId());
        if (optional.isPresent()) {
            return new MappingJacksonValue(optional.get());
        }
        return new MappingJacksonValue(null);
    }
}
