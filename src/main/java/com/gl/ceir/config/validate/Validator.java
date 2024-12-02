package com.gl.ceir.config.validate;

import com.gl.ceir.config.exceptions.RequestInvalidException;

public interface Validator<T> {

	public boolean validateRegister(T t) throws RequestInvalidException;

	public boolean validateEdit(T t) throws RequestInvalidException;

	public boolean validateViewById(Object t) throws RequestInvalidException;
	
	public boolean validateDelete(Object t) throws RequestInvalidException;
	
	public boolean validateFilter(Object t) throws RequestInvalidException;

	public boolean validateAcceptReject(Object t) throws RequestInvalidException;

}
