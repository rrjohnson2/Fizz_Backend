package com.jsware.fizz.model.member;

import java.util.ArrayList;
import java.util.List;

import com.jsware.fizz.model.idea.Idea;
import com.jsware.fizz.model.rating.Rating;
import com.jsware.fizz.model.retort.Message;
import com.jsware.fizz.model.retort.Retort;

public class Profile {
	
	private String profilePicture;
	
	private String username;
	
	private String firstName;
	
	private String lastName;
	
	private String email;

	private List<Preference> preferences = new ArrayList<Preference>();
	
	private List<Idea> created_ideas = new ArrayList<Idea>();
	
	private List<Idea> ideas_retorted = new ArrayList<Idea>();
	
	private List<Idea> ideas_messaged = new ArrayList<Idea>();
	private List<Idea> ideas_rated = new ArrayList<Idea>();
	
	private List<Retort> created_retorts = new ArrayList<Retort>();
	
	private List<Message> created_messages = new ArrayList<Message>();
	
	private List<Rating> created_ratings = new ArrayList<Rating>();
	
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
		this.created_ratings = mem.getRatings();
		this.setProfilePicture(mem.getProfilePicture());
		
		for(Retort ret: created_retorts)
		{
			ideas_retorted.add(ret.getIdea());
		}
		for(Message msg: created_messages)
		{
			ideas_messaged.add(msg.getRetort().getIdea());
		}
		for(Rating rate: created_ratings)
		{
			ideas_rated.add(rate.getIdea());
		}
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

	public String getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}

	public List<Idea> getIdeas_retorted() {
		return ideas_retorted;
	}

	public void setIdeas_retorted(List<Idea> ideas_retorted) {
		this.ideas_retorted = ideas_retorted;
	}

	public List<Idea> getIdeas_messaged() {
		return ideas_messaged;
	}

	public void setIdeas_messaged(List<Idea> idea_messaged) {
		this.ideas_messaged = idea_messaged;
	}

	public List<Rating> getCreated_ratings() {
		return created_ratings;
	}

	public void setCreated_ratings(List<Rating> created_ratings) {
		this.created_ratings = created_ratings;
	}

	public List<Idea> getIdeas_rated() {
		return ideas_rated;
	}

	public void setIdeas_rated(List<Idea> ideas_rated) {
		this.ideas_rated = ideas_rated;
	}
	
	

}
