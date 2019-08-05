package com.jsware.fizz.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.jsware.fizz.model.member.Member;


public interface MemberRepository extends CrudRepository<Member, Long>
{
	@Query("from Member m where m.username = :username ")
	public Member findByUsername(@Param("username") String username);

	@Query("select case when count(m)> 0 then true else false end from Member m  where  m.username = :username")
	public boolean existByUsername(@Param("username") String username);
	
	@Query("delete from Member m where m.username = :username ")
	public void deeleteByUsername(@Param("username") String username);
}
