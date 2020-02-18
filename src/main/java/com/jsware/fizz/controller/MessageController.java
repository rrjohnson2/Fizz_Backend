package com.jsware.fizz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsware.fizz.constants.FizzConstants;
import com.jsware.fizz.constants.FizzConstants.Logger_State;
import com.jsware.fizz.exceptions.FizzException;
import com.jsware.fizz.model.interactions.Receipt;
import com.jsware.fizz.model.interactions.Ticket;
import com.jsware.fizz.model.retort.Message;
import com.jsware.fizz.repository.MessageRepository;
@Controller
public class MessageController {

	
	@Autowired
	private MessageRepository msgRepo;
	
	private ObjectMapper mapper;
	
	
	@Autowired
	public MessageController(ObjectMapper mapper) {
		super();
		this.mapper=mapper;
		this.mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
	}
	
	@RequestMapping(value="/deleteMessage",method=RequestMethod.POST)
	@ResponseBody
	public Receipt deleteMessage(@RequestBody Ticket ticket) throws FizzException
	{
		try
		{
			
			msgRepo.delete(validateAccess(
					mapper.readValue(mapper.writeValueAsString(ticket.getData()),Message.class),
					ticket.getCustomer()));
			
			FizzConstants.log(
					Logger_State.INFO, 
					FizzConstants.Receipt_Messages.DELETED_MESSAGE.getMessage(),
					IdeaController.class);
			return new Receipt(
					FizzConstants.Receipt_Messages.DELETED_MESSAGE.getMessage(),
					null);
		}
		catch(Exception e)
		{
			FizzConstants.log(Logger_State.ERROR, e.getMessage(), IdeaController.class);
			throw new FizzException(FizzConstants.Error_Messages.DELETED_MESSAGE_X.getMessage());
		}
	}
	
	@RequestMapping(value="/updateMessage",method=RequestMethod.POST)
	@ResponseBody
	public Receipt upgateIdea(@RequestBody Ticket ticket) throws FizzException
	{
		try
		{
			Message msg = mapper.readValue(mapper.writeValueAsString(ticket.getData()),Message.class);
			Message MSG = validateAccess(
					msg,
					ticket.getCustomer());
			
			MSG.setContent(msg.getContent());
			
			msgRepo.save(MSG);
			
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


	private Message validateAccess(Message msg, String username) throws JsonProcessingException, Exception {
		
		Message MSG = msgRepo.findById(msg.getId()).get();
		
		if(!MSG.getCreator().getUsername().equals(username))
		{
			throw new Exception(FizzConstants.Error_Messages.UNAUTHORIZED_ACTION.getMessage());
		}
		return MSG;
	}

}

