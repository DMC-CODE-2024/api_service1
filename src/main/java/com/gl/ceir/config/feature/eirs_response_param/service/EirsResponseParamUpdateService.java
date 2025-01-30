package com.gl.ceir.config.feature.eirs_response_param.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.gl.ceir.config.feature.addressmanagement.FeaturesEnum;
import com.gl.ceir.config.feature.addressmanagement.audit_trail.AuditTrailService;
import com.gl.ceir.config.feature.common.LocalDateTimeDeserializer;
import com.gl.ceir.config.feature.common.LocalDateTimeSerializer;
import com.gl.ceir.config.feature.operatorseries.model.GenricResponse;
import com.gl.ceir.config.model.app.EirsResponse;
import com.gl.ceir.config.repository.app.EirsResponseParamRepository;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class EirsResponseParamUpdateService {
    private final Logger logger = LogManager.getLogger(this.getClass());

    private EirsResponseParamRepository eirsResponseParamRepository;
    private AuditTrailService auditTrailService;

    public EirsResponseParamUpdateService(EirsResponseParamRepository eirsResponseParamRepository, AuditTrailService auditTrailService) {
        this.eirsResponseParamRepository = eirsResponseParamRepository;
        this.auditTrailService = auditTrailService;
    }

    @Transactional
    public GenricResponse update(EirsResponse eirsResponse) throws JsonProcessingException {
        Optional<EirsResponse> byId = eirsResponseParamRepository.findById(eirsResponse.getId());
        logger.info("response {}", byId);
        if (byId.isPresent()) {
            ObjectMapper objectMapper = new ObjectMapper();
            // Register custom serializers and deserializers
            SimpleModule module = new SimpleModule();
            module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
            module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
            objectMapper.registerModule(module);
            String json = objectMapper.writeValueAsString(byId.get());
            EirsResponse result = objectMapper.readValue(json, EirsResponse.class);

            // Update fields except for the tag
            result.setDescription(eirsResponse.getDescription());
            result.setType(eirsResponse.getType());
            result.setValue(eirsResponse.getValue());
            result.setActive(eirsResponse.getActive());
            result.setFeatureName(eirsResponse.getFeatureName());
            result.setRemarks(eirsResponse.getRemarks());
            result.setLanguage(eirsResponse.getLanguage());
            result.setSubject(eirsResponse.getSubject());

            logger.info("payload to save {}", result);
            updateEirsResponseParam(result);

            String requestType = "EIRS_RESPONSE_PARAM_UPDATE";
            logger.info("requestType [" + requestType + "]");
            auditTrailService.auditTrailOperation(eirsResponse.getAuditTrailModel(), FeaturesEnum.getFeatureName(requestType), FeaturesEnum.getSubFeatureName(requestType));

            return new GenricResponse("Record successfully updated", 1);
        }
        return new GenricResponse("No record found for id " + eirsResponse.getId(), 0);
    }

    private void updateEirsResponseParam(EirsResponse eirsResponse) {
        try {
            eirsResponseParamRepository.save(eirsResponse);
            logger.info("eirs_response_param record has been updated for id {}", eirsResponse.getId());
        } catch (Exception e) {
            logger.info("exception occurred while updating data {}", e.getMessage());
        }
    }
}