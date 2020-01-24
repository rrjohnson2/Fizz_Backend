package com.jsware.fizz.model.idea;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jsware.fizz.constants.FizzConstants.Category;

@Entity
@SequenceGenerator(name="foc_seq", initialValue=1)
public class Focus {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="foc_seq")
	private long id;
	
	@ManyToOne()
	@JsonIgnore
    //@OnDelete(action = OnDeleteAction.CASCADE)
	private Idea idea;

	@Enumerated(EnumType.STRING)
	private Category category;
	

	public Focus(Category category) {
		super();
		this.category = category;
	}
	public Focus() {}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
	public Idea getIdea() {
		return idea;
	}

	public void setIdea(Idea idea) {
		this.idea = idea;
	}
	

}
