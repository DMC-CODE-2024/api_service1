package com.gl.ceir.config.repository.app;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.gl.ceir.config.model.app.FieldValidation;

@Repository
public interface FieldValidationRepository extends JpaRepository<FieldValidation,Integer>,  JpaSpecificationExecutor<FieldValidation>{
	
	public FieldValidation findByFieldName(String fieldName);
	public FieldValidation findByFieldNameAndUsertypeAndFeatureId(String fieldName,String usertype,Integer featureId);

}
