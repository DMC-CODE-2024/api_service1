package com.gl.ceir.config.systemValidation.impl;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gl.ceir.config.controller.ConfigurationController;
import com.gl.ceir.config.model.app.FieldValidation;
import com.gl.ceir.config.model.app.GenricResponse;
import com.gl.ceir.config.repository.app.FieldValidationRepository;

@Component
public class SystemValidation {
	
	private static final Logger logger = LogManager.getLogger(ConfigurationController.class);
	
	@Autowired
	FieldValidationRepository fieldValidationRepository;
	
	public  GenricResponse validateFieldsByObject(Object object,String userType,Integer featureId){
		try {
			logger.info("feature_Id :"  +featureId);
			GenricResponse response = new GenricResponse(featureId);
			String fieldTag="";
			String fieldValue="";
			Class<? extends Object> c = object.getClass();
			  Field[] fields = c.getDeclaredFields();
			  for (Field field : fields) {
			      String name = field.getName();
			      logger.info("Field Name: "+name);
			      field.setAccessible(true);
			      if("tag".equals(name)) {
			    	  fieldTag=field.get(object).toString();
			    	  logger.info("fieldTag : "+fieldTag);
			      }
			      if("value".equals(name)) {
			    	  fieldValue=field.get(object).toString();
			    	  logger.info("fieldValue : "+fieldValue);
			    	}
			  }
			      try {
			    	 FieldValidation fieldValidation = fieldValidationRepository.findByFieldNameAndUsertypeAndFeatureId(fieldTag,userType,featureId);
			    	 logger.info("fieldValidation Request :" +fieldValidation);
			    	 if(Objects.nonNull(fieldValidation) && Objects.nonNull(fieldValidation.getFieldName())) {
			    		  logger.info("Field Validation: "+fieldValidation+" nonNull:"+Objects.nonNull(fieldValidation.getFieldName()));
			    		  Pattern regex = Pattern.compile(fieldValidation.getRegex());
			    		  logger.info("Regex: "+regex);
					      String value = fieldValue;
			    		  logger.info("Value: "+value);
			    		  if(("Y".equalsIgnoreCase(fieldValidation.getMandatory()) && value.isEmpty())) {
			    			  	logger.info("Validation Result for "+fieldTag+": Validation Failed with regex 1" );
				    		  	response.setErrorCode(201);
				    		  	logger.info("Validation Message for "+fieldTag+" is :"+fieldValidation.getMessage());
				    		  	response.setMessage(fieldValidation.getMessage());
				    		  	return response;
				    		  	//validationOutput.setIsValid(Boolean.FALSE);
				    		  	//validationOutput.getInvalidFields().put(fieldTag, "Validation Failed with regex 1 ["+regex+"]");
				    	  }else if("N".equalsIgnoreCase(fieldValidation.getMandatory()) && value.isEmpty()) {
			    			  logger.info("Everything is fine and isValid 1 : Mandatory ");
			    		  }else {
			    			  if(!regex.matcher(value).matches()) {
			    				  logger.info("Validation Result for "+fieldTag+": Validation Failed with regex 2");
			    				  response.setErrorCode(201);
			    				  logger.info("Validation Message for "+fieldTag+" is :"+fieldValidation.getMessage());
					    		  	response.setMessage(fieldValidation.getMessage());
			    				  return response;
			    				  //validationOutput.setIsValid(Boolean.FALSE);
			    				  //validationOutput.getInvalidFields().put(fieldTag, "Validation Failed with regex 2 ["+regex+"]");
			    				}else 
			    				  logger.info("Everything is fine and isValid 2: Regex Matched");
			    		  }
			    	  }else {
			    		  logger.info("Everything is fine and isValid : Mandatory & Regex Matched");
			    		  response.setErrorCode(203);
			    		  return response;
			    		  //validationOutput.setIsValid(Boolean.FALSE);
			    		 // validationOutput.getInvalidFields().put(name, "Could not found in table [ field_validation ]");
			    	  }
			      } catch (IllegalArgumentException e) {
			    	  logger.error(e.getMessage(), e);
			    	  logger.info("IllegalArgumentException while validating ["+object+"] Exception = ["+e+"]");
			        //e.printStackTrace();
			      } 
			  logger.info("Validation done for object ["+object+"] isValid");
			  return response;
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			logger.info("Exception in validateFieldsByObject"+e);
			//e.printStackTrace();
		}
		return null; 
	}
}
