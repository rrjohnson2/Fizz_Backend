package com.jsware.fizz.service;

import java.util.ArrayList;
import java.util.List;

import com.jsware.fizz.model.idea.Focus;
import com.jsware.fizz.model.idea.Idea;
import com.jsware.fizz.model.network.Notice;

public interface Notification {
	
	
	
	public static void notifyNodeServer(Notice notice)
	{
		
	}
	
	
	public static List<String> getUsernameFromIdea(Idea idea)
	{
		List<String> usernames = new ArrayList<String>(); 
		return usernames;
		
	}
	public static List<String> getUsernameFromFocus(Focus[] focuses)
	{
		List<String> usernames = new ArrayList<String>(); 
		
		
		for (Focus focus : focuses) {
			
		}
		return usernames;
		
	}
}
