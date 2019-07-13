package com.jsware.fizz.repository;

import org.springframework.data.repository.CrudRepository;

import com.jsware.fizz.model.rating.Rating;

public interface RatingRepository extends CrudRepository<Rating, Long> {

}
