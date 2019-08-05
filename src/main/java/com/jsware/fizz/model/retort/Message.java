package com.jsware.fizz.model.retort;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jsware.fizz.model.member.Member;

@Entity
@SequenceGenerator(name="msg_seq", initialValue=1)
public class Message {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="msg_seq")
	private long id;
	
	@Lob
	private String content;
	
	@ManyToOne
	@JsonIgnore
	private Retort retort;
	
	@ManyToOne
	private Member creator;
	
	private Date timestamp= new Date();
	
	public Message() {}

	public Message(Member creator, Retort retort, String content) {
		this.content=content;
		this.creator=creator;
		this.retort=retort;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	

}
