package com.neverrar.datacloudplatform.backend.controller;

import com.neverrar.datacloudplatform.backend.error.*;
import com.neverrar.datacloudplatform.backend.model.Project;
import com.neverrar.datacloudplatform.backend.model.User;
import com.neverrar.datacloudplatform.backend.repository.ProjectRepository;
import com.neverrar.datacloudplatform.backend.repository.UserRepository;
import com.neverrar.datacloudplatform.backend.service.ProjectService;
import com.neverrar.datacloudplatform.backend.util.Request;
import com.neverrar.datacloudplatform.backend.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

@Controller // This means that this class is a Controller
@RequestMapping(path="api/projects") // This means URL's start with /demo (after Application path)
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @Autowired
    private StringRedisTemplate template;



    @PostMapping // Map ONLY POST Requests
    public @ResponseBody
    Result<String> addNewProject (@RequestBody  Request<Project> request) {
        String userId=template.opsForValue().get(request.getSessionId());
        if(userId==null)  return Result.wrapErrorResult(new InvalidSessionIdError());
        return projectService.addNewProject(request.getData(),userId);
    }

    @GetMapping("/{id}")
    public @ResponseBody Result<Project> getProject (@CookieValue(value = "sessionId",
            defaultValue = "noSession") String sessionId,@PathVariable Integer id) {
        String userId=template.opsForValue().get(sessionId);
        if(userId==null)  return Result.wrapErrorResult(new InvalidSessionIdError());
        return projectService.getProject(userId,id);
    }

    @GetMapping
    public @ResponseBody Result<Set<Project>> getAllProject (@CookieValue(value = "sessionId",
            defaultValue = "noSession") String sessionId) {
        String userId=template.opsForValue().get(sessionId);
        if(userId==null)  return Result.wrapErrorResult(new InvalidSessionIdError());
        return projectService.getAllProject(userId);
    }

    @PutMapping("/{id}")
    public @ResponseBody Result<Project> updateProject (@CookieValue(value = "sessionId",
            defaultValue = "noSession") String sessionId,@RequestBody Project request, @PathVariable Integer id) {
        String userId=template.opsForValue().get(sessionId);
        if(userId==null)  return Result.wrapErrorResult(new InvalidSessionIdError());
        return projectService.updateProject(request,id,userId);
    }


    @DeleteMapping(path="/{id}")
    public @ResponseBody Result<String> deleteProject(@CookieValue(value = "sessionId",
            defaultValue = "noSession") String sessionId,@PathVariable Integer id) {
        String userId=template.opsForValue().get(sessionId);
        if(userId==null) return Result.wrapErrorResult(new InvalidSessionIdError());
        return projectService.deleteProject(userId,id);
    }
}
