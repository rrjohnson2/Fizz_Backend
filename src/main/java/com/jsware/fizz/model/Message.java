package com.jsware.fizz.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name="msg_seq", initialValue=1)
public class Message {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="msg_seq")
	private long id;
	
	@Lob
	private String content;
	
	@ManyToOne
	private Retort retort;
	
	@ManyToOne
	private Member creator;

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

}
