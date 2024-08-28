package com.alivia.bussiness.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alivia.bussiness.model.User;

public interface UserRepositary extends JpaRepository<User, Integer> {
	
	Optional<User> findByName(String name);

	
}
