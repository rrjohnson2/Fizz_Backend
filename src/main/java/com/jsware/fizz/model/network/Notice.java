package com.jsware.fizz.model.network;

import com.jsware.fizz.constants.FizzConstants.Notification_Network_Actions;

public class Notice {
	public String username;
	public Object data;
	public int socket_key;
	public  Notification_Network_Actions action;
	public Notice(String username, Object data,int socket_key, Notification_Network_Actions action) {
		super();
		this.username = username;
		this.data = data;
		this.socket_key = socket_key;
		this.action = action;
	}
}
