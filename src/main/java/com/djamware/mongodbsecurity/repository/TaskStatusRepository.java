package com.djamware.mongodbsecurity.repository;

import com.djamware.mongodbsecurity.domain.TaskStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TaskStatusRepository extends MongoRepository<TaskStatus, String> {
    TaskStatus findByStatus(String status);
}
