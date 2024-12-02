package com.gl.ceir.config.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gl.ceir.config.exceptions.ResourceNotFoundException;
import com.gl.ceir.config.exceptions.ResourceServicesException;
import com.gl.ceir.config.model.app.Rules;
import com.gl.ceir.config.repository.app.RulesRepository;
import com.gl.ceir.config.service.RulesService;


@Service
public class RulesServiceImpl implements RulesService {

	private static final Logger logger = LogManager.getLogger(RulesServiceImpl.class);

	@Autowired
	private RulesRepository rulesRepository;

	@Override
	public List<Rules> getAll() {
		try {
			return rulesRepository.findAll();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
		}

	}

	@Override
	public Rules save(Rules rules) {
		try {
			return rulesRepository.save(rules);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
		}

	}

	@Override
	public Rules get(Long id) {

		try {
			Rules rules = rulesRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("Rules", "id", id));
			return rules;
		} catch (ResourceNotFoundException e) {
			throw e;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
		}
	}

	@Override
	public Rules update(Rules rules) {
		try {
			return rulesRepository.save(rules);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
		}

	}

	@Override
	public void delete(Long rules) {
		try {
			rulesRepository.deleteById(rules);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new ResourceServicesException(this.getClass().getName(), e.getMessage());
		}

	}

	@Override
	public Rules get(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
