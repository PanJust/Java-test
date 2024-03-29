package com.etnetera.hr.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.etnetera.hr.controller.exception.InvalidDataException;
import com.etnetera.hr.rest.Errors;
import com.etnetera.hr.rest.ValidationError;

/**
 * 
 * Main REST controller.
 * 
 * @author Etnetera
 *
 */
public abstract class EtnRestController {

	@ExceptionHandler(InvalidDataException.class)
	public ResponseEntity<Errors> handleValidationException(InvalidDataException ex) {
		BindingResult result = ex.getResult();
		Errors errors = new Errors();
		List<ValidationError> errorList = result.getFieldErrors().stream().map(e -> {
			return new ValidationError(e.getField(), e.getCode());
		}).collect(Collectors.toList());
		errors.setErrors(errorList);
		return ResponseEntity.badRequest().body(errors);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class) 
	public ResponseEntity<Errors> handleValidationException(MethodArgumentNotValidException ex) {
		return handleValidationException(new InvalidDataException(ex.getBindingResult()));
	}
	
}
