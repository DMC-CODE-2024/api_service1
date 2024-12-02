package com.gl.ceir.config.audit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gl.ceir.config.model.app.FilterRequest;
import com.gl.ceir.config.model.aud.AuditTrail;

public class AuditTrailMethods {
	private static final Logger logger = LogManager.getLogger(AuditTrailMethods.class);
@Autowired
AuditTrail auditTrail;

	public AuditTrail saveAuditTrail_filterRequest(FilterRequest filterRequest,String featureName,String subFeatureName) {
		
		auditTrail.setUserId(Long.valueOf(filterRequest.getUserId()));
		auditTrail.setUserName(filterRequest.getUserName());
		auditTrail.setUserTypeId(Long.valueOf(filterRequest.getUserTypeId())); 
		auditTrail.setUserType(filterRequest.getUserType());
		auditTrail.setFeatureId(Long.valueOf(filterRequest.getFeatureId()));
		auditTrail.setFeatureName(featureName);
		auditTrail.setSubFeature(subFeatureName);
		auditTrail.setTxnId("NA");
		auditTrail.setRoleType(filterRequest.getRoleType());
		logger.info("auditTrail request::::::::"+auditTrail);
		return auditTrail;
	}
	
	
	
	
}
