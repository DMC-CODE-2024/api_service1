package com.gl.ceir.config.repository.aud;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import com.gl.ceir.config.model.aud.AuditTrail;

public interface AuditTrailRepository extends JpaRepository<AuditTrail, Long>, JpaSpecificationExecutor<AuditTrail> {

	public AuditTrail getById(long id);
	
	@Query("SELECT DISTINCT s.featureName FROM AuditTrail s")
	public List<String> findDistinctFeatureName();

	@Query("SELECT DISTINCT s.userType FROM AuditTrail s")
	public List<String> findDistinctUserType();
}
