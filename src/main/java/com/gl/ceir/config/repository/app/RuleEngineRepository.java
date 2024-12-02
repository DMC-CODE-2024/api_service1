package com.gl.ceir.config.repository.app;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.gl.ceir.config.model.app.RuleEngine;

@Repository
public interface RuleEngineRepository extends JpaRepository<RuleEngine, Long>, 
JpaSpecificationExecutor<RuleEngine> {

	public RuleEngine getById(long id);
	
	public RuleEngine getByName(String name);
	
	public void deleteById(long id);
}
