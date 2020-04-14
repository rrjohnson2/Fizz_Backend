package com.jsware.fizz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.jsware.fizz.model.idea.Idea;

public interface IdeaRepository extends CrudRepository<Idea, Long> {

	@Query("from Idea i where DATEDIFF(day,i.timestamp,GETDATE()) < :days ")
	public List<Idea> trending(@Param("days")int days);

}
