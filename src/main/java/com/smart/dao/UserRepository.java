package com.smart.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.smart.entities.User1;

public interface UserRepository extends JpaRepository<User1, Integer> {

	@Query("select u from User1 u WHERE u.email = :email")
	public User1 getUserbyUsername(@Param("email") String email);
	
	
}
