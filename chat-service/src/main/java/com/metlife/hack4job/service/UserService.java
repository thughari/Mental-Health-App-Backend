package com.metlife.hack4job.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.metlife.hack4job.model.UserData;
import com.metlife.hack4job.model.UserRegistrationData;
import com.metlife.hack4job.repository.UserRepository;
import java.util.ArrayList;

@Service
public class UserService {
	
    @Autowired
    private UserRepository repository;
    
    public UserData saveUser(UserRegistrationData registrationData) {
        if (!registrationData.getPassword().equals(registrationData.getConfirmPassword())) {
            throw new IllegalArgumentException("Passwords do not match!");
        }

        UserData userData = new UserData();
        userData.setEmail(registrationData.getEmail());
        userData.setPassword(registrationData.getPassword());
        userData.setFirstName(registrationData.getFirstName());
        userData.setLastName(registrationData.getLastName());

        // Initialize chat history as empty
        userData.setChatHistory(new ArrayList<>());

        return repository.save(userData);
    }
}
