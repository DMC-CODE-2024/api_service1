package com.gl.ceir.config.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gl.ceir.config.exceptions.ResourceServicesException;
import com.gl.ceir.config.model.app.GenricResponse;
import com.gl.ceir.config.model.app.StaticRuleEngineMapping;
import com.gl.ceir.config.transaction.StaticRuleEngineTransaction;

@Service
public class StaticRuleServiceImpl {

	private static final Logger logger = LogManager.getLogger(RuleEngineMappingServiceImpl.class);

	@Autowired
	StaticRuleEngineTransaction staticRuleEngineTransaction;
	public GenricResponse save(StaticRuleEngineMapping staticRuleEngineMapping){
		try {

			int errorCode = staticRuleEngineTransaction.save(staticRuleEngineMapping) ?  0 : 1; 
			return new GenricResponse(errorCode);
					
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
			throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
		}
	}


}
