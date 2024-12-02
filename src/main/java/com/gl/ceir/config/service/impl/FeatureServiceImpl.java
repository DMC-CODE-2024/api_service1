package com.gl.ceir.config.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gl.ceir.config.feign.UserFeignClient;
import com.gl.ceir.config.genericresponse.GenricResponse_Class;

@Service
public class FeatureServiceImpl {

	private static final Logger logger = LogManager.getLogger(FeatureServiceImpl.class);
	@Autowired
	UserFeignClient userFeignClient;

	public  GenricResponse_Class responseNameById(int id) {
		try {
			GenricResponse_Class response = userFeignClient.nameById(id);
			return response;
		}
		catch(Exception e) {
			logger.info("");
		}
		return null;

	}
}
