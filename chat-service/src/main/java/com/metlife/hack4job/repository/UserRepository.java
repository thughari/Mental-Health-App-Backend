package com.metlife.hack4job.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.metlife.hack4job.model.UserData;

public interface UserRepository extends MongoRepository<UserData, String> {

}
