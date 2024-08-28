package com.alivia.bussiness.service;

import java.util.Optional;

import com.alivia.bussiness.model.User;

public interface UserService {

	
	public Integer saveUser(User user);
	Optional<User> findByName(String name);

	
}
