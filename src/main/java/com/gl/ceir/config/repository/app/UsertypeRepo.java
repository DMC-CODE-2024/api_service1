package com.gl.ceir.config.repository.app;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.gl.ceir.config.model.app.FeatureMappingDb;


@Repository
public interface UsertypeRepo extends JpaRepository<FeatureMappingDb, Long>, JpaSpecificationExecutor<FeatureMappingDb>{
	public List<FeatureMappingDb> findDistinctByFeature(String  featureName); 
}
