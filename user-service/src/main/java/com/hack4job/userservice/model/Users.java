package com.hack4job.userservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.ToString;

@Document(collection = "users")
@Data
@ToString
public class Users {

    @Id
    private String id;
    private String email;
    private String username;
    private String password;
    private String firstName;
	private String lastName;

}