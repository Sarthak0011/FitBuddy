package com.fitbuddy.activity_service.repositories;

import com.fitbuddy.activity_service.models.Activity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ActivityRepository extends MongoRepository<Activity, String> {
}
