package com.gl.ceir.config.service.impl;

import java.util.Map;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gl.ceir.config.configuration.PropertiesReader;
import com.gl.ceir.config.exceptions.ResourceServicesException;
import com.gl.ceir.config.model.app.AlertDb;
import com.gl.ceir.config.model.app.GenricResponse;
import com.gl.ceir.config.model.app.RunningAlertDb;
import com.gl.ceir.config.repository.app.AlertDbRepository;
import com.gl.ceir.config.repository.app.RunningAlertDbRepository;
import com.gl.ceir.config.util.InterpSetter;

@Service
public class AlertServiceImpl {

	private static final Logger logger = LogManager.getLogger(AlertServiceImpl.class);

	@Autowired
	PropertiesReader propertiesReader;

	@Autowired
	ConfigurationManagementServiceImpl configurationManagementServiceImpl; 



	@Autowired
	InterpSetter interpSetter;
	
	@Autowired
	AlertDbRepository alertDbRepository;
	
	@Autowired
	RunningAlertDbRepository runningAlertDbRepository;

	public GenricResponse raiseAnAlert(String alertId, int userId) {

		try {
			AlertDb alertDb = alertDbRepository.getByAlertId(alertId);
			runningAlertDbRepository.save(new RunningAlertDb(userId, alertId, alertDb.getDescription(), 0));
			return new GenricResponse(0);
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
		}
	}
	
	public GenricResponse raiseAnAlert(String alertId, int userId, Map<String, String> bodyPlaceHolderMap) {

		try {
			AlertDb alertDb = alertDbRepository.getByAlertId(alertId);
			
			// Replace Placeholders from bodyPlaceHolderMap.
			if(Objects.nonNull(bodyPlaceHolderMap)) {
				for (Map.Entry<String, String> entry : bodyPlaceHolderMap.entrySet()) {
					logger.info("Placeholder key : " + entry.getKey() + " value : " + entry.getValue());
					alertDb.setDescription(alertDb.getDescription().replaceAll(entry.getKey(), entry.getValue()));
				}
			}
			
			runningAlertDbRepository.save(new RunningAlertDb(userId, alertId, alertDb.getDescription(), 0));
			return new GenricResponse(0);
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			return new GenricResponse(1);
		}
	}

}