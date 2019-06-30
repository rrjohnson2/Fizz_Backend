package com.jsware.fizz.testconstants;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.util.Arrays;

import com.jsware.fizz.constants.FizzConstants.Category;
import com.jsware.fizz.model.Member;
import com.jsware.fizz.model.Preference;

public class TestConstants {
	
	/*
	 * Sam Bethe Profile
	 */
	public Preference preference_A = new Preference(Category.MOVIES,10);
	public Preference preference_B = new Preference(Category.TECHNOLOGY,10);
	public Preference preference_C = new Preference(Category.TECHNOLOGY,5);
	public Preference preference_D = new Preference(Category.MOVIES,5);
	public List<Preference> sam_preferences = new ArrayList<Preference>();
	public String sam_password = "sammyboy";
	public Member sam_bethe;
	public MemberJson sam_bethe_json;
	
	public TestConstants() {
		
		SamBethe();
		
	}

	private void SamBethe() {
		sam_preferences.add(preference_A);
		sam_preferences.add(preference_B);
		sam_bethe= new Member("samsquach","Sam", "Bethe", "sBethe@gmail", sam_preferences);
		sam_bethe_json= new MemberJson(sam_bethe,sam_password);
	}

}
