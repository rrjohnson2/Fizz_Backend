package com.jsware.fizz.constants;





import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsware.fizz.model.idea.Focus;
import com.jsware.fizz.model.idea.Idea;
import com.jsware.fizz.model.member.Member;
import com.jsware.fizz.model.member.Preference;
import com.jsware.fizz.model.network.Client;
import com.jsware.fizz.model.network.Notice;
import com.jsware.fizz.repository.MemberRepository;

@Controller
public class FizzConstants {
	
	private static  Logger logger;
	
	private static HashMap<String,Integer> activeClients = new HashMap<>();
	
	private static List<Member> all_members = new ArrayList<>();
	private static MemberRepository memberRepo;
	
	private static List<Notice> pending_notifications= new ArrayList<>();

	private static String realTimeServerURL = "http://localhost:3000/";
			
	private final static CloseableHttpClient http_client = HttpClients.createDefault();
	public static final int  suggestCount = 5;
	
	private static   ObjectMapper mapper;
	
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
	
	public static enum Notification_Network_Actions{
		FOCUS,
		RETORT,
		COMMENT,
		RATING,
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
		UPDATE_SUCCESSFUL("UPDATED PROFILE"),
		CLIENT_ADDED("CLIENT JOINED REAL TIME SERVER"),
		CLIENT_REMOVED("CLIENT REMOVED REAL TIME SERVER"),
		PARTIES_NOTIFIED("NOTIFIED REQUESTED PARTIES");;
		
		
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
		UPDATE_X("UPDATE FAILED "),
		PARTIES_NOTIFIED_X("COULD NOT SEND NOTIFICATION");
		
		
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
	
	@RequestMapping(value="/clientActivated",method=RequestMethod.POST)
	@ResponseBody
	public void clientActivated(@RequestBody Client client )
	{
		activeClients.put(client.getUsername(), client.getKey());
		
		log(Logger_State.INFO,
				Receipt_Messages.CLIENT_ADDED.getMessage(),
				FizzConstants.class);
	} 
	
	@RequestMapping(value="/clientDeactivated",method=RequestMethod.POST)
	@ResponseBody
	public static void clientDeactivated(@RequestBody Client client )
	{
		activeClients.remove(client.getUsername());
		log(Logger_State.INFO,
				Receipt_Messages.CLIENT_REMOVED.getMessage(),
				FizzConstants.class);
	} 
	
	public static void notifyParties(Object data, Notification_Network_Actions action,String creator)
	{
		List<String> affected_usernames = null;
		 List<Notice> active_notifications= new ArrayList<>();
		switch (action) {
		case FOCUS:
			 affected_usernames = membersWhoFollowFocus((Idea)data,creator);
			break;

		default:
			break;
			

		}
		for (String username : affected_usernames) {
			Notice notice = new Notice(username, data);
			if(activeClients.containsKey(username))
			{
				active_notifications.add(notice);
			}
			else {
				pending_notifications.add(notice);
			}
		}
		if(!active_notifications.isEmpty())
		{
			notifyRealTimeServer(active_notifications);
			log(Logger_State.INFO,
					Receipt_Messages.PARTIES_NOTIFIED.getMessage(),
					FizzConstants.class);
		}

	}
	
	private static void notifyRealTimeServer(List<Notice> active_notifications) {
       new Thread(new Runnable() {
		
		@Override
		public void run() {
			 try {
		        	HttpPost post = new HttpPost(realTimeServerURL +"notifications");
		        	
		            StringBuilder json = new StringBuilder();
		            json.append("{");
		            json.append("\"notifications\":" + mapper.writeValueAsString(active_notifications));
		            json.append("}");
		            
		            post.setEntity(new StringEntity(json.toString()));
		            post.setHeader("Accept", "application/json");
		            post.setHeader("Content-type", "application/json");

		            http_client.execute(post);
		           } catch (Exception e) {
		   			log(Logger_State.ERROR,
							Error_Messages.PARTIES_NOTIFIED_X.getMessage(),
							FizzConstants.class);
				}
		}
       			}).start();
	}


	private static List<String> membersWhoFollowFocus(Idea data, String creator) {
		List<String> usernames = new ArrayList<String>();
		
		for (Focus focus : data.getFocus()) {
			
			for (Member member : all_members) {
			
				if(!creator.equals(member.getUsername()))
				{
					for (Preference preference : memberRepo.findById(member.getId()).get().getPreferences()) {
						if(preference.getCategory().equals(focus.getCategory()))
						{
							usernames.add(member.getUsername());
						}
				}
			} 
		}
	}
		
		return usernames;
	}


	public static  void addMember(Member member)
	{
		all_members.add(member);
	}
	
	public static void checkNotifications(String username)
	{
		List<Notice> notifications = new ArrayList<>();
		for (Notice notice : pending_notifications) {
			if(notice.username.equals(username))
			{
				notifications.add(notice);
			}
		}
		if(!notifications.isEmpty())notifyRealTimeServer(notifications);
	}
	
	
	@Autowired
	public FizzConstants(MemberRepository memberRepository, ObjectMapper mapper)
	{
		memberRepo = memberRepository;
		this.mapper = mapper;
		 Iterator<Member> it = memberRepo.findAll().iterator();
		 
		 while (it.hasNext()) {
			all_members.add(it.next());	
		}
		 
	}
	

}
