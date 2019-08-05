package com.jsware.fizz.constants;




import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FizzConstants {
	
	private static  Logger logger;
	
	public static final int  suggestCount = 5;
	
	public static enum Logger_State
	{
		ERROR,
		INFO
	}
	
	public static enum Vote_Type
	{
		UP,
		DOWN
	}
	
	public static enum Category{
		
		TECHNOLOGY,
		MOVIES,
		MISC;
		
	}
	


	
	public static enum Receipt_Messages 
	{
		CREATED_MEMBER("MEMBER WAS SUCCESSFULLY ADDED"),
		DELETED_MEMBER("MEMBER WAS SUCCESSFULLY DELETED"), 
		PROFILE_OBTAINED("PROFILE RETURNED"), 
		CREATED_IDEA("IDEA SUCCESSFULLY CREATED"), 
		CREATED_RETORT("RETORT SUCCESSFUL"), 
		RATED_IDEA("IDEA RATED SUCCESSFULLY"), 
		COMMENTED("COMMENT SUCCESSFULL"),
		LOGIN_SUCCESSFUL("LOGIN SUCCESSFUL"),
		UPDATE_SUCCESSFUL("UPDATED PROFILE");
		
		private String message;
		
		private Receipt_Messages(String message)
		{
			this.message=message;
		}
		
		public String getMessage()
		{
			return this.message;
		}
		
	}
	
	public static enum Error_Messages 
	{
		CREATED_MEMBER_X("CREATION FAILED"),
		MEMEBER_EXIST_X("USERNAME ARLEADY TAKEN"),
		DELETED_MEMBER_X("DELETE FAILED"),
		PROFILE_X("PROFILE COULDNOT BE GATHERED"),
		CREATED_IDEA_X("IDEA CREATION FAILED"), 
		CREATED_RETORT_X("RETORT FAILED"), 
		RATING_X("RATING FAILED"),
		COMMENTED_X("COMMENT FAILED"),
		LOGIN_X("LOGIN FAILED"),
		UPDATE_X("UPDATE FAILED ");
		
		private String message;
		
		private Error_Messages(String message)
		{
			this.message=message;
		}
		
		public String getMessage()
		{
			return this.message;
		}
		
	}
	
	
	
	public static void log(Logger_State state,String msg,Class<?> classObj) {
		logger = LoggerFactory.getLogger(classObj);
		
		switch (state) {
		case INFO:
			logger.info(msg);
			break;

		case ERROR:
			logger.error(msg);
			break;
		}
		
	}
	
	
	@RequestMapping(value="/getCategories",method=RequestMethod.GET)
	@ResponseBody
	public List<String> getCategories()
	{
		List<String> categories=new ArrayList<String>();
		
		for(Category cat : Category.values())
		{
			categories.add(cat.toString());
		}
		return categories;
	}

}
