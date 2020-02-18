package com.jsware.fizz.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsware.fizz.constants.FizzConstants;
import com.jsware.fizz.constants.FizzConstants.Logger_State;
import com.jsware.fizz.exceptions.FizzException;
import com.jsware.fizz.model.idea.Focus;
import com.jsware.fizz.model.idea.Idea;
import com.jsware.fizz.model.interactions.Receipt;
import com.jsware.fizz.model.interactions.Ticket;
import com.jsware.fizz.model.member.Member;
import com.jsware.fizz.repository.FocusRepository;
import com.jsware.fizz.repository.IdeaRepository;
import com.jsware.fizz.repository.RatingRepository;

@Controller
public class IdeaController {
	
	@Autowired
	private IdeaRepository ideaRepo;
	
	@Autowired
	private FocusRepository focusRepo;
	
	@Autowired
	private RatingRepository ratingRepository;
	
	private ObjectMapper mapper;
	
	
	
	
	




	@Autowired
	public IdeaController(ObjectMapper mapper) {
		super();
		this.mapper=mapper;
		this.mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
	}
	
	
	@RequestMapping(value="/getAllIdeas", method=RequestMethod.GET)
	@ResponseBody
	public Receipt createIdea() throws JsonParseException, JsonMappingException, JsonProcessingException, IOException, FizzException
	{
		
		try {
			
			List<Idea> ideas = new ArrayList<Idea>();
			
			Iterable<Idea> ideas_from_datbase = ideaRepo.findAll();
			
			Iterator<Idea> ideas_iterator = ideas_from_datbase.iterator();
			
			while(ideas_iterator.hasNext())
			{
				ideas.add(ideas_iterator.next());
			}
					
			
			FizzConstants.log(
					Logger_State.INFO, 
					FizzConstants.Receipt_Messages.CREATED_IDEA.getMessage(),
					MemberContoller.class);
			return new Receipt(
					FizzConstants.Receipt_Messages.CREATED_IDEA.getMessage(),
					ideas);
			
		} catch (Exception e) {
			FizzConstants.log(Logger_State.ERROR, e.getMessage(), TicketController.class);
			throw new FizzException(FizzConstants.Error_Messages.CREATED_IDEA_X.getMessage());
		}
	}
	
	@RequestMapping(value="/deleteIdea",method=RequestMethod.POST)
	@ResponseBody
	public Receipt deleteIdea(@RequestBody Ticket ticket) throws FizzException
	{
		try
		{
			
			Idea idea = validateAccess(
					mapper.readValue(mapper.writeValueAsString(ticket.getData()),Idea.class),
					ticket.getCustomer());
			
			ideaRepo.delete(idea);
			
			FizzConstants.log(
					Logger_State.INFO, 
					FizzConstants.Receipt_Messages.DELETED_IDEA.getMessage(),
					IdeaController.class);
			return new Receipt(
					FizzConstants.Receipt_Messages.DELETED_IDEA.getMessage(),
					null);
		}
		catch(Exception e)
		{
			FizzConstants.log(Logger_State.ERROR, e.getMessage(), IdeaController.class);
			throw new FizzException(FizzConstants.Error_Messages.DELETED_IDEA_X.getMessage());
		}
	}
	
	@RequestMapping(value="/updateIdea",method=RequestMethod.POST)
	@ResponseBody
	public Receipt upgateIdea(@RequestBody Ticket ticket) throws FizzException
	{
		try
		{
			Idea idea = mapper.readValue(mapper.writeValueAsString(ticket.getData()),Idea.class);
			Idea IDEA = validateAccess(
					idea,
					ticket.getCustomer());
			
			IDEA.update(idea);
			ideaRepo.save(IDEA);
			
			FizzConstants.log(
					Logger_State.INFO, 
					FizzConstants.Receipt_Messages.UPDATE_IDEA.getMessage(),
					IdeaController.class);
			return new Receipt(
					FizzConstants.Receipt_Messages.UPDATE_IDEA.getMessage(),
					null);
		}
		catch(Exception e)
		{
			FizzConstants.log(Logger_State.ERROR, e.getMessage(), IdeaController.class);
			throw new FizzException(FizzConstants.Error_Messages.UPDATE_IDEA_X.getMessage());
		}
	}
	
	
	
	



	private Idea validateAccess(Idea idea, String username) throws JsonProcessingException, Exception {
		
		Idea IDEA = ideaRepo.findById(idea.getId()).get();
		
		if(!IDEA.getCreator().getUsername().equals(username))
		{
			throw new Exception(FizzConstants.Error_Messages.UNAUTHORIZED_ACTION.getMessage());
		}
		return IDEA;
	}

}
