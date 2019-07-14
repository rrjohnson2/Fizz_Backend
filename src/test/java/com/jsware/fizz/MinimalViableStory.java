package com.jsware.fizz;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.transaction.Transactional;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.DisabledIf;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsware.fizz.constants.FizzConstants;
import com.jsware.fizz.model.idea.Idea;
import com.jsware.fizz.model.interactions.Receipt;
import com.jsware.fizz.model.interactions.Ticket;
import com.jsware.fizz.model.member.Member;
import com.jsware.fizz.model.member.Profile;
import com.jsware.fizz.model.retort.Retort;
import com.jsware.fizz.testconstants.MemberJson;
import com.jsware.fizz.testconstants.TestConstants;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MinimalViableStory {
	
	/**
	 * Sammy Bethe signups with Fizz
	 * He creates an full profile
	 * He creates an idea about sufboards for cats
	 * and David Reed retorts that the idea is good
	 * Sam thanks him 
	 */
	
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper mapper;
	
	private static TestConstants ts;
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ts = new TestConstants();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	


	@Test
	public void AcreateSam() throws Exception {
		
		createMember(ts.sam_bethe_json);
		Profile results = getProfile(ts.sam_bethe.getUsername());
			
		assertTrue(!results.getPreferences().isEmpty());
	}
	
	@Test
	public void BcreateSamIdea() throws Exception
	{
		String ticket_json = mapper.writeValueAsString(ts.sam_idea_ticket);
		
		ResultActions resaction = this.mockMvc.perform(
				post("/createIdea")
					.contentType(MediaType.APPLICATION_JSON)
					.content(ticket_json));
		MvcResult results = resaction
			.andExpect(status().isOk())
			.andReturn();
		
		Receipt receipt= mapper.readValue(
				results.getResponse().
					getContentAsString(),
				Receipt.class);
		
		ts.sam_idea= mapper.readValue(
				mapper.writeValueAsString(receipt.getData()),
				Idea.class);
		
		assertNotNull(receipt);
	}
	
	@Test
	public void CretortDavid() throws Exception
	{
		ts.DavidReed();
		createMember(ts.david_reed_json);
		String retortJson = mapper.writeValueAsString(ts.david_retort_ticket);
		
		ResultActions resaction = this.mockMvc.perform(
				post("/retortIdea")
					.contentType(MediaType.APPLICATION_JSON)
					.content(retortJson));
		MvcResult results = resaction
			.andDo(print())
			.andExpect(status().isOk())
			.andReturn();
		
		Receipt receipt= mapper.readValue(
				results.getResponse().
					getContentAsString(),
				Receipt.class);
		
		ts.david_retort= mapper.readValue(
				mapper.writeValueAsString(receipt.getData()),
				Retort.class);
		
		
		assertNotNull(receipt);
	}
	
	@Test
	public void DrateDavid() throws JsonParseException, JsonMappingException, JsonProcessingException, UnsupportedEncodingException, IOException, Exception {
		
		String rateJson = mapper.writeValueAsString(ts.david_rate_ticket);
		
		ResultActions resaction = this.mockMvc.perform(
				post("/rateIdea")
					.contentType(MediaType.APPLICATION_JSON)
					.content(rateJson));
		MvcResult results = resaction
			.andDo(print())
			.andExpect(status().isOk())
			.andReturn();
		
		Receipt receipt= mapper.readValue(
				results.getResponse().
					getContentAsString(),
				Receipt.class);
		
		assertNotNull(receipt);
		
	}
	
	@Test
	public void ErateDavid() throws JsonParseException, JsonMappingException, JsonProcessingException, UnsupportedEncodingException, IOException, Exception {
		
		
		String rateJson = mapper.writeValueAsString(ts.david_rate_ticket);
		
		ResultActions resaction = this.mockMvc.perform(
				post("/rateIdea")
					.contentType(MediaType.APPLICATION_JSON)
					.content(rateJson));
		MvcResult results = resaction
			.andDo(print())
			.andExpect(status().isOk())
			.andReturn();
		
		Receipt receipt= mapper.readValue(
				results.getResponse().
					getContentAsString(),
				Receipt.class);
		
		assertNotNull(receipt);
		
	}
	
	@Test
	public void FsamReply() throws JsonParseException, JsonMappingException, JsonProcessingException, UnsupportedEncodingException, IOException, Exception {
		
		ts.SamReply();
		String replyJson = mapper.writeValueAsString(ts.sam_message_ticket);
		
		ResultActions resaction = this.mockMvc.perform(
				post("/commentRetort")
					.contentType(MediaType.APPLICATION_JSON)
					.content(replyJson));
		MvcResult results = resaction
			.andDo(print())
			.andExpect(status().isOk())
			.andReturn();
		
		Receipt receipt= mapper.readValue(
				results.getResponse().
					getContentAsString(),
				Receipt.class);
		
		assertNotNull(receipt);
		
	}
	
	@Test
	public void GallIdeas() throws JsonParseException, JsonMappingException, JsonProcessingException, UnsupportedEncodingException, IOException, Exception {
				
		ResultActions resaction = this.mockMvc.perform(get("/getAllIdeas"));
		MvcResult results = resaction
			.andDo(print())
			.andExpect(status().isOk())
			.andReturn();
		
		Receipt receipt= mapper.readValue(
				results.getResponse().
					getContentAsString(),
				Receipt.class);
		
		assertNotNull(receipt);
		
	}
	
	private void createMember(MemberJson mj) throws JsonProcessingException, Exception, IOException, JsonParseException,
			JsonMappingException, UnsupportedEncodingException {
		String member_json = mapper.writeValueAsString(mj);
		ResultActions ra = this.mockMvc
				.perform(post("/createMember").contentType(MediaType.APPLICATION_JSON).content(member_json));

		MvcResult mvcResults = ra
				.andExpect(status().isOk())
				.andReturn();

		Receipt confirmed = mapper.readValue(mvcResults.getResponse().getContentAsString(), Receipt.class);
		assertNotNull(confirmed);
		Member member = mapper.readValue(mapper.writeValueAsString(confirmed.getData()), Member.class);
	}

	
	private Profile getProfile(String username) throws JsonProcessingException, Exception, IOException, JsonParseException,
			JsonMappingException, UnsupportedEncodingException {
		ResultActions ra = this.mockMvc.perform( 
				post("/getProfile")
					.contentType(MediaType.APPLICATION_JSON)
					.param("username", username));
		
		 MvcResult mvcResults = ra
			.andExpect(status().isOk())
			.andReturn();
		 
		 Receipt confirmed = mapper.readValue(
				 mvcResults.getResponse().getContentAsString(),
				 Receipt.class);
		 
		 Profile profile = mapper.readValue(
				 			mapper.writeValueAsString(confirmed.getData()),
				 			Profile.class);
		 
		 return profile;
		 
		 
	}

}
