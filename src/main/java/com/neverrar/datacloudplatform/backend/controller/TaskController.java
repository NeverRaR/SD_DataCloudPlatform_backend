package com.neverrar.datacloudplatform.backend.controller;

import com.neverrar.datacloudplatform.backend.error.*;
import com.neverrar.datacloudplatform.backend.model.Project;
import com.neverrar.datacloudplatform.backend.model.Task;
import com.neverrar.datacloudplatform.backend.model.User;
import com.neverrar.datacloudplatform.backend.repository.ProjectRepository;
import com.neverrar.datacloudplatform.backend.repository.TaskRepository;
import com.neverrar.datacloudplatform.backend.repository.UserRepository;
import com.neverrar.datacloudplatform.backend.service.TaskService;
import com.neverrar.datacloudplatform.backend.util.Request;
import com.neverrar.datacloudplatform.backend.util.Result;
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
    private StringRedisTemplate template;

    @Autowired
    private TaskService taskService;


    @PostMapping // Map ONLY POST Requests
    public @ResponseBody
    Result<String> addNewTask (@RequestBody  Request<Task> request) {
        String userId=template.opsForValue().get(request.getSessionId());
        if(userId==null)  return Result.wrapErrorResult(new InvalidSessionIdError());
        return taskService.addNewTask(request.getData(),userId);
    }

    @GetMapping("/{id}")
    public @ResponseBody Result<Task> getTask (@RequestParam String sessionId,@PathVariable Integer id) {
        String userId=template.opsForValue().get(sessionId);
        if(userId==null)  return Result.wrapErrorResult(new InvalidSessionIdError());
        return taskService.getTask(userId,id);
    }

    @GetMapping
    public @ResponseBody Result<Set<Task>> getAllTask (@RequestParam String sessionId,@RequestParam Integer projectId) {
        String userId=template.opsForValue().get(sessionId);
        if(userId==null)  return Result.wrapErrorResult(new InvalidSessionIdError());
        return taskService.getAllTask(userId,projectId);
    }

    @PutMapping("/{id}")
    public @ResponseBody Result<Task> updateTask (@RequestBody Request<Task> request, @PathVariable Integer id) {
        String userId=template.opsForValue().get(request.getSessionId());
        if(userId==null)  return Result.wrapErrorResult(new InvalidSessionIdError());
        return taskService.updateTask(request.getData(),id,userId);
    }


    @DeleteMapping(path="/{id}")
    public @ResponseBody Result<String> deleteTask(@RequestParam String sessionId,@PathVariable Integer id) {
        String userId=template.opsForValue().get(sessionId);
        if(userId==null)  return Result.wrapErrorResult(new InvalidSessionIdError());
        return taskService.deleteTask(userId,id);
    }
}
