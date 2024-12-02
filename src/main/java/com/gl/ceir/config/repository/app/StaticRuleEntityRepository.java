package com.gl.ceir.config.repository.app;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gl.ceir.config.model.app.StaticRuleEngineMapping;

@Repository
public interface StaticRuleEntityRepository extends JpaRepository<StaticRuleEngineMapping, Long>{

	public StaticRuleEngineMapping getById(long id);

	
}
