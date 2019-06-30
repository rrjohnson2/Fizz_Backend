package com.jsware.fizz.model.idea;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@SequenceGenerator(name="rating_seq", initialValue=1)
public class Rating {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="rating_seq")
	private long id;

	private double score;
	
	private int likes;
	
	private int dislikes;
	
	@OneToOne()
	@JsonIgnore
	private Idea idea;
	
	
	public Rating(Idea idea, double score, int likes, int dislikes) {
		// TODO Auto-generated constructor stub
		this.score=score;
		this.likes=likes;
		this.dislikes=dislikes;
		this.idea=idea;
	}


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


	public void setId(long id) {
		this.id = id;
	}


	public double getScore() {
		return score;
	}


	public void setScore(double score) {
		this.score = score;
	}


	public int getLikes() {
		return likes;
	}


	public void setLikes(int likes) {
		this.likes = likes;
	}


	public int getDislikes() {
		return dislikes;
	}


	public void setDislikes(int dislikes) {
		this.dislikes = dislikes;
	}


	public Idea getIdea() {
		return idea;
	}


	public void setIdea(Idea idea) {
		this.idea = idea;
	}


	public void init(Idea idea) {
		this.idea= idea;
		this.score=0.0;
		this.likes=0;
		this.dislikes=0;
		
	}
	
	
	
	
}
