package com.devsuperior.bds02.resources.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.devsuperior.bds02.services.exceptions.DatabaseException;
import com.devsuperior.bds02.services.exceptions.ResourceNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {

	private HttpStatus httpStatus;
	private StandartError err;

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandartError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {
		err = new StandartError();
		setHttpStatus(HttpStatus.NOT_FOUND);
		err.setTimestamp(Instant.now());
		err.setStatus(getHttpStatus().value());
		err.setError("Resource Not Found");
		err.setMessage(e.getMessage());
		err.setPath(request.getRequestURI());
		return ResponseEntity.status(getHttpStatus()).body(err);
	}

	@ExceptionHandler(DatabaseException.class)
	public ResponseEntity<StandartError> dataBaseIntegrity(DatabaseException e, HttpServletRequest request){
		err = new StandartError();
		setHttpStatus(HttpStatus.BAD_REQUEST);
		err.setTimestamp(Instant.now());
		err.setStatus(getHttpStatus().value());
		err.setError("Database Integrity");
		err.setMessage(e.getMessage());
		err.setMessage(request.getRequestURI());
		return ResponseEntity.status(getHttpStatus()).body(err);
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

}
