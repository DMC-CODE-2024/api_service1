package com.gl.ceir.config.service;

import com.gl.ceir.config.model.app.ValidationOutput;

public interface FieldValidationService {
	
	public ValidationOutput validateFieldsByObject(Object object);
	
}
