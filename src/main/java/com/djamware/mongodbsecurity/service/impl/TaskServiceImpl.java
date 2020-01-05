package com.djamware.mongodbsecurity.service.impl;

import com.djamware.mongodbsecurity.domain.Task;
import com.djamware.mongodbsecurity.repository.TaskRepository;
import com.djamware.mongodbsecurity.service.TaskService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl implements TaskService {
    private TaskRepository taskRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Task findTaskById(String taskId) {
        return taskRepository.findById(taskId).orElseThrow();
    }

    @Override
    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public List<Task> findAllUsersTask(String email, Pageable pageRequest) {
        return taskRepository.findAllByUsersCanEditTaskContains(email, pageRequest);
    }

    @Override
    public List<Task> findAll(Pageable pageRequest) {
        return taskRepository.findAll(pageRequest).getContent();
    }

    @Override
    public boolean delete(String taskId) {
        taskRepository.deleteById(taskId);
        return taskRepository.findById(taskId).isEmpty();
    }
}
