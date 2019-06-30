package com.jsware.fizz.model;

import java.util.ArrayList;
import java.util.List;

public class Profile {
	
	private String username;
	
	private String firstName;
	
	private String lastName;
	
	private String email;

	private List<Preference> preferences = new ArrayList<Preference>();
	
	private List<Idea> created_ideas = new ArrayList<Idea>();
	
	private List<Retort> created_retorts = new ArrayList<Retort>();
	
	private List<Message> created_messages = new ArrayList<Message>();
	
	public Profile() {}

	public Profile(Member mem) {
		super();
		this.username = mem.getUsername();
		this.firstName = mem.getFirstName();
		this.lastName = mem.getLastName();
		this.email = mem.getEmail();
		this.preferences = mem.getPreferences();
		this.created_ideas = mem.getCreated_ideas();
		this.created_retorts = mem.getCreated_retorts();
		this.created_messages = mem.getCreated_messages();
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

	public List<Preference> getPreferences() {
		return preferences;
	}

	public List<Idea> getCreated_ideas() {
		return created_ideas;
	}

	public List<Retort> getCreated_retorts() {
		return created_retorts;
	}

	public List<Message> getCreated_messages() {
		return created_messages;
	}
	
	

}
