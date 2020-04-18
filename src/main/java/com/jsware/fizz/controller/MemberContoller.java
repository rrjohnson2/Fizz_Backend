package com.jsware.fizz.controller;



import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsware.fizz.constants.FizzConstants;
import com.jsware.fizz.constants.FizzConstants.Category;
import com.jsware.fizz.constants.FizzConstants.Error_Messages;
import com.jsware.fizz.constants.FizzConstants.Logger_State;
import com.jsware.fizz.exceptions.FizzException;
import com.jsware.fizz.model.interactions.Receipt;
import com.jsware.fizz.model.interactions.Ticket;
import com.jsware.fizz.model.member.Member;
import com.jsware.fizz.model.member.Preference;
import com.jsware.fizz.model.member.Profile;
import com.jsware.fizz.repository.MemberRepository;
import com.jsware.fizz.repository.PreferenceRepository;







@Controller
public class MemberContoller {
	
	@Autowired
	private MemberRepository memRepo;
	
	@Autowired
	private PreferenceRepository prefRepo;
	
	@Autowired
	private ObjectMapper mapper;
	

	
	
	
	@RequestMapping(value="/createMember",method=RequestMethod.POST)
	@ResponseBody
	public Receipt createMember(@RequestBody Member member) throws FizzException
	{
		try
		{
			if(!memRepo.existByUsername(member.getUsername()))
			{
				member = memRepo.save(member);
				FizzConstants.addMember(member);
				if(member.getPreferences()!=null)
				{
					for(Preference preference:member.getPreferences())
					{
						preference.setOwner(member);
						prefRepo.save(preference);
					}
				}
			}
			else 
			{
				FizzConstants.log(
						Logger_State.ERROR,
						Error_Messages.MEMEBER_EXIST_X.getMessage() , 
						MemberContoller.class);
				throw new FizzException(
						FizzConstants.Error_Messages.MEMEBER_EXIST_X.getMessage(),
						suggestUsernames(member.getUsername())
								);	
			}
			FizzConstants.log(
					Logger_State.INFO, 
					FizzConstants.Receipt_Messages.CREATED_MEMBER.getMessage(),
					MemberContoller.class);
			return new Receipt(
					FizzConstants.Receipt_Messages.CREATED_MEMBER.getMessage(),
					member);
		}
		catch (FizzException e) {
			throw e;
		}
		catch(Exception e)
		{
			FizzConstants.log(Logger_State.ERROR, e.getMessage(), MemberContoller.class);
			throw new FizzException(FizzConstants.Error_Messages.CREATED_MEMBER_X.getMessage());
		}
	}
	
	private List<String> suggestUsernames(String username) {
		int count = 0;
		List<String> data = new ArrayList<String>();
		
		while(count<FizzConstants.suggestCount)
		{
			String username_suggested = username.concat(
					String.valueOf(
							new Random().nextInt(5000)
							)
					);
			if(!memRepo.existByUsername(username_suggested))
			{
				data.add(username_suggested);
				count++;
			}
		}
		return data;
	}

	@RequestMapping(value="/deleteMember",method=RequestMethod.POST)
	@ResponseBody
	public Receipt deleteMember(@RequestParam String username) throws FizzException
	{
		try
		{
			memRepo.deleteByUsername(username);
			FizzConstants.log(
					Logger_State.INFO, 
					FizzConstants.Receipt_Messages.DELETED_MEMBER.getMessage(),
					MemberContoller.class);
			return new Receipt(
					FizzConstants.Receipt_Messages.DELETED_MEMBER.getMessage(),
					username);
		}
		catch(Exception e)
		{
			FizzConstants.log(Logger_State.ERROR, e.getMessage(), MemberContoller.class);
			throw new FizzException(FizzConstants.Error_Messages.DELETED_MEMBER_X.getMessage());
		}
	}
	
	@RequestMapping(value="/getProfile",method=RequestMethod.GET)
	@ResponseBody
	public Receipt getProfile(@RequestParam String username) throws FizzException
	{
		try
		{
			Profile profile = new Profile(
					memRepo.findByUsername(username)
						);
			FizzConstants.log(
					Logger_State.INFO, 
					FizzConstants.Receipt_Messages.PROFILE_OBTAINED.getMessage(),
					MemberContoller.class);
			return new Receipt(
					FizzConstants.Receipt_Messages.PROFILE_OBTAINED.getMessage(),
					profile);
		}
		catch(Exception e)
		{
			FizzConstants.log(Logger_State.ERROR, e.getMessage(), MemberContoller.class);
			throw new FizzException(FizzConstants.Error_Messages.PROFILE_X.getMessage());
		}
	}
	
