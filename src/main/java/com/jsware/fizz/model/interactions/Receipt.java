package com.jsware.fizz.model.interactions;

public class Receipt {
	private String message;
	private Object data;
	
	
	public String getMessage() {
		return message;
	}

	public Object getData() {
		return data;
	}

	public Receipt(String message, Object data) {
		super();
		this.message = message;
		this.data = data;
	}
	
	public Receipt(String message) {
		super();
		this.message = message;
	}
	
	public Receipt() {
	
	}
	
	
}
