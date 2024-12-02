package com.gl.ceir.config.exceptions;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.gl.ceir.config.dto.ApiResponse;

@ControllerAdvice
public class AllExceptions {
	private static final Logger logger = LogManager.getLogger(AllExceptions.class);

	@ExceptionHandler(value = ResourceNotFoundException.class)
	public ResponseEntity<Object> exception(ResourceNotFoundException exception) {
		return new ResponseEntity<>(new ApiResponse(HttpStatus.NOT_FOUND.value(), "FAIL", exception.getMessage()),
				HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = ResourceServicesException.class)
	public ResponseEntity<Object> exception(ResourceServicesException exception) {
		return new ResponseEntity<>(
				new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "EXCEPTION", exception.getMessage()),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(value = MyFileNotFoundException.class)
	public ResponseEntity<Object> exception(MyFileNotFoundException exception) {
		return new ResponseEntity<>(new ApiResponse(HttpStatus.NOT_FOUND.value(), "FAIL", exception.getMessage()),
				HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = FileStorageException.class)
	public ResponseEntity<Object> exception(FileStorageException exception) {
		return new ResponseEntity<>(new ApiResponse(HttpStatus.NOT_FOUND.value(), "FAIL", exception.getMessage()),
				HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = RequestInvalidException.class)
	public ResponseEntity<Object> requestInvalidException(RequestInvalidException exception) {
		ApiResponse apiResponse = new ApiResponse(HttpStatus.BAD_REQUEST.value(), "INVALID", exception.getMessage());
		logger.info(apiResponse);
		
		return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
	}

}
