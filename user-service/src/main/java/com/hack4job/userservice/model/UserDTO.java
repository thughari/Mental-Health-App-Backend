package com.hack4job.userservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO {
	
	private String id;
	private String username;
    private String email;
    private String firstName;
    private String lastName;

}
