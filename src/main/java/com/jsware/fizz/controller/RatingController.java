package com.jsware.fizz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsware.fizz.constants.FizzConstants;
import com.jsware.fizz.constants.FizzConstants.Logger_State;
import com.jsware.fizz.exceptions.FizzException;
import com.jsware.fizz.model.idea.Idea;
import com.jsware.fizz.model.interactions.Receipt;
import com.jsware.fizz.model.interactions.Ticket;
import com.jsware.fizz.model.rating.Rating;
import com.jsware.fizz.repository.RatingRepository;

@Controller
public class RatingController {
	
	@Autowired
	private RatingRepository ratingRepo;
	
	private ObjectMapper mapper;


	@Autowired
	public RatingController(ObjectMapper mapper) {
		super();
		this.mapper=mapper;
		this.mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
	}
	
	
	
	
	@RequestMapping(value="/removeRating",method=RequestMethod.POST)
	@ResponseBody
	public Receipt removeRating(@RequestBody Ticket ticket) throws FizzException
	{
		try
		{
			ratingRepo.delete(
					validateAccess(
							mapper.readValue(mapper.writeValueAsString(ticket.getData()),Rating.class),
							ticket.getCustomer()));
			
			FizzConstants.log(
					Logger_State.INFO, 
					FizzConstants.Receipt_Messages.DELETE_RATING.getMessage(),
					IdeaController.class);
			return new Receipt(
					FizzConstants.Receipt_Messages.DELETE_RATING.getMessage(),
					null);
		}
		catch(Exception e)
		{
			FizzConstants.log(Logger_State.ERROR, e.getMessage(), IdeaController.class);
			throw new FizzException(FizzConstants.Error_Messages.DELETE_RATING_X.getMessage());
		}
	}




	private Rating validateAccess(Rating readValue, String customer) throws Exception {
		Rating RATE = ratingRepo.findById(readValue.getId()).get();
		
		if(!RATE.getCreator().getUsername().equals(customer))
		{
			throw new Exception(FizzConstants.Error_Messages.UNAUTHORIZED_ACTION.getMessage());
		}
		return RATE;
	}

}
