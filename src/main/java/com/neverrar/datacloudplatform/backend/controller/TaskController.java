package com.neverrar.datacloudplatform.backend.controller;

import com.neverrar.datacloudplatform.backend.error.*;
import com.neverrar.datacloudplatform.backend.model.Project;
import com.neverrar.datacloudplatform.backend.model.Task;
import com.neverrar.datacloudplatform.backend.model.User;
import com.neverrar.datacloudplatform.backend.repository.ProjectRepository;
import com.neverrar.datacloudplatform.backend.repository.TaskRepository;
import com.neverrar.datacloudplatform.backend.repository.UserRepository;
import com.neverrar.datacloudplatform.backend.request.CreateTaskRequest;
import com.neverrar.datacloudplatform.backend.request.UpdateTaskRequest;
import com.neverrar.datacloudplatform.backend.service.AuthenticationService;
import com.neverrar.datacloudplatform.backend.service.TaskService;
import com.neverrar.datacloudplatform.backend.util.Request;
import com.neverrar.datacloudplatform.backend.util.Result;
import com.neverrar.datacloudplatform.backend.view.TaskInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

@Controller // This means that this class is a Controller
@RequestMapping(path="api/tasks") // This means URL's start with /demo (after Application path)
public class TaskController {


    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    private TaskService taskService;


    @PostMapping // Map ONLY POST Requests
    public @ResponseBody
    Result<TaskInformation> addNewTask (@CookieValue(value = "sessionId",
            defaultValue = "noSession") String sessionId, @RequestBody CreateTaskRequest body) {
        User user=authenticationService.getUser(sessionId);
        if(user==null)  {
            return Result.wrapErrorResult(new InvalidSessionIdError());
        }
        return taskService.addNewTask(body,user);
    }

    @GetMapping("/{id}")
    public @ResponseBody Result<TaskInformation> getTask (@CookieValue(value = "sessionId",
            defaultValue = "noSession") String sessionId, @PathVariable Integer id) {
        User user=authenticationService.getUser(sessionId);
        if(user==null)  {
            return Result.wrapErrorResult(new InvalidSessionIdError());
        }
        return taskService.getTask(user,id);
    }


    @PutMapping("/{id}")
    public @ResponseBody Result<TaskInformation> updateTask (@CookieValue(value = "sessionId",
            defaultValue = "noSession") String sessionId, @RequestBody UpdateTaskRequest body
            , @PathVariable Integer id) {
        User user=authenticationService.getUser(sessionId);
        if(user==null)  {
            return Result.wrapErrorResult(new InvalidSessionIdError());
        }
        return taskService.updateTask(body,id,user);
    }


    @DeleteMapping(path="/{id}")
    public @ResponseBody Result<String> deleteTask(@CookieValue(value = "sessionId",
            defaultValue = "noSession") String sessionId,@PathVariable Integer id) {
        User user=authenticationService.getUser(sessionId);
        if(user==null)  {
            return Result.wrapErrorResult(new InvalidSessionIdError());
        }
        return taskService.deleteTask(user,id);
    }
}
