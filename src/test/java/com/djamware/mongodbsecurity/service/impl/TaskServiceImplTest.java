package com.djamware.mongodbsecurity.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.djamware.mongodbsecurity.domain.Task;
import com.djamware.mongodbsecurity.repository.TaskRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TaskServiceImplTest {
    private TaskRepository taskRepository;
    private TaskServiceImpl taskService;
    private Task task;
    private List<Task> expectedData = new ArrayList<>();


    @Before
    public void setUp() throws Exception {
        taskRepository = mock(TaskRepository.class);
        taskService = new TaskServiceImpl(taskRepository);
        task = new Task("taskID",
                "testTitle",
                "testText",
                "example@gmail.com",
                LocalDateTime.now(),
                "example@gmail.com",
                LocalDateTime.now(),
                Set.of("some@email.com"),
                "NEW_TASK");
        expectedData.add(task);

    }

    @Test
    public void findTaskByIdOk() {
        when(taskRepository.findById(any())).thenReturn(Optional.of(task));
        Assert.assertEquals(task.getTitle(), taskService.findTaskById(any()).getTitle());
        Assert.assertEquals(task.getAuthor(), taskService.findTaskById(any()).getAuthor());
        Assert.assertEquals(task, taskService.findTaskById(any()));
    }

    @Test(expected = NoSuchElementException.class)
    public void findTaskByIdNotOk() {
        when(taskRepository.findById("correctId")).thenReturn(Optional.of(task));
        Assert.assertNotEquals(task, taskService.findTaskById("wrongId"));
    }

    @Test
    public void saveTaskOk() {
        when(taskRepository.save(task)).thenReturn(task);
        Assert.assertEquals(task, taskService.saveTask(task));
    }

    @Test
    public void findAllUsersTaskOk() {
        when(taskRepository.findAllByUsersCanEditTaskContains(any(), any()))
                .thenReturn(expectedData);
        Assert.assertEquals(expectedData, taskService.findAllUsersTask(any(), any()));
    }

    @Test
    public void delete() {
        Assert.assertEquals(true, taskService.delete(task.getTaskId()));
    }
}
