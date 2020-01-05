package com.djamware.mongodbsecurity.service.impl;

import com.djamware.mongodbsecurity.domain.TaskStatus;
import com.djamware.mongodbsecurity.repository.TaskStatusRepository;
import com.djamware.mongodbsecurity.service.TaskStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskStatusServiceImpl implements TaskStatusService {
    private TaskStatusRepository statusRepository;

    @Autowired
    public TaskStatusServiceImpl(TaskStatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    @Override
    public TaskStatus findByStatus(String status) {
        return statusRepository.findByStatus(status);
    }

    @Override
    public void save(TaskStatus status) {
        statusRepository.save(status);
    }
}
