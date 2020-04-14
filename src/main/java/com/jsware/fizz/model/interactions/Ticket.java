package com.jsware.fizz.model.interactions;

import com.jsware.fizz.model.member.Member;

public class Ticket {
	
	public static enum UpdateReason 
	{
		PICTURE,
		PASSWORD,
		USERNAME,
		FIRSTNAME,
		LASTNAME,
		EMAIL,
		PREFERENCES
	}

	private String customer;
	
	private Object data;
	
	private  UpdateReason update_reason; 
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



	public UpdateReason getUpdate_reason() {
		return update_reason;
	}



	public void setUpdate_reason(UpdateReason update_reason) {
		this.update_reason = update_reason;
	}
	
}
