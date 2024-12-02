package com.gl.ceir.config.repository.app;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.gl.ceir.config.model.app.CheckIMEIResponseParam;
import com.gl.ceir.config.model.app.SystemConfigurationDb;
import com.gl.ceir.config.model.aud.AuditTrail;

public interface CheckIMEIResponse extends JpaRepository<CheckIMEIResponseParam, Long>, JpaSpecificationExecutor<CheckIMEIResponseParam>{

	@Query("SELECT DISTINCT s.featureName FROM CheckIMEIResponseParam s")
	public List<String> findDistinctFeatureName();
	
	@Query("SELECT DISTINCT s.type FROM CheckIMEIResponseParam s")
	public List<String> findDistinctType();
	
	public CheckIMEIResponseParam getById(long id);
}
