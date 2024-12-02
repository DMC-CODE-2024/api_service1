package com.gl.ceir.config.service.impl;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Objects;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gl.ceir.config.model.app.FieldValidation;
import com.gl.ceir.config.model.app.ValidationOutput;
import com.gl.ceir.config.repository.app.FieldValidationRepository;
import com.gl.ceir.config.service.FieldValidationService;

@Service
public class FieldValidationServiceImpl implements FieldValidationService {
	
	private static final Logger logger = LogManager.getLogger(FieldValidationServiceImpl.class);
	
	@Autowired
	FieldValidationRepository fieldValidationRepository;
	
	@Override
	public ValidationOutput validateFieldsByObject(Object object) {
		
		logger.info("Validation process started for object ["+object+"]");
		
		ValidationOutput validationOutput = new ValidationOutput(Boolean.TRUE,new HashMap<String, String>());
		
		Class<? extends Object> c = object.getClass();
		  Field[] fields = c.getDeclaredFields();
		  for (Field field : fields) {
		      String name = field.getName();  
		      field.setAccessible(true);          
		      try {
		    	  FieldValidation fieldValidation = fieldValidationRepository.findByFieldName(name);
		    	  if(Objects.nonNull(fieldValidation)) {
		    		  Pattern regex = Pattern.compile(fieldValidation.getRegex());	    		  
		    		  String value = field.get(object).toString();
		    		  if(!regex.matcher(value).matches()) {
		    			  validationOutput.setIsValid(Boolean.FALSE);
			    		  validationOutput.getInvalidFields().put(name, "Validation Failed with regex ["+regex+"]");
		    		  }
		    	  }else {
		    		  continue;
		    		  //validationOutput.setIsValid(Boolean.FALSE);
		    		 // validationOutput.getInvalidFields().put(name, "Could not found in table [ field_validation ]");
		    	  }
		      } catch (IllegalArgumentException e) {
		    	  logger.error("IllegalArgumentException while validating ["+object+"] Exception = ["+e+"]");
		        e.printStackTrace();
		      } catch (IllegalAccessException e) {
		    	  logger.error("IllegalAccessException while validating ["+object+"] Exception = ["+e+"]");
				e.printStackTrace();
			}
		  }
		 
		  logger.info("Validation done for object ["+object+"] isValid = "+validationOutput.getIsValid());
		  
		return validationOutput;
	}
}
