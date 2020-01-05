package com.djamware.mongodbsecurity.controller;

import com.djamware.mongodbsecurity.constants.Constants;
import com.djamware.mongodbsecurity.domain.Task;
import com.djamware.mongodbsecurity.dto.TaskCreateDto;
import com.djamware.mongodbsecurity.dto.TaskDto;
import com.djamware.mongodbsecurity.exception.AccessDeniedException;
import com.djamware.mongodbsecurity.service.TaskService;
import com.djamware.mongodbsecurity.service.TaskStatusService;
import com.djamware.mongodbsecurity.util.Email;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/task")
@RestController
public class TaskController {
    private TaskService taskService;
    private TaskStatusService statusService;

    @Autowired
    public TaskController(TaskService taskService,
                          TaskStatusService statusService) {
        this.taskService = taskService;
        this.statusService = statusService;
    }

    @PostMapping("/create")
    public Task createNewTask(@Valid @RequestBody TaskCreateDto taskCreateDto,
                              @RequestParam String password,
                              HttpServletRequest request) {
        String activeUserEmail = request.getUserPrincipal().getName();
        Task task = new Task();
        task.setTitle(taskCreateDto.getTitle());
        task.setText(taskCreateDto.getText());
        task.setAuthor(activeUserEmail);
        task.setCreateTime(LocalDateTime.now());
        task.setLastChangeTask(activeUserEmail);
        task.setLastChangeTime(LocalDateTime.now());
        Set<String> userCanEditEmails = new HashSet<>(taskCreateDto.getUsersCanEditTask());
        userCanEditEmails.add(activeUserEmail);
        task.setUsersCanEditTask(userCanEditEmails);
        task.setTaskStatus(Constants.NEW_TASK);
        Email.sendEmail(activeUserEmail,
                password,
                new ArrayList<>(task.getUsersCanEditTask()),
                task.getTitle(),
                task.getText());
        return taskService.saveTask(task);
    }

    @GetMapping(value = "/{taskId}")
    public Task task(@PathVariable("taskId") String taskId) {
        return taskService.findTaskById(taskId);
    }

    @PutMapping("/{taskId}")
    public Task updateTask(@PathVariable("taskId") String taskId,
                           @RequestBody TaskDto taskDto,
                           @RequestParam String password,
                           HttpServletRequest request) {
        Task task = taskService.findTaskById(taskId);
        String currentUserEmail = request.getUserPrincipal().getName();
        if (!task.getUsersCanEditTask().contains(currentUserEmail)) {
            throw new AccessDeniedException("You can't edit this task!");
        }
        Set<String> userCanEditEmails = new HashSet<>(taskDto.getUsersCanEditTask());
        userCanEditEmails.add(task.getAuthor());
        userCanEditEmails.add(currentUserEmail);
        task.setUsersCanEditTask(userCanEditEmails);
        task.setText(taskDto.getText());
        task.setLastChangeTask(currentUserEmail);
        task.setLastChangeTime(LocalDateTime.now());
        if (statusService.findByStatus(taskDto.getTaskStatus()) == null) {
            task.setTaskStatus(Constants.IN_PROGRESS);
        } else {
            task.setTaskStatus(taskDto.getTaskStatus());
        }
        Email.sendEmail(currentUserEmail,
                password,
                new ArrayList<>(userCanEditEmails),
                task.getTitle(),
                task.getText() + "\n Updated by " + currentUserEmail);
        return taskService.saveTask(task);
    }

    @DeleteMapping("/{taskId}")
    public void delete(@PathVariable("taskId") String taskId,
                       @RequestParam String password,
                       HttpServletRequest request) {
        String currentUserEmail = request.getUserPrincipal().getName();
        Task task = taskService.findTaskById(taskId);
        if (!request.getUserPrincipal().getName()
                .equals(taskService.findTaskById(taskId).getAuthor())) {
            throw new AccessDeniedException("You have no permission to delete. " +
                    "Only author can delete task.");
        }
        Email.sendEmail(currentUserEmail,
                password,
                new ArrayList<>(task.getUsersCanEditTask()),
                task.getTitle(),
                task.getText() + "\n Deleted by " + currentUserEmail);
        taskService.delete(taskId);
    }

    @GetMapping("/allUserTask")
    public List<Task> getAllUsersTask(@RequestParam(value = "page", required = false,
            defaultValue = Constants.PAGE) Integer page,
                                      @RequestParam(value = "limit", required = false,
                                              defaultValue = Constants.LIMIT) Integer limit,
                                      @RequestParam(value = "sortBy", required = false,
                                              defaultValue = Constants.TASKS_SORT_BY) String sortBy,
                                      @RequestParam(value = "sortOrder", required = false,
                                              defaultValue = Constants.SORT_ORDER) String sortOrder,
                                      HttpServletRequest request) {
        Sort.Direction orderingDirection = Sort.Direction.fromString(sortOrder);
        Sort sortByRequest = Sort.by(orderingDirection, sortBy);
        Pageable pageRequest = PageRequest.of(page, limit, sortByRequest);
        return taskService.findAllUsersTask(request.getUserPrincipal().getName(), pageRequest);
    }

    @GetMapping("/all")
    public List<Task> getAllTask(@RequestParam(value = "page", required = false,
            defaultValue = Constants.PAGE) Integer page,
                                 @RequestParam(value = "limit", required = false,
                                         defaultValue = Constants.LIMIT) Integer limit,
                                 @RequestParam(value = "sortBy", required = false,
                                         defaultValue = Constants.TASKS_SORT_BY) String sortBy,
                                 @RequestParam(value = "sortOrder", required = false,
                                         defaultValue = Constants.SORT_ORDER) String sortOrder) {
        Sort.Direction orderingDirection = Sort.Direction.fromString(sortOrder);
        Sort sortByRequest = Sort.by(orderingDirection, sortBy);
        Pageable pageRequest = PageRequest.of(page, limit, sortByRequest);
        return taskService.findAll(pageRequest);
    }
}
