package com.hack4job.userservice.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hack4job.userservice.model.LoginResponse;
import com.hack4job.userservice.model.UserDTO;
import com.hack4job.userservice.model.Users;
import com.hack4job.userservice.repo.UserRepo;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {
	
	@Autowired
	private UserRepo repo;
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private JWTService jwtService;
	
	@Autowired
	private PasswordEncoder encoder;
	
	public Users register(Users user) {
		log.info("inside register user" + user.toString());
		Users isUserExist = repo.findByUsername(user.getUsername());
		if(isUserExist != null) {
			log.info("user already exists " +isUserExist.toString());
			throw new DataIntegrityViolationException("user already exist with the username: "+isUserExist.getUsername());
		}
		user.setPassword(encoder.encode(user.getPassword()));
		user.setId(generateID());
		return repo.save(user);
	}

	public LoginResponse loginUser(Users user) {
		log.info(user.getUsername()+ "  " + user.getPassword());
		Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		log.info("auth " + authentication.toString());
		
		String jwtToken = authentication.isAuthenticated()?jwtService.generateToken(user.getUsername()):"fail";
		Users dbUser = repo.findByUsername(user.getUsername());
		return new LoginResponse(jwtToken, dbUser);
	}
	
	private String generateID() {
        long count = repo.count() + 1;
        return "MLFINM" +
                LocalDate.now().getDayOfWeek().getValue() +
                LocalDate.now().getDayOfMonth() +
                LocalDate.now().getYear() +
                count;
    }

	public void logoutUser(String token) {
		
		jwtService.revokeToken(token);
	}

	public UserDTO getUser(HttpServletRequest request) {
		UserDTO userDTO = new UserDTO();
		String token = request.getHeader(HttpHeaders.AUTHORIZATION).substring(7);
		String username = jwtService.extractUserName(token);
		Users user = repo.findByUsername(username);
		userDTO.setUsername(user.getUsername());
		userDTO.setId(user.getId());
		userDTO.setEmail(user.getEmail());
		userDTO.setFirstName(user.getFirstName());
		userDTO.setLastName(user.getLastName());
		return userDTO;
	}
	
}
