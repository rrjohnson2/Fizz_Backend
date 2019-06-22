package com.jsware.fizz.repository;

import org.springframework.data.repository.CrudRepository;

import com.jsware.fizz.model.Member;


public interface MemberRepository extends CrudRepository<Member, String> {

}
