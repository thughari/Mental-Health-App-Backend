package com.metlife.hack4job.controller;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.metlife.hack4job.model.Message;
import com.metlife.hack4job.service.ChatService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

//@CrossOrigin(allowedHeaders ="*")
@RestController
@RequestMapping("/api/chat")
@Slf4j
public class ChatController {

    @Autowired
    private ChatService chatService;

    @PostMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> chatWithAI(@RequestBody Message userMessage, HttpServletRequest request) {
        log.info("User Message: {}", userMessage);
        return chatService.getAIResponseStream(userMessage, request)
                          .delayElements(Duration.ofMillis(200));
    }
    @PostMapping(value = "/routine", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> generateRoutine(@RequestBody Message userMessage, HttpServletRequest httpServletRequest) {
        log.info("User Message: {}", userMessage);
        return chatService.suggestRoutine(userMessage, httpServletRequest)
                          .delayElements(Duration.ofMillis(200));
    }
}
