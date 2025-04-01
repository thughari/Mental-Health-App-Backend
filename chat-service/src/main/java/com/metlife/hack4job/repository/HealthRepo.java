package com.metlife.hack4job.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.metlife.hack4job.model.HealthData;

@Repository
public interface HealthRepo extends MongoRepository<HealthData, String> {

}
