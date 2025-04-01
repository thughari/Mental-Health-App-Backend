package com.metlife.hack4job.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.metlife.hack4job.model.UserData;
import com.metlife.hack4job.model.UserRegistrationData;
import com.metlife.hack4job.service.UserService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin(allowedHeaders ="*")
@RestController
@RequestMapping("api/user")
@Slf4j
public class UserController {
	@Autowired
	private UserService userService;
	
	@PostMapping("/register")
	public UserData registerUser(@RequestBody UserRegistrationData registrationData) {
		log.info(registrationData.toString());
		return userService.saveUser(registrationData);
	}
	
}
