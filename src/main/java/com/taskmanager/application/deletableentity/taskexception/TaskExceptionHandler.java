package com.taskmanager.application.deletableentity.taskexception;

import javax.persistence.EntityNotFoundException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Extensible exception handler for exceptions thrown by Task entities and controllers.
 */

//Note: This annotation restricts this handler to the Task package.
//		Other entities and controllers might have different approaches to handling exception messaging, logging and security.
@RestControllerAdvice(basePackages = "com.taskmanager.application.deletableentity.task")
public class TaskExceptionHandler extends ResponseEntityExceptionHandler {

	/**
	 * Handles 404 exceptions for the Task package.
	 */
	@ExceptionHandler(EntityNotFoundException.class)
	protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex) {
		TaskExceptionDetails taskExceptionDetails = new TaskExceptionDetails(HttpStatus.NOT_FOUND);
		taskExceptionDetails.setMessage(ex.getMessage());
		return buildResponseEntity(taskExceptionDetails);
	}
	
	/**
	 * Handles validation exceptions thrown by @Valid for the Task package.
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		TaskExceptionDetails taskExceptionDetails = new TaskExceptionDetails(status);
		taskExceptionDetails.setMessage(ex.getMessage());
		return buildResponseEntity(taskExceptionDetails);
	}
	
	/**
	 * Packages TaskExceptionDetails into a standard api response.
	 */
	private ResponseEntity<Object> buildResponseEntity(TaskExceptionDetails taskExceptionDetails) {
       return new ResponseEntity<>(taskExceptionDetails, taskExceptionDetails.getStatus());
   }
}
