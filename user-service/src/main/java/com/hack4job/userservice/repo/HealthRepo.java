package com.hack4job.userservice.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.hack4job.userservice.model.HealthData;

@Repository
public interface HealthRepo extends MongoRepository<HealthData, String> {

}
