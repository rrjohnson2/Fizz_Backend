package com.jsware.fizz.testconstants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.assertj.core.util.Arrays;

import com.jsware.fizz.constants.FizzConstants.Category;
import com.jsware.fizz.constants.FizzConstants.Vote_Type;
import com.jsware.fizz.model.idea.Focus;
import com.jsware.fizz.model.idea.Idea;
import com.jsware.fizz.model.interactions.Ticket;
import com.jsware.fizz.model.member.Member;
import com.jsware.fizz.model.member.Preference;
import com.jsware.fizz.model.rating.Rating;
import com.jsware.fizz.model.retort.Retort;

public class TestConstants {
	
	/*
	 * Sam Bethe Profile
	 */
	public Preference preference_A = new Preference(Category.MOVIES,10);
	public Preference preference_B = new Preference(Category.TECHNOLOGY,10);
	public Preference preference_C = new Preference(Category.TECHNOLOGY,5);
	public Preference preference_D = new Preference(Category.MOVIES,5);
	public List<Preference> sam_preferences = new ArrayList<Preference>();
	public String sam_password = "sammyboy";
	public Member sam_bethe;
	public MemberJson sam_bethe_json;
	
	public String david_password = "davyboy";
	public Member david_reed;
	public MemberJson david_reed_json;
	
	public Focus sam_idea_focus = new  Focus(Category.MISC);
	public Idea sam_idea= new Idea("Cats On Water", "cats need sufboards please dont be a bitch about it");
	public Retort david_retort;
	public Ticket sam_idea_ticket=new Ticket();
	public Ticket sam_message_ticket=new Ticket();
	public HashMap<String,Object> sam_reply_data= new HashMap<String, Object>();
	public Ticket david_retort_ticket= new Ticket();
	public Ticket david_rate_ticket = new Ticket();
	public HashMap<String,Object> david_retort_data= new HashMap<String, Object>();
	public HashMap<String,Object> david_rating_data= new HashMap<String, Object>();
	 
	
	public TestConstants() {
		
		SamBethe();
	}

	public void DavidReed() {
		david_reed = new Member("davidboy","David","Reed","reed2@gmail.com",null);
		david_reed_json = new MemberJson(david_reed, david_password);
		david_retort= new Retort("this is such a great idea, get started");
		david_retort_data.put("idea", sam_idea.getId());
		david_retort_data.put("retort", david_retort);
		david_retort_ticket.setCustomer(david_reed.getUsername());
		david_retort_ticket.setData(david_retort_data);
		david_rate_ticket.setCustomer(david_reed.getUsername());
		david_rating_data.put("idea", sam_idea.getId());
		david_rating_data.put("vote", Vote_Type.UP);
		david_rate_ticket.setData(david_rating_data);
		
		
	}

	private void SamBethe() {
		sam_preferences.add(preference_A);
		sam_preferences.add(preference_B);
		sam_bethe= new Member("samsquach","Sam", "Bethe", "sBethe@gmail", sam_preferences);
		sam_bethe_json= new MemberJson(sam_bethe,sam_password);
		sam_idea.getFocus().add(sam_idea_focus);
		sam_idea_ticket.setCustomer(sam_bethe.getUsername());
		sam_idea_ticket.setData(sam_idea);
	}
	public void SamReply()
	{
		sam_reply_data.put("comment", "thanks");
		sam_reply_data.put("retort", david_retort.getId());
		sam_message_ticket.setCustomer(sam_bethe.getUsername());
		sam_message_ticket.setData(sam_reply_data);

	}

}
