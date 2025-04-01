package com.metlife.hack4job.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "chat_history")
public class ChatHistory {
    @Id
    private String id;
    private String username;
    private String role;  // "user" or "assistant"
    private String message;
    private LocalDateTime timestamp;
}