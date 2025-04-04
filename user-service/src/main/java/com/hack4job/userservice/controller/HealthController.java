package com.hack4job.userservice.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hack4job.userservice.model.HealthData;
import com.hack4job.userservice.service.HealthService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/api/health")
@Slf4j
public class HealthController {
	
	@Autowired
	private HealthService healthService;
	
	@PostMapping("/post")
	public ResponseEntity<?> postHealthData(@RequestBody HealthData healthData, HttpServletRequest request) {
		log.info("inside health controller: "+ healthData.toString());
		
		HealthData savedHealthData = healthService.saveOrUpdate(healthData, request);
		log.info(savedHealthData.toString());
		return ResponseEntity.status(HttpStatus.SC_CREATED).body(savedHealthData);
	}
	
	@GetMapping("/get")
	public ResponseEntity<?> getHealthData(HttpServletRequest request) {
		log.info("inside health cntroller");
		HealthData healthData = healthService.getHealthData(request);
		log.info(healthData!=null?healthData.toString():"no health data present");
		return ResponseEntity.status(HttpStatus.SC_OK).body(healthData);
	}
	
	

}