	@RequestMapping(value="/login",method=RequestMethod.POST)
	@ResponseBody
	public Receipt login(@RequestBody Ticket ticket) throws FizzException
	{
		try
		{
			Member member = memRepo.findByUsername(ticket.getCustomer());
			String password = (String) ticket.getData();
			if(!member.AccessGranted(password))
			{
				throw new FizzException(FizzConstants.Error_Messages.LOGIN_X.getMessage());
			}
			FizzConstants.log(
					Logger_State.INFO, 
					FizzConstants.Receipt_Messages.LOGIN_SUCCESSFUL.getMessage(),
					MemberContoller.class);
			return new Receipt(
					FizzConstants.Receipt_Messages.LOGIN_SUCCESSFUL.getMessage(),
					member);
		}
		catch(Exception e)
		{
			FizzConstants.log(Logger_State.ERROR, e.getMessage(), MemberContoller.class);
			throw new FizzException(FizzConstants.Error_Messages.LOGIN_X.getMessage());
		}
	}

	
	
	@RequestMapping(value="/update",method=RequestMethod.POST)
	@ResponseBody
	public Receipt update(@RequestBody Ticket ticket) throws FizzException
	{
		try
		{
			Member member = memRepo.findByUsername(ticket.getCustomer());
			
			Object res = null;
			
			switch (ticket.getUpdate_reason()) {
			case PICTURE:
				 picture(ticket,member);
				break;
			case PASSWORD:
				password(ticket,member);
				break;
			case USERNAME:
				username(ticket,member);
				break;
			case FIRSTNAME:
				firstname(ticket,member);
				break;
			case LASTNAME:
				lastname(ticket,member);
				break;
			case EMAIL:
				email(ticket,member);
				break;
			case PREFERENCES:
				preferences(ticket, member);
				res = member.getPreferences();
				break;
			default:
				throw new Exception();
			}
			
			
			FizzConstants.log(
					Logger_State.INFO, 
					FizzConstants.Receipt_Messages.UPDATE_SUCCESSFUL.getMessage(),
					MemberContoller.class);
			return new Receipt(
					FizzConstants.Receipt_Messages.UPDATE_SUCCESSFUL.getMessage(),
				res);
		}
		catch(Exception e)
		{
			FizzConstants.log(Logger_State.ERROR, e.getMessage(), MemberContoller.class);
			throw new FizzException(FizzConstants.Error_Messages.UPDATE_X.getMessage());
		}
	}
	
	
	
	private void updatePreferences(Member member, List<Preference> list) {
		
		HashMap<Preference, Boolean> isNew = new HashMap<>();
		List<Preference> og = member.getPreferences();
		int i = 0;
		int larger = og.size() < list.size()? list.size(): og.size();
		while(i <larger)
		{
			Preference nue = i <list.size() ? list.get(i): null;
			Preference old = i< og.size() ? og.get(i): null;
			
			helper(isNew, nue,true);
			helper(isNew, old,false);
			i++;
		}
		
		for(Preference pre: isNew.keySet())
		{
			if(isNew.get(pre))
			{
				pre.setOwner(member);
				prefRepo.save(pre);
			}
			else prefRepo.delete(pre);
		}
			
	}

	private void helper(HashMap<Preference, Boolean> isNew, Preference pre,boolean nue) {
		
		if(pre == null) return;
		
		if(!isNew.containsKey(pre))
		{
			isNew.put(pre, nue);
		}
		else {
			isNew.remove(pre);
		}
	}
	
	private void picture(Ticket ticket,Member member) throws  JsonProcessingException
	{
			String data = mapper.writeValueAsString(ticket.getData()).replace("\"", "");
			
			member.setProfilePicture(data);
			
			memRepo.save(member);
			
	}
	private void password(Ticket ticket,Member member) throws FizzException, IOException
	{
			
		HashMap<String, Object> data = mapper.readValue(
				mapper.writeValueAsString(ticket.getData()),
				HashMap.class); 
		
			String old_password = (String) data.get("old_password");
			
			String password = (String) data.get("password");
			
			if(!member.AccessGranted(old_password)) throw new FizzException(FizzConstants.Error_Messages.UPDATE_X.getMessage());
			
			member.setPassword(password);
			
			memRepo.save(member);
	}
	private  void username(Ticket ticket, Member member) throws  JsonProcessingException
	{
			
			String data = mapper.writeValueAsString(ticket.getData()).replace("\"", "");
			
			member.setUsername(data);
			
			memRepo.save(member);
		
	}
	

	private  void firstname(Ticket ticket, Member member) throws  JsonProcessingException
	{
			
			String data = mapper.writeValueAsString(ticket.getData()).replace("\"", "");
			
			member.setFirstName(data);
			
			memRepo.save(member);
		
	}

	private  void lastname(Ticket ticket, Member member) throws  JsonProcessingException
	{
			
			String data = mapper.writeValueAsString(ticket.getData()).replace("\"", "");
			
			member.setLastName(data);
			
			memRepo.save(member);
		
	}

	private  void email(Ticket ticket, Member member) throws  JsonProcessingException
	{
			
			String data = mapper.writeValueAsString(ticket.getData()).replace("\"", "");
			
			member.setEmail(data);
			
			memRepo.save(member);
		
	}
	private void preferences( Ticket ticket, Member member) throws Exception
	{
			
			Preference[] data = mapper.readValue(
 					mapper.writeValueAsString(ticket.getData()),
					Preference[].class);
			
		
			updatePreferences(member,Arrays.asList(data));
	}
		
	

	
}
