package com.jsware.fizz;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsware.fizz.constants.FizzConstants;
import com.jsware.fizz.model.Member;
import com.jsware.fizz.testconstants.TestConstants;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
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
	
	private TestConstants ts = new TestConstants();
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
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
	@Rollback(false)
	public void createMember() throws Exception {
		
		String member_json = mapper.writeValueAsString(ts.sam_bethe);
		ResultActions ra = this.mockMvc.perform( 
				post("/createMember")
					.contentType(MediaType.APPLICATION_JSON)
					.content(member_json ));
		
		 MvcResult mvcResults = ra
			.andDo(print())
			.andExpect(status().isOk())
			.andReturn();
		 
		 Member confirmed = mapper.readValue(
				 mvcResults.getResponse().getContentAsString(),
				 Member.class);
		 assertNotNull(confirmed);
		
	}

}
