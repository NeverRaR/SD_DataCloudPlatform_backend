package com.neverrar.datacloudplatform.backend.controller;

import com.neverrar.datacloudplatform.backend.error.*;
import com.neverrar.datacloudplatform.backend.model.Project;
import com.neverrar.datacloudplatform.backend.model.Tester;
import com.neverrar.datacloudplatform.backend.model.User;
import com.neverrar.datacloudplatform.backend.repository.ProjectRepository;
import com.neverrar.datacloudplatform.backend.repository.TesterRepository;
import com.neverrar.datacloudplatform.backend.service.TesterService;
import com.neverrar.datacloudplatform.backend.util.Request;
import com.neverrar.datacloudplatform.backend.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@Controller // This means that this class is a Controller
@RequestMapping(path="/testers") // This means URL's start with /demo (after Application path)
public class TesterController {

    @Autowired
    private StringRedisTemplate template;

    @Autowired
    private TesterService testerService;

    @PostMapping // Map ONLY POST Requests
    public @ResponseBody
    Result<String> addNewTester (@RequestBody  Request<Tester> request) {
        String userId=template.opsForValue().get(request.getSessionId());
        if(userId==null)  return Result.wrapErrorResult(new InvalidSessionIdError());
        return testerService.addNewTester(request.getData(), userId);
    }

    @GetMapping("/{id}")
    public @ResponseBody Result<Tester> getTester (@RequestParam String sessionId,@PathVariable Integer id) {
        String userId=template.opsForValue().get(sessionId);
        if(userId==null)  return Result.wrapErrorResult(new InvalidSessionIdError());
        return testerService.getTester(userId,id);
    }

    @GetMapping
    public @ResponseBody Result<Set<Tester>> getAllTester (@RequestParam String sessionId,@RequestParam Integer projectId) {
        String userId=template.opsForValue().get(sessionId);
        if(userId==null)  return Result.wrapErrorResult(new InvalidSessionIdError());
        return testerService.getAllTester(userId,projectId);
    }

    @PutMapping("/{id}")
    public @ResponseBody Result<Tester> updateTester (@RequestBody Request<Tester> request, @PathVariable Integer id) {
        String userId=template.opsForValue().get(request.getSessionId());
        if(userId==null)  return Result.wrapErrorResult(new InvalidSessionIdError());
        return testerService.updateTester(request.getData(), id,userId);
    }


    @DeleteMapping(path="/{id}")
    public @ResponseBody Result<String> deleteTester(@RequestParam String sessionId,@PathVariable Integer id) {
        String userId=template.opsForValue().get(sessionId);
        if(userId==null)  return Result.wrapErrorResult(new InvalidSessionIdError());
        return testerService.deleteTester(userId,id);
    }
}
