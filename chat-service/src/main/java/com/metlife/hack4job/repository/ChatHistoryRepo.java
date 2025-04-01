package com.metlife.hack4job.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.metlife.hack4job.model.ChatHistory;

@Repository
public interface ChatHistoryRepo extends MongoRepository<ChatHistory, String> {

	List<ChatHistory> findTop10ByUsernameOrderByTimestampAsc(String username);

}
