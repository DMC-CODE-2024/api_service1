package com.gl.ceir.config.feature.eirs_response_param.service;

import com.gl.ceir.config.feature.addressmanagement.FeaturesEnum;
import com.gl.ceir.config.feature.addressmanagement.audit_trail.AuditTrailService;
import com.gl.ceir.config.feature.eirs_response_param.paging.EirsResponseParamPaging;
import com.gl.ceir.config.model.app.EirsResponse;
import com.gl.ceir.config.repository.app.EirsResponseParamRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.Optional;

@Service
public class EirsResponseParamPagingService {
    private final Logger logger = LogManager.getLogger(this.getClass());
    @PersistenceContext
    private EntityManager em;
    private EirsResponseParamPaging eirsResponseParamPaging;
    private AuditTrailService auditTrailService;
    private EirsResponseParamRepository eirsResponseParamRepository;

    public EirsResponseParamPagingService(EirsResponseParamPaging eirsResponseParamPaging, AuditTrailService auditTrailService, EirsResponseParamRepository eirsResponseParamRepository) {
        this.eirsResponseParamPaging = eirsResponseParamPaging;
        this.auditTrailService = auditTrailService;
        this.eirsResponseParamRepository = eirsResponseParamRepository;
    }

    public MappingJacksonValue paging(EirsResponse eirsResponse, int pageNo, int pageSize) {
        Page<EirsResponse> page = eirsResponseParamPaging.findAll(eirsResponse, pageNo, pageSize);
        String requestType = "EIRS_RESPONSE_PARAM_VIEWALL";
        logger.info("requestType [" + requestType + "]");
        auditTrailService.auditTrailOperation(eirsResponse.getAuditTrailModel(), FeaturesEnum.getFeatureName(requestType), FeaturesEnum.getSubFeatureName(requestType));
        return new MappingJacksonValue(page);
    }

    public MappingJacksonValue find(EirsResponse eirsResponse) {
        Optional<EirsResponse> optional = eirsResponseParamRepository.findById(eirsResponse.getId());
        if (optional.isPresent()) {
            return new MappingJacksonValue(optional.get());
        }
        return new MappingJacksonValue(null);
    }
}