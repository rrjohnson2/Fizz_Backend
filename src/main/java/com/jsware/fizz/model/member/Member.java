package com.jsware.fizz.model.member;


import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Random;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Fetch;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jsware.fizz.model.idea.Idea;
import com.jsware.fizz.model.retort.Message;
import com.jsware.fizz.model.retort.Retort;

@Entity
public class Member {
	
	@Id
	@Column( unique = true ,length=10)
	private String username;
	
	private String firstName;
	
	private String lastName;
	
	private String email;
	
	@JsonIgnore
	private String salt;
	
	@JsonIgnore
	private String saltyPassword;
	
	@OneToMany(mappedBy="creator")
	@JsonIgnore
	private List<Idea> created_ideas = new ArrayList<Idea>();
	
	
	@OneToMany(mappedBy="creator")
	@JsonIgnore
	private List<Retort> created_retorts = new ArrayList<Retort>();
	
	@OneToMany(mappedBy="owner")
	private List<Preference> preferences = new ArrayList<Preference>();
	


	@OneToMany(mappedBy="creator")
	@JsonIgnore
	private List<Message> created_messages = new ArrayList<Message>();
	
	
	
	
	public Member(String username, String firstName, String lastName, String email, List<Preference> preferences) {
		super();
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.preferences = preferences;
	}

	public Member() {};

	public List<Retort> getCreated_retorts() {
		return created_retorts;
	}


	public void setCreated_retorts(List<Retort> created_retorts) {
		this.created_retorts = created_retorts;
	}


	public List<Preference> getPreferences() {
		return preferences;
	}


	public void setPreferences(List<Preference> preferences) {
		this.preferences = preferences;
	}


	public List<Message> getCreated_messages() {
		return created_messages;
	}


	public void setCreated_messages(List<Message> created_messages) {
		this.created_messages = created_messages;
	}


	public void setSalt(String salt) {
		this.salt = salt;
	}


	public void setSaltyPassword(String saltyPassword) {
		this.saltyPassword = saltyPassword;
	}
	
	
	public List<Idea> getCreated_ideas() {
		return created_ideas;
	}


	public void setCreated_ideas(List<Idea> created_ideas) {
		this.created_ideas = created_ideas;
	}


	
	public String getSalt() {
		return salt;
	}


	
	public String getSaltyPassword() {
		return saltyPassword;
	}

	public String getFirstName() {
		return firstName;
	}



	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}



	public String getLastName() {
		return lastName;
	}



	public void setLastName(String lastName) {
		this.lastName = lastName;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getUsername() {
		return username;
	}



	public void setUsername(String username) {
		this.username = username;
	}


	public void setPassword(String password)
	{
		Random rand = new  SecureRandom();
		
		byte[] saltyBytes = new byte[32];
		
		rand.nextBytes(saltyBytes);
		
		this.salt = Base64.getEncoder().encodeToString(saltyBytes);
		
		this.saltyPassword= salt+encrypt(this.salt,password)+salt;
	}



	private String encrypt(String salt, String password) {
		/*
		 * This encryption method takes the salt and password mixes it up by alternating 
		 * characters into a result variable salt starts first;
		 * but if one or the other runs out the the crypt uses the remaining characters of the other list
		 */
		char[] res = new char[salt.length()+password.length()];
		
		char[] salt_char = salt.toCharArray();
		char[] password_char = password.toCharArray();
		
		int salt_count=0;
		int salt_max = salt.length();
		int password_count =0;
		int password_max = password.length();
		
		for(int i = 0; i<res.length;i++)
		{
			if(i%2==0)
			{
				if(salt_count<salt_max)
				{
					res[i]=salt_char[salt_count];
					salt_count++;
				}
				else {
					populateRemaining(i,password_char,password_count,res);
					break;
				}
			}
			
			else
			{
				if(password_count<password_max)
					{
						
						res[i]= password_char[password_count];
						password_count++;
					}
				else {
					populateRemaining(i,salt_char,salt_count,res);
					break;
					}
				
			}
		}
			
		return new String(res);
	}



	private void populateRemaining(int current, char[] _char, int _count, char[] res) {
		for(int i= current;i<res.length;i++)
		{
			res[i]=_char[_count];
			_count++;
		}
	}


	public boolean AccessGranted(String passowrd)
	{
		return saltyPassword.equals(salt+passowrd+salt);
	}
	
	

}
