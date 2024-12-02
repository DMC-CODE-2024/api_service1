package com.gl.ceir.config.service.impl;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gl.ceir.config.configuration.PropertiesReader;
import com.gl.ceir.config.model.app.StakeholderFeature;
import com.gl.ceir.config.repository.app.FeatureRepo;

@Service
public class StakeholderfeatureServiceImpl {

	private static final Logger logger = LogManager.getLogger(StakeholderfeatureServiceImpl.class);
	
	@Autowired
	PropertiesReader propertiesReader;

	@Autowired
	FeatureRepo featureRepo;
	
	public String getFeatureNameById(Long id) {
		String featureName = "";
		
		Optional<StakeholderFeature> stakeholderFeatureOptional = featureRepo.findById(id);
		logger.debug(stakeholderFeatureOptional.get());
		
		if(stakeholderFeatureOptional.isPresent()) {
			featureName = stakeholderFeatureOptional.get().getName();
			logger.debug("Feature is [" + featureName + "]");
		}else {
			logger.warn("Feature name not found for feature with id[" + id + "]");
		}
		
		return featureName;
	}

}