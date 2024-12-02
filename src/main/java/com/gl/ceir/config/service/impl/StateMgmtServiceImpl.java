package com.gl.ceir.config.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.gl.ceir.config.configuration.PropertiesReader;
import com.gl.ceir.config.exceptions.ResourceServicesException;
import com.gl.ceir.config.model.app.StateMgmtDb;
import com.gl.ceir.config.model.app.StatesInterpretationDb;
import com.gl.ceir.config.repository.app.StateMgmtRepository;
import com.gl.ceir.config.repository.app.StatesInterpretaionRepository;
import com.gl.ceir.config.util.Utility;

@Service
public class StateMgmtServiceImpl {

	private static final Logger logger = LogManager.getLogger(StateMgmtServiceImpl.class);

	@Autowired
	private StateMgmtRepository stateMgmtRepository;

	@Autowired
	private StatesInterpretaionRepository statesInterpretaionRepository; 

	

	@Autowired
	PropertiesReader propertiesReader;

	@Autowired
	Utility utility;

	
	
	public StateMgmtDb saveStateMgmt(StateMgmtDb stateMgmtDb){
		try {
			return stateMgmtRepository.save(stateMgmtDb);
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
			throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
		}
	}

	/*
	 * 
	 * @future_scope : must use a join between state_mgmt_db and states_interpretation_db
	 */
	public List<StateMgmtDb> getByFeatureIdAndUserTypeId(Integer featureId, Integer userTypeId) {
		try {
			List<StateMgmtDb> stateMgmtDbsResult = new ArrayList<>();

			logger.info("Going to get states by featureId and usertypeId ");

			List<StateMgmtDb> stateMgmtDbs = stateMgmtRepository.getByFeatureIdAndUserTypeId(featureId, userTypeId);

			List<StatesInterpretationDb> statesInterpretationDbs = statesInterpretaionRepository.findByFeatureId(featureId);
			logger.debug(statesInterpretationDbs);

			for(StatesInterpretationDb statesInterpretationDb : statesInterpretationDbs) {

				for(StateMgmtDb stateMgmtDb : stateMgmtDbs) {
					if(stateMgmtDb.getState().equals(statesInterpretationDb.getState())) {
						stateMgmtDb.setInterpretation(statesInterpretationDb.getInterpretation());
						stateMgmtDbsResult.add(stateMgmtDb);
					}
				}
			}

			return stateMgmtDbsResult;

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
		}
	}

	

}
