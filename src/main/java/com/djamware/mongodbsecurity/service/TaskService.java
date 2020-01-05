package com.djamware.mongodbsecurity.service;

import com.djamware.mongodbsecurity.domain.Task;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface TaskService {
    Task findTaskById(String taskId);

    Task saveTask(Task task);

    List<Task> findAllUsersTask(String email, Pageable pageRequest);

    List<Task> findAll(Pageable pageRequest);

    boolean delete(String taskId);
}
