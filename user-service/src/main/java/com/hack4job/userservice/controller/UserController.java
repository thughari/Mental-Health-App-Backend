package com.hack4job.userservice.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hack4job.userservice.model.UserDTO;
import com.hack4job.userservice.model.Users;
import com.hack4job.userservice.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("api/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/get")
	public ResponseEntity<?> getUserProfile(HttpServletRequest request) {
		UserDTO userDetails = userService.getUser(request);
		return ResponseEntity.status(HttpStatus.SC_OK).body(userDetails);
	}
	
	
}
