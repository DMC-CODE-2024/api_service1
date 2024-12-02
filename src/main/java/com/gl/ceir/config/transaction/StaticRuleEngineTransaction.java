package com.gl.ceir.config.transaction;

import jakarta.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gl.ceir.config.model.app.StaticRuleEngineMapping;
import com.gl.ceir.config.repository.app.StaticRuleEntityRepository;
import com.gl.ceir.config.service.impl.RuleEngineMappingServiceImpl;

@Component
@Transactional(rollbackOn = Exception.class)
public class StaticRuleEngineTransaction {
	
	@Autowired
	StaticRuleEntityRepository staticRuleEntityRepository;

	private static final Logger logger = LogManager.getLogger(StaticRuleEngineTransaction.class);
	
	public Boolean save(StaticRuleEngineMapping staticRuleEngineMapping) {
		Boolean status = Boolean.FALSE;
		
		staticRuleEntityRepository.save(staticRuleEngineMapping);
		
		logger.info("StaticRuleEngineMapping db has been updated." + staticRuleEngineMapping );
		
		status = Boolean.TRUE;
		return status;
	}
}
