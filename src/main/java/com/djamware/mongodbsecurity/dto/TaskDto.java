package com.djamware.mongodbsecurity.dto;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TaskDto {
    private String taskId;
    private String text;
    private Set<String> usersCanEditTask;
    private String taskStatus;
}
