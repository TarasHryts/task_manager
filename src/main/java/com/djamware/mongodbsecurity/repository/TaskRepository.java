package com.djamware.mongodbsecurity.repository;

import com.djamware.mongodbsecurity.domain.Task;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TaskRepository extends MongoRepository<Task, String> {
    List<Task> findAllByUsersCanEditTaskContains(String email, Pageable pageRequest);
}
