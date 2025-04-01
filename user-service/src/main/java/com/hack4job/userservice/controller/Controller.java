package com.hack4job.userservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/auth")
public class Controller {
	
	@GetMapping("/")
	public String helloController(HttpServletRequest request) {
		return "hello from controller. session id: "+request.getSession().getId();
	}

}
