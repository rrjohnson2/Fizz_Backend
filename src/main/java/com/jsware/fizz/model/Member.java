package com.jsware.fizz.model;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Member {
	
	@Id
	@Column( unique = true ,length=10)
	private String username;
	
	private String firstName;
	
	private String lastName;
	
	private String email;
	
	private String salt;
	
	private String saltyPassword;
	
	
	@JsonIgnore
	public String getSalt() {
		return salt;
	}


	@JsonIgnore
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

	
	

}
