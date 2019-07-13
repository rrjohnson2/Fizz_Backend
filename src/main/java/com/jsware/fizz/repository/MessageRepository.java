package com.jsware.fizz.repository;

import org.springframework.data.repository.CrudRepository;

import com.jsware.fizz.model.retort.Message;

public interface MessageRepository extends CrudRepository<Message, Long> {

}
