package com.jsware.fizz.model.network;


public class Notice {
	public String username;
	public Object data;
	public int socket_key;
	public Notice(String username, Object data,int socket_key) {
		super();
		this.username = username;
		this.data = data;
		this.socket_key = socket_key;
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
	public int getSocket_key() {
		return socket_key;
	}
	public void setSocket_key(int socket_key) {
		this.socket_key = socket_key;
	}
	
	
}
