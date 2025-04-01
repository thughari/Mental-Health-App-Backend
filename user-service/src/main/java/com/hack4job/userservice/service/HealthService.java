package com.hack4job.userservice.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import com.hack4job.userservice.model.HealthData;
import com.hack4job.userservice.repo.HealthRepo;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class HealthService {
	
	@Autowired
	private HealthRepo repo;
	
	@Autowired
	private JWTService jwtService;

    public HealthData saveOrUpdate(HealthData healthData, HttpServletRequest request) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String username = jwtService.extractUserName(authHeader.substring(7));

        // Check if HealthData already exists for the user
        Optional<HealthData> existingHealthData = repo.findById(username);

        if (existingHealthData.isPresent()) {
            // Update existing HealthData
            HealthData existing = existingHealthData.get();

            existing.setSteps(healthData.getSteps()); //set the existing metrics with the ones that exist.
            existing.setCaloriesBurned(healthData.getCaloriesBurned());
            existing.setSleepHours(healthData.getSleepHours());
            existing.setHeartRate(healthData.getHeartRate());
            existing.setSystolicBloodPressure(healthData.getSystolicBloodPressure());
            existing.setDiastolicBloodPressure(healthData.getDiastolicBloodPressure());
            existing.setCustomFields(healthData.getCustomFields());

            // Save the updated entity
            return repo.save(existing);
        } else {
            // Create new HealthData
            healthData.setUsername(username); // assign the username to healthData
            return repo.save(healthData); // save the healthData with the username.
        }
    }

    public HealthData getHealthData(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION).substring(7);
        String username = jwtService.extractUserName(token);
        log.info("inside get health data + user: "+ username);
        Optional<HealthData> healthData = repo.findById(username);
        return healthData.orElse(null);

    }
}