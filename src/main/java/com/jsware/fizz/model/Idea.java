package com.jsware.fizz.model;


import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name="idea_seq", initialValue=1)
public class Idea {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="idea_seq")
	private long id;
	
	@ManyToOne()
	@JoinColumn(name="creator")
	private Member creator;
	
	private Date timestamp;
	
	@OneToOne()
	private Focus focus;
	
	private String title, description;
	
	@OneToMany(mappedBy="idea")
	private List<Retort> resorts;
	
	@OneToOne(mappedBy="idea")
	private Rating rating = new Rating(0.0,0,0);
	
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
	

	public Focus getFocus() {
		return focus;
	}

	public void setFocus(Focus focus) {
		this.focus = focus;
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

	public List<Retort> getResorts() {
		return resorts;
	}

	public void setResorts(List<Retort> resorts) {
		this.resorts = resorts;
	}

	public Rating getRating() {
		return rating;
	}

	public void setRating(Rating rating) {
		this.rating = rating;
	}


	
	
}
