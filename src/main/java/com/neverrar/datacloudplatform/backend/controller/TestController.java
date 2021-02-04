package com.neverrar.datacloudplatform.backend.controller;

import com.neverrar.datacloudplatform.backend.error.*;
import com.neverrar.datacloudplatform.backend.model.*;
import com.neverrar.datacloudplatform.backend.repository.ProjectRepository;
import com.neverrar.datacloudplatform.backend.repository.TaskRepository;
import com.neverrar.datacloudplatform.backend.repository.TestRepository;
import com.neverrar.datacloudplatform.backend.repository.TesterRepository;
import com.neverrar.datacloudplatform.backend.service.TestService;
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
@RequestMapping(path="api/tests") // This means URL's start with /demo (after Application path)
public class TestController {

    @Autowired
    private StringRedisTemplate template;

    @Autowired
    private TestService testService;

    @PostMapping // Map ONLY POST Requests
    public @ResponseBody
    Result<String> addNewTest (@CookieValue(value = "sessionId",
            defaultValue = "noSession") String sessionId,@RequestBody  Request<Test> request) {
        String userId=template.opsForValue().get(request.getSessionId());
        if(userId==null)  return Result.wrapErrorResult(new InvalidSessionIdError());
        return testService.addNewTest(request.getData(), userId);
    }

    @GetMapping("/{id}")
    public @ResponseBody Result<Test> getTest (@CookieValue(value = "sessionId",
            defaultValue = "noSession") String sessionId,@PathVariable Integer id) {
        String userId=template.opsForValue().get(sessionId);
        if(userId==null)  return Result.wrapErrorResult(new InvalidSessionIdError());
        return testService.getTest(userId,id);
    }

    @GetMapping
    public @ResponseBody Result<Set<Test>> getAllTest (@CookieValue(value = "sessionId",
            defaultValue = "noSession") String sessionId,@RequestParam Integer taskId) {
        String userId=template.opsForValue().get(sessionId);
        if(userId==null)  return Result.wrapErrorResult(new InvalidSessionIdError());
        return testService.getAllTest(userId,taskId);
    }

    @DeleteMapping(path="/{id}")
    public @ResponseBody Result<String> deleteTest(@CookieValue(value = "sessionId",
            defaultValue = "noSession") String sessionId,@PathVariable Integer id) {
        String userId=template.opsForValue().get(sessionId);
        if(userId==null)  return Result.wrapErrorResult(new InvalidSessionIdError());
        return testService.deleteTest(userId,id);
    }
}
