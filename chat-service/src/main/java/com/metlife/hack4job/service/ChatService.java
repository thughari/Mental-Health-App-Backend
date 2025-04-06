package com.metlife.hack4job.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.metlife.hack4job.model.ChatHistory;
import com.metlife.hack4job.model.HealthData;
import com.metlife.hack4job.model.Message;
import com.metlife.hack4job.repository.ChatHistoryRepo;
import com.metlife.hack4job.repository.HealthRepo;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class ChatService {

	@Autowired
	private ChatHistoryRepo chatHistoryRepo;

	@Autowired
	private HealthRepo healthRepo;

	@Autowired
	private JWTService jwtService;

	@Value("${OLLAMA_BASE_URL:http://localhost:11434}")
private String ollamaBaseUrl;

	private WebClient webClient;

	@PostConstruct
	public void initWebClient() {
		this.webClient = WebClient.builder()
			.baseUrl(ollamaBaseUrl)
			.build();
	}

	public Flux<String> getAIResponseStream(Message userMessage, HttpServletRequest request) {
		String token = request.getHeader(HttpHeaders.AUTHORIZATION).substring(7);
		String username = jwtService.extractUserName(token);

		// Fetch last 10 messages for context
		List<ChatHistory> chatHistory = chatHistoryRepo.findTop10ByUsernameOrderByTimestampAsc(username);
		
		Optional<HealthData> userHealth = healthRepo.findById(username);

		// Build the chat history prompt
		StringBuilder chatContext = new StringBuilder();
		chatHistory.forEach(msg -> chatContext.append(msg.getRole()).append(": ").append(msg.getMessage()).append("\n"));
		log.info("history: " + chatContext.toString());
		String prompt = "You are an assistant specializing in mental health. "
				+ "Answer carefully and only focus on medical/mental health-related topics. "
				+ "If the query is about suffering with mental health problems try to comfort or try your best. "
				+ "This is the current time: " 
				+ LocalDateTime.now().toString()
				+ ". If you are asking any info say lieke it would be confidential and will be among us. "
				+ "If asked about anything else, respond with: 'I am unable to help with that concern.' "
				+ "If the user wishes(like hi or hay or hello like) you wish back in a good way! "
				+ "Keep responses very short and effective. Keep it under 3 lines. "
				+ (userHealth.isPresent() ? "providing the health details " + userHealth.get().toString():"")
				+ " Here is the chat history:\n"
				+ chatContext.toString() 
				+"\n"
				+ "User: " + userMessage.getMessage();

		// Save user message in history
		saveUserChatHistory(username, "user", userMessage.getMessage());

		// Collect the full response before saving
		List<String> responseChuncks = new ArrayList<>();

		return webClient.post()
				.uri("/api/generate")
				.bodyValue(Map.of(
						"model", "llama3",
						"prompt", prompt
						))
				.retrieve()
				.bodyToFlux(String.class)
				.doOnNext(responseChuncks::add)  // Accumulate response chunks
				.doOnComplete(() -> saveAIChatHistory(username, "assistant", responseChuncks))
				.delayElements(Duration.ofMillis(100));  // Simulating streaming effect
	}


	private void saveUserChatHistory(String username, String role, String message) {

		ChatHistory chat = new ChatHistory();
		chat.setUsername(username);
		chat.setRole(role);
		chat.setMessage(message);
		log.info("user message: "+ message);
		chat.setTimestamp(LocalDateTime.now());
		chatHistoryRepo.save(chat);
		
	}

	private void saveAIChatHistory(String username, String role, List<String> responseChuncks) {
		ChatHistory chat = new ChatHistory();
		StringBuilder response = new StringBuilder();
		responseChuncks.forEach(json -> {
            JsonObject obj = JsonParser.parseString(json).getAsJsonObject();
            response.append(obj.get("response").getAsString());
        });
		log.info("model response: " + response.toString());
		chat.setUsername(username);
		chat.setRole(role);
		chat.setMessage(response.toString());
		chat.setTimestamp(LocalDateTime.now());
		chatHistoryRepo.save(chat);
	}

	public Flux<String> suggestRoutine(Message userMessage, HttpServletRequest httpServletRequest) {

		String token = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION).substring(7);
		log.info("token: "+token);
		String username = jwtService.extractUserName(token);
		log.info("username: " + username);

		Optional<HealthData> healthData = healthRepo.findById(username);

		log.info("health data: " + healthData.toString());
		String builtPrompt = buildPromptForRoutineSuggestion(userMessage, healthData.get());
		log.info("builtPrompt: " + builtPrompt);

		return webClient.post()
				.uri("/api/generate")
				.bodyValue(Map.of(
						"model", "llama3",
						"prompt", builtPrompt 
						))
				.retrieve()
				.bodyToFlux(String.class)
				.delayElements(Duration.ofMillis(100));
	}

	private String buildPromptForRoutineSuggestion(Message userMessage, HealthData healthData) {
		StringBuilder sb = new StringBuilder();
		sb.append("You are a doctor now.\n");
		sb.append("suggest me a routine based on my details, here are my details in JSON format: ");
		sb.append(healthData);
		sb.append(" and here are some optional details ");
		sb.append(userMessage.getMessage());
		sb.append(", keep the response short and effective, ");
		sb.append("and don't show or talk about the fields, ");
		sb.append("just say based on your healh data.");
		return sb.toString();
	}

}
