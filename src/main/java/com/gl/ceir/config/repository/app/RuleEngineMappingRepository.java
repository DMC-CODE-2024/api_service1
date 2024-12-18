package com.gl.ceir.config.repository.app;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.gl.ceir.config.model.app.RuleEngineMapping;

@Repository
public interface RuleEngineMappingRepository extends JpaRepository<RuleEngineMapping, Long>, 
JpaSpecificationExecutor<RuleEngineMapping> {

	public RuleEngineMapping getById(long id);

	public RuleEngineMapping getByName(String name);

	public void deleteById(long id);
	public RuleEngineMapping findByNameAndFeatureAndUserType(String name,String feature,String userType);
	 public List<RuleEngineMapping> findByNameAndUserTypeAndFeatureAndRuleOrderGreaterThan(String name,String userType,String feature,Integer ruleOrder);
	 public List<RuleEngineMapping> findByNameAndUserTypeAndFeatureAndRuleOrderGreaterThanEqual(String name,String userType,String feature,Integer ruleOrder);
	 public List<RuleEngineMapping> findByNameAndUserTypeAndFeatureAndRuleOrderLessThan(String name,String userType,String feature,Integer ruleOrder);
	public RuleEngineMapping findByNameAndFeatureAndUserTypeAndRuleOrder(String name,String feature,String userType,Integer ruleOrder);
	public List<RuleEngineMapping> findByNameAndUserTypeAndFeatureAndRuleOrderBetween(String name,String userType,String feature,Integer existingRuleOrder,Integer ruleOrder);
	 public List<RuleEngineMapping> findByNameAndUserTypeAndFeature(String name,String userType,String feature);

	// public List<RuleEngineMapping> findDistinctFeatureByName(String name);
	 public List<RuleEngineMapping> findByFeature(String  featureName);
	 public List<RuleEngineMapping> findByFeatureAndName(String  featureName,String name);
	 @Query(value = "SELECT * FROM feature_rule r where r.name =?1", nativeQuery = true)
	 public List<RuleEngineMapping> findDistinctByName(String name);
}
