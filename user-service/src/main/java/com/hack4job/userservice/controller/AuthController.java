package com.hack4job.userservice.controller;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hack4job.userservice.model.LoginResponse;
import com.hack4job.userservice.model.Users;
import com.hack4job.userservice.service.UserService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@Slf4j
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
	private UserService service;	
	
	@PostMapping("/register")
	public ResponseEntity<?> register(@Validated @RequestBody Users user) {
		try {
			Users savedUser = service.register(user);
			return ResponseEntity.status(HttpStatus.SC_CREATED).body(savedUser);
		} catch(DataIntegrityViolationException exception) {
			exception.printStackTrace();
			if(exception.getMessage().contains("username")){
				return ResponseEntity.status(HttpStatus.SC_BAD_REQUEST).body("User with the username "+ user.getUsername()+ " already exists!");
			}
			return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).body("Registration failed");
		}
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody Users user) {
		log.info(user.toString());
		try {
			LoginResponse loggedInUser = service.loginUser(user);
			return ResponseEntity.status(HttpStatus.SC_CREATED).body(loggedInUser);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.SC_UNAUTHORIZED).body("login failed");
		}
	}
	
	@GetMapping("/logout")
	public ResponseEntity<?> logout(@RequestParam String token) {
		service.logoutUser(token);
		return ResponseEntity.status(HttpStatus.SC_OK).body("logout successful");
	}
	

}
