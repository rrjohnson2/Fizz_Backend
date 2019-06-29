package com.jsware.fizz.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name="retort_seq", initialValue=1)
public class Retort {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="retort_seq")
	private long id;
	
	@ManyToOne()
	@JoinColumn(name="idea")
	private Idea idea;
	
	@ManyToOne()
	@JoinColumn(name="creator")
	private Member creator;
	
	@Lob
	private String content;
	
	@OneToMany(mappedBy="retort")
	private List<Message> messages;

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
		Retort other = (Retort) obj;
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
	
	
}
