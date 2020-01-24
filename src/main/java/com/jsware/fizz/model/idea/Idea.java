package com.jsware.fizz.model.idea;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import com.jsware.fizz.constants.FizzConstants.Vote_Type;
import com.jsware.fizz.model.member.Member;
import com.jsware.fizz.model.rating.Rating;
import com.jsware.fizz.model.retort.Retort;

@Entity
@SequenceGenerator(name="idea_seq", initialValue=1)
public class Idea {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="idea_seq")
	private long id;
	
	@ManyToOne()
	@JoinColumn(name="creator")
	private Member creator;
	
	private Date timestamp = new Date();
	
	@OneToMany(mappedBy="idea",cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Focus> focuses = new ArrayList<Focus>();
	
	private String title, description;
	
	@OneToMany(mappedBy="idea",cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Retort> retorts =  new ArrayList<Retort>();
	
	@OneToMany(mappedBy="idea",cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Rating> ratings = new ArrayList<Rating>();
	
	public Idea() {}
	
	public Idea(String title, String description) {
		super();
		this.title = title;
		this.description = description;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Idea other = (Idea) obj;
		if (id != other.id)
			return false;
		return true;
	}
	

	public List<Focus> getFocuses() {
		return focuses;
	}

	public void setFocuses(List<Focus> focus) {
		this.focuses = focus;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Member getCreator() {
		return creator;
	}

	public void setCreator(Member creator) {
		this.creator = creator;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Retort> getRetorts() {
		return retorts;
	}

	public void setRetorts(List<Retort> resorts) {
		this.retorts = resorts;
	}

	public List<Rating> getRatings() {
		return ratings;
	}
	
	public int getUpVotes()
	{
		return countVotes(Vote_Type.UP);
	}
	
	public int getDownVotes()
	{
		return countVotes(Vote_Type.DOWN);	
	}

	private int countVotes(Vote_Type vote)
	{
		int count = 0;
		
		for(Rating rate :ratings)
		{
			if(rate.getVote()==vote)
			{
				count++;
			}
		}
		return count;
	}

	public List<Focus> update(Idea idea) {
		
		/*
		 *  This clunky method is a result of this.fouces not being able to be set by normal means
		 *  
		 *  meaning to update a list relationship on the parent the list items have to be deleted and added back or jpa bitches 
		 */
		
		List<Focus> unused_focus = new ArrayList<Focus>();
		
		for (Focus focus : focuses) {
		
			if(!idea.focuses.contains(focus))
			{
				unused_focus.add(focus);
			}
			
		}
		
		for (Focus focus : idea.focuses) {
			if(!this.focuses.contains(focus)) {
				focus.setIdea(this);
				focuses.add(focus);
			}
		}
		
		for (Focus focus : unused_focus) {
			this.focuses.remove(focus);
		}
		
		
		description = idea.description;
		title=idea.title;
		
		return unused_focus;
		
		
		
	}

	
	
}
