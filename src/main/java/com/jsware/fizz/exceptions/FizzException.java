package com.jsware.fizz.exceptions;

public class FizzException extends Exception {

	private Object data;
	public FizzException(String error)
	{
		super(error);
	}
	public FizzException(String error,Object data)
	{
		super(error);
		this.setData(data);
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
}
