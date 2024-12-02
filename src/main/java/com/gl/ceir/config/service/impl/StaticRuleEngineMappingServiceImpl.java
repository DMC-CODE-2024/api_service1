package com.gl.ceir.config.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gl.ceir.config.exceptions.ResourceServicesException;
import com.gl.ceir.config.model.app.GenricResponse;
import com.gl.ceir.config.model.app.RuleEngineMapping;
import com.gl.ceir.config.model.app.StaticRuleEngineMapping;
import com.gl.ceir.config.repository.app.StaticRuleEntityRepository;

@Service
public class StaticRuleEngineMappingServiceImpl {

	private static final Logger logger = LogManager.getLogger(StaticRuleEngineMappingServiceImpl.class);

	@Autowired
	private StaticRuleEntityRepository staticRuleEntityRepository;

	public GenricResponse save(StaticRuleEngineMapping staticRuleEngineMapping) {

		try {

			staticRuleEntityRepository.save(staticRuleEngineMapping);

			return new GenricResponse(0);
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
			throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
		}
	}

	public StaticRuleEngineMapping findById(long id) {
		try {
			return staticRuleEntityRepository.getById(id);
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
			throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
		}
	}

	public GenricResponse updateById(StaticRuleEngineMapping staticRuleEngineMapping) {
		try {
			StaticRuleEngineMapping staticRuleEngineMappingOld = staticRuleEntityRepository
					.getById(staticRuleEngineMapping.getId());
			logger.info("ruleEngineMappingOld : " + staticRuleEngineMappingOld);
            staticRuleEngineMapping.setFeature(staticRuleEngineMappingOld.getFeature());
            staticRuleEngineMapping.setName(staticRuleEngineMappingOld.getName());
			

			staticRuleEntityRepository.save(staticRuleEngineMapping);

			return new GenricResponse(0);
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
			throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
		}
	}

	public GenricResponse deleteById(Long id) {
		try {
			staticRuleEntityRepository.deleteById(id);

			return new GenricResponse(0);
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
			throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
		}
	}

}
