package com.hack4job.userservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hack4job.userservice.model.UserDetals;
import com.hack4job.userservice.model.Users;
import com.hack4job.userservice.repo.UserRepo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MyUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepo repo;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users user = repo.findByUsername(username);
		if(user==null) {
			throw new UsernameNotFoundException("user cannot be found with the user name : " + username);
		}
		log.info(user.toString());
		return new UserDetals(user);
	}

}
