package com.neverrar.datacloudplatform.backend.controller;

import com.neverrar.datacloudplatform.backend.error.*;
import com.neverrar.datacloudplatform.backend.model.Project;
import com.neverrar.datacloudplatform.backend.model.User;
import com.neverrar.datacloudplatform.backend.repository.ProjectRepository;
import com.neverrar.datacloudplatform.backend.repository.UserRepository;
import com.neverrar.datacloudplatform.backend.request.CreateProjectRequest;
import com.neverrar.datacloudplatform.backend.request.UpdateProjectRequest;
import com.neverrar.datacloudplatform.backend.service.AuthenticationService;
import com.neverrar.datacloudplatform.backend.service.ProjectService;
import com.neverrar.datacloudplatform.backend.util.Request;
import com.neverrar.datacloudplatform.backend.util.Result;
import com.neverrar.datacloudplatform.backend.view.AllProjectInfoByUser;
import com.neverrar.datacloudplatform.backend.view.AllTaskByProject;
import com.neverrar.datacloudplatform.backend.view.ProjectInformation;
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
    private AuthenticationService authenticationService;



    @PostMapping // Map ONLY POST Requests
    public @ResponseBody
    Result<ProjectInformation> addNewProject (@CookieValue(value = "sessionId",
            defaultValue = "noSession") String sessionId,@RequestBody CreateProjectRequest body) {
        User user=authenticationService.getUser(sessionId);
        if(user==null)  {
            return Result.wrapErrorResult(new InvalidSessionIdError());
        }
        return projectService.addNewProject(body,user);
    }

    @GetMapping
    public @ResponseBody Result<AllProjectInfoByUser> getOwnedProject (@CookieValue(value = "sessionId",
            defaultValue = "noSession") String sessionId) {
        User user=authenticationService.getUser(sessionId);
        if(user==null)  {
            return Result.wrapErrorResult(new InvalidSessionIdError());
        }
        return  projectService.getOwnedProject(user);
    }

    @GetMapping("{id}/tasks")
    public @ResponseBody Result<AllTaskByProject> getOwnedTask (@CookieValue(value = "sessionId",
            defaultValue = "noSession") String sessionId, @PathVariable Integer id) {
        User user=authenticationService.getUser(sessionId);
        if(user==null)  {
            return Result.wrapErrorResult(new InvalidSessionIdError());
        }
        return  projectService.getOwnedTask(user,id);
    }


    @GetMapping("{id}/testers")
    public @ResponseBody Result<AllTaskByProject> getOwnedTask (@CookieValue(value = "sessionId",
            defaultValue = "noSession") String sessionId, @PathVariable Integer id) {
        User user=authenticationService.getUser(sessionId);
        if(user==null)  {
            return Result.wrapErrorResult(new InvalidSessionIdError());
        }
        return  projectService.getOwnedTask(user,id);
    }

    @GetMapping("/{id}")
    public @ResponseBody Result<ProjectInformation> getProject (@CookieValue(value = "sessionId",
            defaultValue = "noSession") String sessionId, @PathVariable Integer id) {
        User user=authenticationService.getUser(sessionId);
        if(user==null)  {
            return Result.wrapErrorResult(new InvalidSessionIdError());
        }
        return projectService.getProject(user,id);
    }

    @PutMapping("/{id}")
    public @ResponseBody Result<ProjectInformation> updateProject (@CookieValue(value = "sessionId",
            defaultValue = "noSession") String sessionId, @RequestBody UpdateProjectRequest body,
                                                        @PathVariable Integer id) {
        User user=authenticationService.getUser(sessionId);
        if(user==null)  {
            return Result.wrapErrorResult(new InvalidSessionIdError());
        }
        return projectService.updateProject(body,id,user);
    }


    @DeleteMapping(path="/{id}")
    public @ResponseBody Result<String> deleteProject(@CookieValue(value = "sessionId",
            defaultValue = "noSession") String sessionId,@PathVariable Integer id) {
        User user=authenticationService.getUser(sessionId);
        if(user==null)  {
            return Result.wrapErrorResult(new InvalidSessionIdError());
        }
        return projectService.deleteProject(user,id);
    }
}
