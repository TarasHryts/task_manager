package com.djamware.mongodbsecurity;

import com.djamware.mongodbsecurity.constants.Constants;
import com.djamware.mongodbsecurity.domain.Role;
import com.djamware.mongodbsecurity.domain.TaskStatus;
import com.djamware.mongodbsecurity.repository.RoleRepository;
import com.djamware.mongodbsecurity.service.TaskStatusService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MongodbSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(MongodbSecurityApplication.class, args);
    }

    @Bean
    CommandLineRunner init(RoleRepository roleRepository, TaskStatusService taskStatusRepository) {
        return args -> {
            Role adminRole = roleRepository.findByRole("ADMIN");
            if (adminRole == null) {
                Role newAdminRole = new Role();
                newAdminRole.setRole("ADMIN");
                roleRepository.save(newAdminRole);
            }
            Role userRole = roleRepository.findByRole("USER");
            if (userRole == null) {
                Role newUserRole = new Role();
                newUserRole.setRole("USER");
                roleRepository.save(newUserRole);
            }
            TaskStatus upcomingTasksStatus = taskStatusRepository.findByStatus(Constants.NEW_TASK);
            if (upcomingTasksStatus == null) {
                TaskStatus newTaskStatus = new TaskStatus();
                newTaskStatus.setStatus(Constants.NEW_TASK);
                taskStatusRepository.save(newTaskStatus);
            }
            TaskStatus readyForWOrkStatus = taskStatusRepository.findByStatus(Constants.READY_FOR_WORK);
            if (readyForWOrkStatus == null) {
                TaskStatus newTaskStatus = new TaskStatus();
                newTaskStatus.setStatus(Constants.READY_FOR_WORK);
                taskStatusRepository.save(newTaskStatus);
            }
            TaskStatus inProgressStatus = taskStatusRepository.findByStatus(Constants.IN_PROGRESS);
            if (inProgressStatus == null) {
                TaskStatus newTaskStatus = new TaskStatus();
                newTaskStatus.setStatus(Constants.IN_PROGRESS);
                taskStatusRepository.save(newTaskStatus);
            }
            TaskStatus underReviewStatus = taskStatusRepository.findByStatus(Constants.UNDER_REVIEW);
            if (underReviewStatus == null) {
                TaskStatus newTaskStatus = new TaskStatus();
                newTaskStatus.setStatus(Constants.UNDER_REVIEW);
                taskStatusRepository.save(newTaskStatus);
            }
            TaskStatus blockedStatus = taskStatusRepository.findByStatus(Constants.BLOCKED);
            if (blockedStatus == null) {
                TaskStatus newTaskStatus = new TaskStatus();
                newTaskStatus.setStatus(Constants.BLOCKED);
                taskStatusRepository.save(newTaskStatus);
            }
            TaskStatus doneStatus = taskStatusRepository.findByStatus(Constants.DONE);
            if (doneStatus == null) {
                TaskStatus newTaskStatus = new TaskStatus();
                newTaskStatus.setStatus(Constants.DONE);
                taskStatusRepository.save(newTaskStatus);
            }
        };
    }
}
