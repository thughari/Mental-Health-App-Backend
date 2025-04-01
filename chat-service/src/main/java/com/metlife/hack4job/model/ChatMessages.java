package com.metlife.hack4job.model;

import java.time.LocalDateTime;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class ChatMessages {
	
	private String userQuestion;
    private String aiResponse;
    private LocalDateTime timestamp;

}
