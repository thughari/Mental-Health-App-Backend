package com.hack4job.userservice.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.hack4job.userservice.model.Users;

@Repository
public interface UserRepo extends MongoRepository<Users, String> {

    Users findByUsername(String username);

}