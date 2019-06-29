package com.jsware.fizz.repository;

import org.springframework.data.repository.CrudRepository;

import com.jsware.fizz.model.Preference;

public interface PreferenceRepository extends CrudRepository<Preference, Long> {

}
