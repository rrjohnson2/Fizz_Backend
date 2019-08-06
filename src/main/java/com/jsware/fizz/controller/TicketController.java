package com.jsware.fizz.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsware.fizz.constants.FizzConstants;
import com.jsware.fizz.constants.FizzConstants.Logger_State;
import com.jsware.fizz.constants.FizzConstants.Vote_Type;
import com.jsware.fizz.exceptions.FizzException;
import com.jsware.fizz.model.idea.Focus;
import com.jsware.fizz.model.idea.Idea;
import com.jsware.fizz.model.interactions.Receipt;
import com.jsware.fizz.model.interactions.Ticket;
import com.jsware.fizz.model.member.Member;
import com.jsware.fizz.model.rating.Rating;
import com.jsware.fizz.model.retort.Message;
import com.jsware.fizz.model.retort.Retort;
import com.jsware.fizz.repository.FocusRepository;
import com.jsware.fizz.repository.IdeaRepository;
import com.jsware.fizz.repository.MemberRepository;
import com.jsware.fizz.repository.MessageRepository;
import com.jsware.fizz.repository.RatingRepository;
import com.jsware.fizz.repository.RetortRepository;

@Controller
public class TicketController {
	
	@Autowired
	private MemberRepository memRepo;
	
	@Autowired
	private IdeaRepository ideaRepo;
	
	@Autowired
	private FocusRepository focRepo;
	
	@Autowired
	private RatingRepository ratRepo;
	
	@Autowired
	private RetortRepository retRepo;
	
	@Autowired
	private MessageRepository mesRepo;
	
	
	private ObjectMapper mapper;
	
	
	
	
	




	@Autowired
	public TicketController(ObjectMapper mapper) {
		super();
		this.mapper=mapper;
		this.mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
	}

	@RequestMapping(value="/createIdea", method=RequestMethod.POST)
	@ResponseBody
	public Receipt createIdea(@RequestBody Ticket ticket) throws JsonParseException, JsonMappingException, JsonProcessingException, IOException, FizzException
	{
		
		try {
			Member creator = memRepo.findByUsername(ticket.getCustomer());
			
			Idea idea = mapper.readValue(
					mapper.writeValueAsString(ticket.getData()),
					Idea.class);
			
			idea.setCreator(creator);
			
			idea = ideaRepo.save(idea);
			
			for(Focus foc : idea.getFocus())
			{
				foc.setIdea(idea);
				focRepo.save(foc);
			}
							
			FizzConstants.log(
					Logger_State.INFO, 
					FizzConstants.Receipt_Messages.CREATED_IDEA.getMessage(),
					MemberContoller.class);
			return new Receipt(
					FizzConstants.Receipt_Messages.CREATED_IDEA.getMessage(),
					idea);
			
		} catch (Exception e) {
			FizzConstants.log(Logger_State.ERROR, e.getMessage(), TicketController.class);
			throw new FizzException(FizzConstants.Error_Messages.CREATED_IDEA_X.getMessage());
		}
	}
	
	@RequestMapping(value="/retortIdea", method=RequestMethod.POST)
	@ResponseBody
	public Receipt retortIdea(@RequestBody Ticket ticket) throws JsonParseException, JsonMappingException, JsonProcessingException, IOException, FizzException
	{
		
		try {
			Member creator = memRepo.findByUsername(ticket.getCustomer());
			
			HashMap<String, Object> data = mapper.readValue(
					mapper.writeValueAsString(ticket.getData()),
					HashMap.class);
			
			Idea idea = ideaRepo.findById(
					Long.parseLong(
							mapper.writeValueAsString(data.get("idea"))
							))
					.get();
			
			Retort retort = mapper.readValue(
					mapper.writeValueAsString(data.get("retort")),
					Retort.class);
			
			retort.setCreator(creator);
			retort.setIdea(idea);
			
			retort = retRepo.save(retort);
			
			FizzConstants.log(
					Logger_State.INFO, 
					FizzConstants.Receipt_Messages.CREATED_RETORT.getMessage(),
					MemberContoller.class);
			return new Receipt(
					FizzConstants.Receipt_Messages.CREATED_RETORT.getMessage(),
					retort);
			
		} catch (Exception e) {
			FizzConstants.log(Logger_State.ERROR, e.getMessage(), TicketController.class);
			throw new FizzException(FizzConstants.Error_Messages.CREATED_RETORT_X.getMessage());
		}
	}
	
	@RequestMapping(value="/rateIdea", method=RequestMethod.POST)
	@ResponseBody
	public Receipt rateIdea(@RequestBody Ticket ticket) throws JsonParseException, JsonMappingException, JsonProcessingException, IOException, FizzException
	{
		
		try {
			Member creator = memRepo.findByUsername(ticket.getCustomer());
			
			@SuppressWarnings("unchecked")
			HashMap<String, Object> data = mapper.readValue(
					mapper.writeValueAsString(ticket.getData()),
					HashMap.class);
			
			Idea idea = ideaRepo.findById(
					Long.parseLong(
							mapper.writeValueAsString(data.get("idea"))
							))
					.get();
			
			
			Rating rating = creator.hasRatedIdea(idea);
		
			Vote_Type vote  = Vote_Type.valueOf((String) data.get("vote"));
			
			boolean newRating = rating.getVote()==null;
			
			if(newRating)
			{
				rating.setCreator(creator);
				rating.setIdea(idea);
			}

			rating.setVote(vote);
			
			rating = ratRepo.save(rating);
		
			FizzConstants.log(
					Logger_State.INFO, 
					FizzConstants.Receipt_Messages.RATED_IDEA.getMessage(),
					MemberContoller.class);
			return new Receipt(
					FizzConstants.Receipt_Messages.RATED_IDEA.getMessage(),
					rating);
			
		} catch (Exception e) {
			FizzConstants.log(Logger_State.ERROR, e.getMessage(), TicketController.class);
			throw new FizzException(FizzConstants.Error_Messages.RATING_X.getMessage());
		}
	}
	
	
	@RequestMapping(value="/commentRetort", method=RequestMethod.POST)
	@ResponseBody
	public Receipt commentRetort(@RequestBody Ticket ticket) throws JsonParseException, JsonMappingException, JsonProcessingException, IOException, FizzException
	{
		
		try {
			Member creator = memRepo.findByUsername(ticket.getCustomer());
			
			@SuppressWarnings("unchecked")
			HashMap<String, Object> data = mapper.readValue(
					mapper.writeValueAsString(ticket.getData()),
					HashMap.class);
			
			Retort retort = retRepo.findById(
					Long.parseLong(
							mapper.writeValueAsString(data.get("retort"))
							))
					.get();
			Message message = new Message(creator,retort,(String)data.get("comment"));
			
		
			message = mesRepo.save(message);
		
			FizzConstants.log(
					Logger_State.INFO, 
					FizzConstants.Receipt_Messages.COMMENTED.getMessage(),
					MemberContoller.class);
			return new Receipt(
					FizzConstants.Receipt_Messages.COMMENTED.getMessage(),
					message);
			
		} catch (Exception e) {
			FizzConstants.log(Logger_State.ERROR, e.getMessage(), TicketController.class);
			throw new FizzException(FizzConstants.Error_Messages.COMMENTED_X.getMessage());
		}
	}
	

}
