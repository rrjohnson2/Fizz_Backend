package com.jsware.fizz.model.interactions;

import com.jsware.fizz.model.member.Member;

public class Ticket {

	private Member customer;
	
	private Object data;
	
	public Ticket() {}
	
	

	public Ticket(Member customer, Object data) {
		super();
		this.customer = customer;
		this.data = data;
	}



	public Member getCustomer() {
		return customer;
	}

	public void setCustomer(Member customer) {
		this.customer = customer;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
}
