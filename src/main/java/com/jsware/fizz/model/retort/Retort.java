package com.jsware.fizz.model.retort;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jsware.fizz.model.idea.Idea;
import com.jsware.fizz.model.member.Member;

@Entity
@SequenceGenerator(name="retort_seq", initialValue=1)
public class Retort {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="retort_seq")
	private long id;
	
	@ManyToOne()
	@JoinColumn(name="idea")
	@JsonIgnore
	private Idea idea;
	
	@ManyToOne()
	@JoinColumn(name="creator")
	private Member creator;
	
	@Lob
	private String content;
	
	@OneToMany(mappedBy="retort",cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Message> messages = new ArrayList<Message>();
	
	private Date timestamp = new Date();
	
	public Retort() {
		
	}
	
	public Retort(String content) {
		super();
		this.content = content;
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
	
}
