package com.djamware.mongodbsecurity.domain;

import java.time.LocalDateTime;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "task")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    @Id
    private String taskId;
    private String title;
    private String text;
    private String author;
    private LocalDateTime createTime;
    private String lastChangeTask;
    private LocalDateTime lastChangeTime;
    private Set<String> usersCanEditTask;
    private String taskStatus;
}
