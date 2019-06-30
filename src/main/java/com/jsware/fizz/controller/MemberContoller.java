package com.jsware.fizz.controller;



import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jsware.fizz.constants.FizzConstants;
import com.jsware.fizz.constants.FizzConstants.Error_Messages;
import com.jsware.fizz.constants.FizzConstants.Logger_State;
import com.jsware.fizz.exceptions.FizzException;
import com.jsware.fizz.model.Member;
import com.jsware.fizz.model.Preference;
import com.jsware.fizz.model.Profile;
import com.jsware.fizz.model.Receipt;
import com.jsware.fizz.repository.MemberRepository;
import com.jsware.fizz.repository.PreferenceRepository;







@Controller
public class MemberContoller {
	
	private MemberRepository memRepo;
	private PreferenceRepository prefRepo;
	
	@Autowired
	public MemberContoller(MemberRepository memRepo, PreferenceRepository prefRepo)
	{
		this.memRepo=memRepo;
		this.prefRepo=prefRepo;
	}
	
	@RequestMapping(value="/createMember",method=RequestMethod.POST)
	@ResponseBody
	public Receipt createMember(@RequestBody Member member) throws FizzException
	{
		try
		{
			if(!memRepo.existsById(member.getUsername()))
			{
				memRepo.save(member);
				for(Preference preference:member.getPreferences())
				{
					preference.setOwner(member);
					prefRepo.save(preference);
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
			if(!memRepo.existsById(username_suggested))
			{
				data.add(username_suggested);
				count++;
			}
		}
		return data;
	}

	@RequestMapping(value="/deleteMember",method=RequestMethod.POST)
	@ResponseBody
	public Receipt deleteMember(@RequestBody Member member) throws FizzException
	{
		try
		{
			memRepo.delete(member);
			FizzConstants.log(
					Logger_State.INFO, 
					FizzConstants.Receipt_Messages.DELETED_MEMBER.getMessage(),
					MemberContoller.class);
			return new Receipt(
					FizzConstants.Receipt_Messages.DELETED_MEMBER.getMessage(),
					member);
		}
		catch(Exception e)
		{
			FizzConstants.log(Logger_State.ERROR, e.getMessage(), MemberContoller.class);
			throw new FizzException(FizzConstants.Error_Messages.DELETED_MEMBER_X.getMessage());
		}
	}
	
	@RequestMapping(value="/getProfile",method=RequestMethod.POST)
	@ResponseBody
	public Receipt getProfile(@RequestBody Member member) throws FizzException
	{
		try
		{
			Profile profile = new Profile(
					memRepo.findById(member.getUsername())
						.get()
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
	  
	
	
}
