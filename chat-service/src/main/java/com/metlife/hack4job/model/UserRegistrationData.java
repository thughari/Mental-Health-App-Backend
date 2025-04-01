package com.metlife.hack4job.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserRegistrationData {
	
	private String email;
	private String password;
	private String confirmPassword;
	private String firstName;
	private String lastName;

}
