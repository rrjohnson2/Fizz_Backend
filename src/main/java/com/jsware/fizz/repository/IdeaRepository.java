package com.jsware.fizz.repository;

import org.springframework.data.repository.CrudRepository;

import com.jsware.fizz.model.idea.Idea;

public interface IdeaRepository extends CrudRepository<Idea, Long> {

}
