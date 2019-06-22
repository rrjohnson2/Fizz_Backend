package com.jsware.fizz.exceptions;

import java.util.Date;

import org.springframework.http.HttpStatus;

public class ErrorDetail {
	
	private String message, description;
	private Object data;
	private final Date timeStamp= new Date();
	public ErrorDetail(String message, String description) {
		super();
		this.message = message;
		this.description = description;
	}
	public ErrorDetail(String message, String description,Object data) {
		super();
		this.message = message;
		this.description = description;
		this.data=data;
	}
	public String getMessage() {
		return message;
	}
	public String getDescription() {
		return description;
	}
	public Date getTimeStamp() {
		return timeStamp;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
	
	
	

}
