package com.jsware.fizz.model.network;

public class Client {

	private String username;
	private int key;
	
	public Client() {
		
	}
	public Client(String username, int key)
	{
		this.username = username;
		this.key = key;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}
		
	
}
