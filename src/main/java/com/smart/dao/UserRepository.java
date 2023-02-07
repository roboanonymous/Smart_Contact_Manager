package com.smart.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smart.entities.User1;

public interface UserRepository extends JpaRepository<User1, Integer> {

	
}
