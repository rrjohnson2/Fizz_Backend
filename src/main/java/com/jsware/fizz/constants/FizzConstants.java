package com.jsware.fizz.constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

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
import com.jsware.fizz.model.rating.Rating;
import com.jsware.fizz.model.retort.Message;
import com.jsware.fizz.model.retort.Retort;
import com.jsware.fizz.repository.IdeaRepository;
import com.jsware.fizz.repository.MemberRepository;

@Controller
public class FizzConstants {
	
	private static  Logger logger;
	
	private static HashMap<String,Integer> activeClients = new HashMap<>();
	
	private static List<Member> all_members = new ArrayList<>();
	private static MemberRepository memberRepo;
	
	private static IdeaRepository ideaRepo;
	
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
		Bad,
		Poor,
		So_So,
		Fair,
		Good
	}
	
	public static enum Category{
		
		TECHNOLOGY,
		MOVIES,
		ARTS,
		PHILOSOPHY,
		RELIGION,
		SCIENCE,
		POLITICS,
		FOOD,
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
		PARTIES_NOTIFIED("NOTIFIED REQUESTED PARTIES"), 
		IDEA_RETURN("IDEAS RETYRNED TO USER"), 
		DELETED_IDEA("DELETED IDEA SUCCESSFUL"),
		UPDATE_IDEA("IDEA UPDATED"), 
		UPDATE_RETORT("RETORT UPDATED"), DELETED_RETORT("DELETED RETORT"), DELETED_MESSAGE("MESSAGE DELETED"), DELETE_RATING("DELETED RATING");
		
		
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
		PARTIES_NOTIFIED_X("COULD NOT SEND NOTIFICATION"), 
		DELETED_IDEA_X("DELETION FAILED IDEA"),
		UNAUTHORIZED_ACTION("USER IS NOT AUTHORIZED TO TAKE THIS ACTION"),
		UPDATE_IDEA_X("UPDATE IDEA FAILED"), UPDATE_RETORT_X("UPDATE FAILED RETORT"), DELETED_RETORT_X("RETORT DELETION FAILED"), DELETED_MESSAGE_X("MESSAGE DELETE FAILED"), DELETE_RATING_X("DELETED RATING");
		
		
		
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
	
	@RequestMapping(value="/trending",method=RequestMethod.GET)
	@ResponseBody
	public List<Idea> trending()
	{
			List<Idea> res = ideaRepo.trending(365);
			
			if(res.isEmpty()) return res;
			Collections.sort(res);
			
			int size = res.size() >= 3? 2 : res.size()-1 ;
			return res.subList(0, size);
	}
	
	@RequestMapping(value="/VoteTypes",method=RequestMethod.GET)
	@ResponseBody
	public List<String> VoteTypes()
	{
		List<String> ratings=new ArrayList<String>();
		
		for(Vote_Type vote : Vote_Type.values())
		{
			ratings.add(vote.toString());
		}
		return ratings;
	}
	
	@RequestMapping(value="/clientActivated",method=RequestMethod.POST)
	@ResponseBody
	public void clientActivated(@RequestBody Client client )
	{
		activeClients.put(client.getUsername(), client.getKey());
		checkNotifications(client.getUsername(),client.getKey());
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
		case RATING:
			 affected_usernames = membersWhoFollowFocus((Rating)data,creator);
			break;
		case RETORT:
			affected_usernames = membersWhoFollowFocus((Retort)data,creator);
			break;
		case COMMENT:
			affected_usernames = membersWhoFollowFocus((Message)data,creator);
			break;
			

		default:
			break;
			

		}
		for (String username : affected_usernames) {
			if(activeClients.containsKey(username))
			{
				Notice notice = new Notice(username, data,activeClients.get(username),action);
				active_notifications.add(notice);
			}
			pending_notifications.add(new Notice(username, data,-1,action));

		}
		if(!active_notifications.isEmpty())
		{
			notifyRealTimeServer(active_notifications);
			log(Logger_State.INFO,
					Receipt_Messages.PARTIES_NOTIFIED.getMessage(),
					FizzConstants.class);
		}

	}
	
	private static List<String> membersWhoFollowFocus(Message data, String creator) {
		List<String> usernames = new ArrayList<String>();
		
		String idea_creator = data.getRetort().getIdea().getCreator().getUsername();
		String retort_creator = data.getRetort().getCreator().getUsername();
		
		for (Message msg : data.getRetort().getMessages()) {
			String username = msg.getCreator().getUsername();
			if(!username.equals(creator) && !username.equals(idea_creator) && !username.equals(retort_creator))
			{
				usernames.add(username);
			}
		}
		
		if(!creator.equals(idea_creator))usernames.add(idea_creator);
		
		return usernames;
	}


	private static List<String> membersWhoFollowFocus(Retort data, String creator) {
		List<String> usernames = new ArrayList<String>();
		String idea_creator = data.getIdea().getCreator().getUsername();
		if(!creator.equals(idea_creator))usernames.add(idea_creator);
		return usernames;
	}


	private static List<String> membersWhoFollowFocus(Rating data, String creator) {
		List<String> usernames = new ArrayList<String>();
		String idea_creator = data.getIdea().getCreator().getUsername();
		if(!creator.equals(idea_creator))usernames.add(idea_creator);
		return usernames;
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
		
		for (Focus focus : data.getFocuses()) {
			
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
	
	public static void checkNotifications(String username, int socket_key)
	{
		List<Notice> notifications = new ArrayList<>();
		for (Notice notice : pending_notifications) {
			if(notice.username.equals(username))
			{
				notice.socket_key=socket_key;
				notifications.add(notice);
			}
		}
		if(!notifications.isEmpty())notifyRealTimeServer(notifications);
	}
	
	
	@Autowired
	public FizzConstants(MemberRepository memberRepository, ObjectMapper mapper,IdeaRepository iR)
	{
		memberRepo = memberRepository;
		ideaRepo = iR;
		this.mapper = mapper;
		 Iterator<Member> it = memberRepo.findAll().iterator();
		 
		 while (it.hasNext()) {
			all_members.add(it.next());	
		}
		 
	}
	
	
	

}
