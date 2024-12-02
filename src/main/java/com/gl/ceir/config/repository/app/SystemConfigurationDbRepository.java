package com.gl.ceir.config.repository.app;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.gl.ceir.config.model.app.SystemConfigurationDb;
import org.springframework.data.jpa.repository.Query;

public interface SystemConfigurationDbRepository extends JpaRepository<SystemConfigurationDb, Long>, JpaSpecificationExecutor<SystemConfigurationDb> {

	public SystemConfigurationDb findByTag(String tag);
	public SystemConfigurationDb getByTag(String tag);

	public SystemConfigurationDb getById(Long id);

	public List<SystemConfigurationDb> getByType(Integer type);
	@Query("SELECT DISTINCT s.featureName FROM SystemConfigurationDb s")
	public List<String> findDistinctFeatureName();
}
