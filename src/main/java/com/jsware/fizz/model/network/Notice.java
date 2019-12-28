package com.jsware.fizz.model.network;


public class Notice {
	public String username;
	public Object data;
	public Notice(String username, Object data) {
		super();
		this.username = username;
		this.data = data;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
	
}
