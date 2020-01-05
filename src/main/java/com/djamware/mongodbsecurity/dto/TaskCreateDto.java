package com.djamware.mongodbsecurity.dto;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskCreateDto {
    private String title;
    private String text;
    private Set<String> usersCanEditTask;
}
