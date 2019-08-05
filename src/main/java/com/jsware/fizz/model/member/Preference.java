package com.jsware.fizz.model.member;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jsware.fizz.constants.FizzConstants.Category;
@Table(
	uniqueConstraints = @UniqueConstraint(columnNames = { "owner_id","category" })	
	)
@Entity
@SequenceGenerator(name="pref_seq", initialValue=1)
public class Preference {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="pref_seq")
	private long id;
	
	@Enumerated(EnumType.STRING)
	@Column( unique = true ,length=10)
	private Category category;
	
	private int weight;
	
	@ManyToOne()
	@JsonIgnore
	private Member owner;

	public Member getOwner() {
		return owner;
	}

	public void setOwner(Member owner) {
		this.owner = owner;
	}

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

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public Preference(Category category, int weight) {
		super();
		this.id = id;
		this.category = category;
		this.weight = weight;
	}

	public Preference() {
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
		Preference other = (Preference) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
	

}
