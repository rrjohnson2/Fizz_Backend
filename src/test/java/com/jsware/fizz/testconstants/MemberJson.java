package com.jsware.fizz.testconstants;

import java.util.List;

import com.jsware.fizz.model.Member;
import com.jsware.fizz.model.Preference;

public class MemberJson {
	
	public MemberJson(Member member,String password) {
		this.email= member.getEmail();
		this.firstName=member.getFirstName();
		this.password = password;
		this.lastName=member.getLastName();
		this.username=member.getUsername();
		this.preferences=member.getPreferences();
		
		
	}

	public String getUsername() {
		return username;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public List<Preference> getPreferences() {
		return preferences;
	}
	

	private String username;
	
	private String firstName;
	
	private String lastName;
	
	private String email;
	
	private String password;
	
	private List<Preference> preferences;

	
	

}
