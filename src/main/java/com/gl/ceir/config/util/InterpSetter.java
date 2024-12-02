package com.gl.ceir.config.util;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gl.ceir.config.model.app.StateMgmtDb;
import com.gl.ceir.config.model.app.SystemConfigListDb;
import com.gl.ceir.config.service.impl.ConfigurationManagementServiceImpl;
import com.gl.ceir.config.service.impl.StateMgmtServiceImpl;

@Component
public final class InterpSetter {

	@Autowired
	ConfigurationManagementServiceImpl configurationManagementServiceImpl;

	@Autowired
	StateMgmtServiceImpl stateMgmtServiceImpl;

	public String setConfigInterp(String tag, Integer value) {
		try {
			if(Objects.isNull(value)) {
				return "";	
			}

			List<SystemConfigListDb> systemConfigListDbs = configurationManagementServiceImpl.getSystemConfigListByTag(tag);
			return systemConfigListDbs.stream().filter(o -> Integer.valueOf(o.getValue()) == value).findAny().get().getInterpretation();

		}catch (Exception e) {
			return "";
		}
	}
	
	
	public String setTagId(String tag, Integer value) {
		try {
			if(Objects.isNull(value)) {
				return "";	
			}

			List<SystemConfigListDb> systemConfigListDbs = configurationManagementServiceImpl.getSystemConfigListByTag(tag);
			return systemConfigListDbs.stream().filter(o -> Integer.valueOf(o.getValue()) == value).findAny().get().getTagId();

		}catch (Exception e) {
			return "";
		}
	}

	public String setConfigInterp(String tag, int value, int start, int end) {
		try {

			List<SystemConfigListDb> systemConfigListDbs = configurationManagementServiceImpl.getSystemConfigListByTag(tag);
			return systemConfigListDbs.stream()
					.filter(o -> Integer.valueOf(o.getValue()) == value)
					.findAny()
					.get()
					.getInterpretation()
					.substring(start, end);

		}catch (IndexOutOfBoundsException e) {
			return "";
		}catch (Exception e) {
			// TODO: handle exception
			return "";
		}
	}

	public String setStateInterp(int featureId, int userTypeId, int value) {
		try {

			List<StateMgmtDb> stateInterpList = stateMgmtServiceImpl.getByFeatureIdAndUserTypeId(featureId, userTypeId);
			return stateInterpList.stream().filter(o -> o.getState() == value).findAny().get().getInterpretation();

		}catch (Exception e) {
			// TODO: handle exception
			return "";
		}
	}
}