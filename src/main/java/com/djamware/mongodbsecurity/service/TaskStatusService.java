package com.djamware.mongodbsecurity.service;

import com.djamware.mongodbsecurity.domain.TaskStatus;

public interface TaskStatusService {
    TaskStatus findByStatus(String status);
    void save(TaskStatus status);
}
