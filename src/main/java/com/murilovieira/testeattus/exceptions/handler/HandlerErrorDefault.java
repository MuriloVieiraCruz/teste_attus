package com.murilovieira.testeattus.exceptions.handler;

import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import com.murilovieira.testeattus.exceptions.*;
import jakarta.validation.ConstraintViolationException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Map;

@ControllerAdvice
@RestControllerAdvice
public class HandlerErrorDefault {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public Map<String, Object> handle(){
		return errorMapCreator(ApiError.INVALID_BODY,
				"The body of the request has errors or does not exist");
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(InvalidDefinitionException.class)
	public Map<String, Object> handle(InvalidDefinitionException ide){
		String attribute = ide.getPath().get(ide.getPath().size() - 1).getFieldName();
		String errorMessage = "Attribute '" + attribute + "' has invalid format";
	    return errorMapCreator(ApiError.INVALID_FORMAT, errorMessage);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ConstraintViolationException.class)
	public Map<String, Object> handle(ConstraintViolationException cve){

		JSONObject body = new JSONObject();

		JSONArray messages = new JSONArray();

		body.put("errors", messages);

		cve.getConstraintViolations().forEach((error) -> {

	    	String[] paths = error.getPropertyPath().toString().split("\\.");
	    	
	    	String attribute = paths[paths.length - 1];
	    	
	    	String errorMessage = error.getMessage();
	    	
	    	String completeMessage = "\"The attribute '" + attribute +
	    			"' presented the following error: '" + errorMessage + "'\"";

	    	String plainJsonError = "{ code:" + ApiError.CONDITION_VIOLATED.getCode() +
	    			", message: " + completeMessage + " }";

			messages.put(new JSONObject(plainJsonError));

	    });

	    return body.toMap();

	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(IllegalArgumentException.class)
	public Map<String, Object> handle(IllegalArgumentException ie){
		return errorMapCreator(ApiError.INVALID_PARAMETER, ie.getMessage());
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(NullPointerException.class)
	public Map<String, Object> handle(NullPointerException npe){
		return errorMapCreator(ApiError.INVALID_PARAMETER, npe.getMessage());
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(BusinessException.class)
	public Map<String, Object> handle(BusinessException be){
		return errorMapCreator(ApiError.RULE_ERROR, be.getMessage());
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MissingPathVariableException.class)
	public Map<String, Object> handle(MissingPathVariableException mpve){
		return errorMapCreator(ApiError.PRECONDITION_REQUIRED, mpve.getMessage());
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public Map<String, Object> handle(MethodArgumentTypeMismatchException matme){
		return errorMapCreator(ApiError.INVALID_TYPE_PARAMETER, "The URI has invalid values");
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public Map<String, Object> handle(HttpRequestMethodNotSupportedException hrmnse){
		return errorMapCreator(ApiError.HTTP_METHOD_NOT_ALLOWED, hrmnse.getMessage());
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public Map<String, Object> handle(MissingServletRequestParameterException mrpe){
		return errorMapCreator(ApiError.PARAMETER_REQUIRED, mrpe.getMessage());
	}
	
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(RegisterNotFoundException.class)
	public Map<String, Object> handle(RegisterNotFoundException rnee){
		return errorMapCreator(ApiError.REGISTER_NOT_FOUND, rnee.getMessage());
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ConverterException.class)
	public Map<String, Object> handle(ConverterException ce){
		return errorMapCreator(ApiError.INVALID_CONVERSION, ce.getMessage());
	}	
	
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(IntegrationException.class)
	public Map<String, Object> handle(IntegrationException ie){
		return errorMapCreator(ApiError.INVALID_INTEGRATION, ie.getMessage());
	}	
	
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(InvalidDataAccessResourceUsageException.class)
	public Map<String, Object> handle(InvalidDataAccessResourceUsageException ie){
		return errorMapCreator(ApiError.INVALID_INTEGRATION,
				"An integration error occurred with the external API");
	}	
	
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(DataIntegrityViolationException.class)
	public Map<String, Object> handlePSQLExceptions(
			DataIntegrityViolationException dve){
	    return errorMapCreator(ApiError.INVALID_PARAMETER,
	    		"A referential integrity error has occurred in the database");
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, Object> handle(MethodArgumentNotValidException manve){
		var errors = manve.getFieldErrors();
		JSONObject body = new JSONObject();
		JSONObject details = new JSONObject();
		errors.forEach((error) -> {
			details.put("code", ApiError.INVALID_FIELD.getCode());
			details.put("message", error.getDefaultMessage());
		});
		body.put("errors", details);
		return body.toMap();
	}
	
	private Map<String, Object> errorMapCreator(ApiError apiError, String errorMessage){
		
		JSONObject body = new JSONObject();					
		
		JSONObject detail = new JSONObject();
		detail.put("message", errorMessage);
		detail.put("code", apiError.getCode());
		
		JSONArray details = new JSONArray();
		details.put(detail);
		
		body.put("errors", details);
		
		return body.toMap();
		
	}
	
}
