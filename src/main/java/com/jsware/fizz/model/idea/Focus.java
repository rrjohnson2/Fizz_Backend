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

	public Focus setIdea(Idea idea) {
		this.idea = idea;
		return this;
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
		Focus other = (Focus) obj;
		if (id != other.id)
			return false;
		return true;
	}
	

	
}
