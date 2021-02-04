package com.neverrar.datacloudplatform.backend.service;

import com.neverrar.datacloudplatform.backend.error.InvalidSessionIdError;
import com.neverrar.datacloudplatform.backend.error.PermissionDeniedError;
import com.neverrar.datacloudplatform.backend.error.ProjectNotExistedError;
import com.neverrar.datacloudplatform.backend.error.TaskNotExistedError;
import com.neverrar.datacloudplatform.backend.model.Project;
import com.neverrar.datacloudplatform.backend.model.Task;
import com.neverrar.datacloudplatform.backend.model.Test;
import com.neverrar.datacloudplatform.backend.model.User;
import com.neverrar.datacloudplatform.backend.repository.ProjectRepository;
import com.neverrar.datacloudplatform.backend.repository.TaskRepository;
import com.neverrar.datacloudplatform.backend.repository.UserRepository;
import com.neverrar.datacloudplatform.backend.request.CreateTaskRequest;
import com.neverrar.datacloudplatform.backend.request.UpdateTaskRequest;
import com.neverrar.datacloudplatform.backend.util.Request;
import com.neverrar.datacloudplatform.backend.util.Result;
import com.neverrar.datacloudplatform.backend.view.TaskInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@Service
public class TaskService {
    @Autowired // This means to get the bean called userRepository
    // Which is auto-generated by Spring, we will use it to handle the data
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TaskRepository taskRepository;

    public Result<TaskInformation> addNewTask (CreateTaskRequest body, User user) {
        Optional<Project> optionalProject=projectRepository.findById(body.getProjectId());
        if(!optionalProject.isPresent()) {
            return Result.wrapErrorResult(new ProjectNotExistedError());
        }
        if(!optionalProject.get().getOwner().getId().equals(user.getId())) {
            return Result.wrapErrorResult(new PermissionDeniedError());
        }
        Task task=new Task();
        task.setOwner(user);
        task.setProject(optionalProject.get());
        task.setDescription(body.getDescription());
        task.setName(body.getName());
        task.setScene(body.getScene());
        taskRepository.save(task);
        return Result.wrapSuccessfulResult(new TaskInformation(task));
    }

    public Result<TaskInformation> getTask (User user, Integer id) {
        Optional<Task> optionalTask= taskRepository.findById(id);
        if(!optionalTask.isPresent()) {
            return Result.wrapErrorResult(new ProjectNotExistedError());
        }
        if(!user.getId().equals(optionalTask.get().getOwner().getId())) {
            return Result.wrapErrorResult(new PermissionDeniedError());
        }
        return Result.wrapSuccessfulResult(new TaskInformation(optionalTask.get()));
    }

    public Result<TaskInformation> updateTask (UpdateTaskRequest body, Integer id, User user) {
        Optional<Task> optionalTask=taskRepository.findById(id);
        if(!optionalTask.isPresent()) {
            return Result.wrapErrorResult(new TaskNotExistedError());
        }
        if(!optionalTask.get().getOwner().getId().equals(user.getId())) {
            return Result.wrapErrorResult(new PermissionDeniedError());
        }
        Task task=new Task();
        task.setScene(body.getScene());
        task.setName(body.getName());
        task.setDescription(body.getDescription());
        taskRepository.save(task);
        return Result.wrapSuccessfulResult(new TaskInformation(task));
    }


    public Result<String> deleteTask(User user,Integer id) {
        Optional<Task> optionalTask=taskRepository.findById(id);
        if(!optionalTask.isPresent()) {
            return Result.wrapErrorResult(new TaskNotExistedError());
        }
        if(!optionalTask.get().getOwner().getId().equals(user.getId())) {
            return Result.wrapErrorResult(new PermissionDeniedError());
        }
        taskRepository.delete(optionalTask.get());
        return Result.wrapSuccessfulResult("Deleted");
    }
}
