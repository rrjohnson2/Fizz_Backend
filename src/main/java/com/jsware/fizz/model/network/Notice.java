package com.jsware.fizz.model.network;

import com.jsware.fizz.constants.FizzConstants.Notification_Network_Actions;
import com.jsware.fizz.model.idea.Idea;
import com.jsware.fizz.model.member.Member;
import com.jsware.fizz.model.rating.Rating;
import com.jsware.fizz.model.retort.Message;
import com.jsware.fizz.model.retort.Retort;

public class Notice {
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
}
