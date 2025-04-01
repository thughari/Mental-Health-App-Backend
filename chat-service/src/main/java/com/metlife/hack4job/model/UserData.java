package com.metlife.hack4job.model;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collation = "users")
public class UserData {
	
	@Id
	private String email;
	private String password;
	private String firstName;
	private String lastName;
	private List<ChatMessages> chatHistory;
	
}
