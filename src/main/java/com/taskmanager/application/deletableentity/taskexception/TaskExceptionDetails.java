package com.taskmanager.application.deletableentity.taskexception;

import java.sql.Timestamp;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Extensible class for formatting exceptions thrown by the Task package.
 */

public class TaskExceptionDetails {

	private HttpStatus status;
	//Note: This makes timestamp more human readable to assist with tracking down bugs.
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private final Timestamp timestamp;
	private String message;
	private String debugMessage;
	//Note: Default variables can be referenced from application.properties instead and generally should be in any larger projects.
	private static final String DEFAULT_MESSAGE = "Unknown error";

	private TaskExceptionDetails() {
		timestamp = new Timestamp(System.currentTimeMillis());
	}

	public TaskExceptionDetails(HttpStatus status) {
		this();
		this.status = status;
	}

	public TaskExceptionDetails(HttpStatus status, Throwable ex) {
		this();
		this.status = status;
		this.message = DEFAULT_MESSAGE;
		this.debugMessage = ex.getLocalizedMessage();
	}

	public TaskExceptionDetails(HttpStatus status, String message, Throwable ex) {
		this();
		this.status = status;
		this.message = message;
		this.debugMessage = ex.getLocalizedMessage();
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDebugMessage() {
		return debugMessage;
	}

	public void setDebugMessage(String debugMessage) {
		this.debugMessage = debugMessage;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}
	
}
