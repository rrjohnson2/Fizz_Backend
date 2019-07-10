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
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsware.fizz.constants.FizzConstants;
import com.jsware.fizz.constants.FizzConstants.Logger_State;
import com.jsware.fizz.exceptions.FizzException;
import com.jsware.fizz.model.idea.Focus;
import com.jsware.fizz.model.idea.Idea;
import com.jsware.fizz.model.idea.Rating;
import com.jsware.fizz.model.interactions.Receipt;
import com.jsware.fizz.model.interactions.Ticket;
import com.jsware.fizz.model.member.Member;
import com.jsware.fizz.model.retort.Retort;
import com.jsware.fizz.repository.FocusRepository;
import com.jsware.fizz.repository.IdeaRepository;
import com.jsware.fizz.repository.MemberRepository;
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
	private ObjectMapper mapper;
	
	
	
	
	





	@RequestMapping(value="/createIdea", method=RequestMethod.POST)
	@ResponseBody
	public Receipt createIdea(@RequestBody Ticket ticket) throws JsonParseException, JsonMappingException, JsonProcessingException, IOException, FizzException
	{
		
		try {
			Member creator = memRepo.findById(ticket.getCustomer()).get();
			
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
			
			Rating rating = ratRepo.save(new Rating(idea,0.0, 0, 0));
			
			idea.setRating(rating);
			
		
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
			Member creator = memRepo.findById(ticket.getCustomer()).get();
			
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
	

}
