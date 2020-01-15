package com.jsware.fizz.model.rating;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jsware.fizz.constants.FizzConstants.Vote_Type;
import com.jsware.fizz.model.idea.Idea;
import com.jsware.fizz.model.member.Member;

@Entity
@SequenceGenerator(name="rating_seq", initialValue=1)
public class Rating {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="rating_seq")
	private long id;
	
	@Enumerated(EnumType.STRING)
	private Vote_Type vote;
	
	@ManyToOne
	@JsonIgnore
	private Member creator;
	
	@ManyToOne
	@JsonIgnore
	private Idea idea;
	
	public Rating() {
		// TODO Auto-generated constructor stub
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
		Rating other = (Rating) obj;
		if (id != other.id)
			return false;
		return true;
	}


	public long getId() {
		return id;
	}

	public Idea getIdea() {
		return idea;
	}


	public void setIdea(Idea idea) {
		this.idea = idea;
	}

	public Member getCreator() {
		return creator;
	}


	public void setCreator(Member creator) {
		this.creator = creator;
	}

	public Vote_Type getVote() {
		return vote;
	}

	public void setVote(Vote_Type vote) {
		this.vote = vote;
	}
	
	
	
}
