package com.jsware.fizz.controller;

import java.io.IOException;
import java.util.ArrayList;

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
import com.jsware.fizz.repository.FocusRepository;
import com.jsware.fizz.repository.IdeaRepository;
import com.jsware.fizz.repository.MemberRepository;
import com.jsware.fizz.repository.RatingRepository;

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
	private ObjectMapper mapper;
	
	
	
	
	





	@RequestMapping(value="/createIdea", method=RequestMethod.POST)
	@ResponseBody
	public Receipt createIdea(@RequestBody Idea idea) throws JsonParseException, JsonMappingException, JsonProcessingException, IOException, FizzException
	{
		
		try {
			
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
	

}
