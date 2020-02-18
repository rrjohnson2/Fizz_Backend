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
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsware.fizz.constants.FizzConstants;
import com.jsware.fizz.constants.FizzConstants.Logger_State;
import com.jsware.fizz.exceptions.FizzException;
import com.jsware.fizz.model.idea.Idea;
import com.jsware.fizz.model.interactions.Receipt;
import com.jsware.fizz.model.interactions.Ticket;
import com.jsware.fizz.model.retort.Retort;
import com.jsware.fizz.repository.MessageRepository;
import com.jsware.fizz.repository.RetortRepository;

@Controller
public class RetortController {
	
	@Autowired
	private RetortRepository retortRepo;
	
	@Autowired
	private MessageRepository msgRepo;
	
	private ObjectMapper mapper;
	
	
	@Autowired
	public RetortController(ObjectMapper mapper) {
		super();
		this.mapper=mapper;
		this.mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
	}
	
	@RequestMapping(value="/deleteRetort",method=RequestMethod.POST)
	@ResponseBody
	public Receipt deleteRetort(@RequestBody Ticket ticket) throws FizzException
	{
		try
		{
			Retort retort = validateAccess(
					mapper.readValue(mapper.writeValueAsString(ticket.getData()),Retort.class),
					ticket.getCustomer());
			retortRepo.delete(retort);
			
			FizzConstants.log(
					Logger_State.INFO, 
					FizzConstants.Receipt_Messages.DELETED_RETORT.getMessage(),
					IdeaController.class);
			return new Receipt(
					FizzConstants.Receipt_Messages.DELETED_RETORT.getMessage(),
					null);
		}
		catch(Exception e)
		{
			FizzConstants.log(Logger_State.ERROR, e.getMessage(), IdeaController.class);
			throw new FizzException(FizzConstants.Error_Messages.DELETED_RETORT_X.getMessage());
		}
	}
	
	@RequestMapping(value="/updateRetort",method=RequestMethod.POST)
	@ResponseBody
	public Receipt upgateIdea(@RequestBody Ticket ticket) throws FizzException
	{
		try
		{
			Retort retort = mapper.readValue(mapper.writeValueAsString(ticket.getData()),Retort.class);
			Retort RETORT = validateAccess(
					retort,
					ticket.getCustomer());
			
			RETORT.setContent(retort.getContent());
			
			retortRepo.save(RETORT);
			
			FizzConstants.log(
					Logger_State.INFO, 
					FizzConstants.Receipt_Messages.UPDATE_RETORT.getMessage(),
					IdeaController.class);
			return new Receipt(
					FizzConstants.Receipt_Messages.UPDATE_RETORT.getMessage(),
					null);
		}
		catch(Exception e)
		{
			FizzConstants.log(Logger_State.ERROR, e.getMessage(), IdeaController.class);
			throw new FizzException(FizzConstants.Error_Messages.UPDATE_RETORT_X.getMessage());
		}
	}


	private Retort validateAccess(Retort retort, String username) throws JsonProcessingException, Exception {
		
		Retort RETORT = retortRepo.findById(retort.getId()).get();
		
		if(!RETORT.getCreator().getUsername().equals(username))
		{
			throw new Exception(FizzConstants.Error_Messages.UNAUTHORIZED_ACTION.getMessage());
		}
		return RETORT;
	}

}
