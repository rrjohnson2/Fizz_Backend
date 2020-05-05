package com.jsware.fizz.model.network;

import java.util.Random;

import com.jsware.fizz.constants.FizzConstants.Notification_Network_Actions;
import com.jsware.fizz.model.idea.Idea;
import com.jsware.fizz.model.rating.Rating;
import com.jsware.fizz.model.retort.Message;
import com.jsware.fizz.model.retort.Retort;

public class Notice {
	public int id;
	public String username;
	public String creator_profilePicture;
	public String creator_username;
	public Object data;
	public int socket_key;
	public int idea_id;
	public int retort_id;
	public boolean checked =false;
	public  Notification_Network_Actions action;
	public Notice(String username, Object data,int socket_key, Notification_Network_Actions action) {
		super();
		this.username = username;
		this.data = data;
		this.socket_key = socket_key;
		this.action = action;
		id = new Random().nextInt();
		
		if(data.getClass().equals(Rating.class))
		{
			Rating rate  = (Rating) data;
			creator_profilePicture = rate.getCreator().getProfilePicture();
			creator_username = rate.getCreator().getUsername();
			idea_id = (int) rate.getIdea().getId();
		}
		else if(data.getClass().equals(Retort.class)) {
			Retort retort  = (Retort) data;
			creator_profilePicture = retort.getCreator().getProfilePicture();
			creator_username = retort.getCreator().getUsername();
			idea_id = (int) retort.getIdea().getId();
		}
		else if(data.getClass().equals(Message.class))
		{
			Message msg  = (Message) data;
			creator_profilePicture = msg.getCreator().getProfilePicture();
			creator_username = msg.getCreator().getUsername();
			retort_id = (int) msg.getRetort().getId();
			idea_id = (int) msg.getRetort().getIdea().getId(); 
		}
		else if(data.getClass().equals(Idea.class)) {
			Idea idea = (Idea) data;
			creator_profilePicture = idea.getCreator().getProfilePicture();
			creator_username = idea.getCreator().getUsername();
		}
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Notice other = (Notice) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
}
