package com.jsware.fizz.controller;

import java.io.IOException;
import java.util.ArrayList;
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
import com.fasterxml.jackson.databind.JsonMappingException;
import com.jsware.fizz.constants.FizzConstants;
import com.jsware.fizz.constants.FizzConstants.Logger_State;
import com.jsware.fizz.exceptions.FizzException;
import com.jsware.fizz.model.idea.Focus;
import com.jsware.fizz.model.idea.Idea;
import com.jsware.fizz.model.interactions.Receipt;
import com.jsware.fizz.model.interactions.Ticket;
import com.jsware.fizz.model.member.Member;
import com.jsware.fizz.repository.IdeaRepository;

@Controller
public class IdeaController {
	
	@Autowired
	private IdeaRepository ideaRepo;
	
	
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

}
