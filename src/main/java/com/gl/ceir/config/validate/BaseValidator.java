package com.gl.ceir.config.validate;

import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.gl.ceir.config.exceptions.RequestInvalidException;

public abstract class BaseValidator<T> implements Validator<T>{
	private static final Logger logger = LogManager.getLogger(BaseValidator.class);
	
	public boolean event(RequestInvalidException exception, Object action, Object txnId) {
		if(Objects.isNull(exception)) {
			logger.info("Validation of Consignment " + action + " request is successful for txnid[" + txnId + "]");
			return Boolean.TRUE;
		}else {
			logger.info("Validation of Consignment " + action + " request is failed for txnid[" + txnId + "]");
			throw exception;
		}
	}
}
