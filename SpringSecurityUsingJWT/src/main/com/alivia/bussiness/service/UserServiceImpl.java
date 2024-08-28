package com.alivia.bussiness.service;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.alivia.bussiness.model.User;
import com.alivia.bussiness.repo.UserRepositary;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

	
	@Autowired
	UserRepositary repo;

	@Autowired
	BCryptPasswordEncoder encoder;

	@Override
	public Integer saveUser(User user) {
		String encodedPassword = encoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		return repo.save(user).getId();
	}

	@Override
	public Optional<User> findByName(String name) {
		// TODO Auto-generated method stub
		return repo.findByName(name);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		Optional<User> op = findByName(username);
//		if (op.isEmpty()) {
//			throw new UsernameNotFoundException("User '" + username + "' is not avaiable");
//		}
//		User user = op.get();

		User user = repo.findByName(username)
				.orElseThrow(() -> new UsernameNotFoundException("User '" + username + "' is not avaiable"));

		return new org.springframework.security.core.userdetails.User(
				user.getName(), 
				user.getPassword(),
				user.getRoles().
				stream()
				.map(role -> new SimpleGrantedAuthority(role))
				.collect(Collectors.toList()));

	}

}
