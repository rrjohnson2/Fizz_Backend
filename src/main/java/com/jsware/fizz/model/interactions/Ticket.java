package com.jsware.fizz.model.interactions;

import com.jsware.fizz.model.member.Member;

public class Ticket {

	private String customer;
	
	private Object data;
	
	public Ticket() {}
	
	

	public Ticket(String customer, Object data) {
		super();
		this.customer = customer;
		this.data = data;
	}



	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
}
