package com.jsware.fizz.constants;




import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

@Controller
public class FizzConstants {
	
	private static  Logger logger;
	
	public static final int  suggestCount = 5;
	
	public static enum Logger_State
	{
		ERROR,
		INFO
	}

	
	public static enum Receipt_Messages 
	{
		CREATED_MEMBER("MEMBER WAS SUCCESSFULLY ADDED"),
		DELETED_MEMBER("MEMBER WAS SUCCESSFULLY DELETED");
		
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
		DELETED_MEMBER_X("DELETE FAILED");
		
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

}
