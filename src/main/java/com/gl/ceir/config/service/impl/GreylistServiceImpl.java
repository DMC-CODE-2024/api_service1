package com.gl.ceir.config.service.impl;

import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gl.ceir.config.configuration.PropertiesReader;
import com.gl.ceir.config.model.app.GreylistDb;
import com.gl.ceir.config.model.constants.Tags;
import com.gl.ceir.config.util.InterpSetter;

@Service
public class GreylistServiceImpl {

	private static final Logger logger = LogManager.getLogger(GreylistServiceImpl.class);

	@Autowired
	PropertiesReader propertiesReader;

	@Autowired
	InterpSetter interpSetter;

	

	public void setInterp(GreylistDb greylistDb) {
		try {
			
			if(Objects.nonNull(greylistDb.getComplainType()))
				greylistDb.setComplainTypeInterp(interpSetter.setConfigInterp(Tags.COMPLAINT_TYPE, Integer.parseInt(greylistDb.getComplainType())));
		}catch (NumberFormatException e) {
			greylistDb.setComplainTypeInterp("NA");
		}
	}
}
