package com.neverrar.datacloudplatform.backend.controller;

import com.neverrar.datacloudplatform.backend.error.*;
import com.neverrar.datacloudplatform.backend.model.*;
import com.neverrar.datacloudplatform.backend.repository.ProjectRepository;
import com.neverrar.datacloudplatform.backend.repository.TaskRepository;
import com.neverrar.datacloudplatform.backend.repository.TestRepository;
import com.neverrar.datacloudplatform.backend.repository.TesterRepository;
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
@RequestMapping(path="/tests") // This means URL's start with /demo (after Application path)
public class TestController {

    @Autowired
    private StringRedisTemplate template;

    @Autowired
    private TesterRepository testerRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TestRepository testRepository;

    @PostMapping // Map ONLY POST Requests
    public @ResponseBody
    Result<String> addNewTest (@RequestBody  Request<Test> request) {
        String userId=template.opsForValue().get(request.getSessionId());
        if(userId==null)  return Result.wrapErrorResult(new InvalidSessionIdError());
        Optional<Tester> optionalTester=testerRepository.findById(request.getData().getTester());
        if(!optionalTester.isPresent()) return Result.wrapErrorResult(new TesterNotExistedError());
        if(!optionalTester.get().getOwner().equals(userId))
            return Result.wrapErrorResult(new PermissionDeniedError());
        Optional<Task> optionalTask=taskRepository.findById(request.getData().getTask());
        if(!optionalTask.isPresent()) return Result.wrapErrorResult(new TaskNotExistedError());
        if(!optionalTask.get().getOwner().equals(userId))
            return Result.wrapErrorResult(new PermissionDeniedError());
        Test test=request.getData();
        test.setId(null);
        User user=new User();
        user.setId(userId);
        test.setOwner(user);
        test.setTask(optionalTask.get());
        test.setTester(optionalTester.get());
        test.setTestTime(new Date());
        testRepository.save(test);

        return Result.wrapSuccessfulResult("Saved");
    }

    @GetMapping("/{id}")
    public @ResponseBody Result<Test> getTest (@RequestParam String sessionId,@PathVariable Integer id) {
        String userId=template.opsForValue().get(sessionId);
        if(userId==null)  return Result.wrapErrorResult(new InvalidSessionIdError());
        Optional<Test> optionalTest= testRepository.findById(id);
        if(!optionalTest.isPresent()) return Result.wrapErrorResult(new TestNotExistedError());
        if(!userId.equals(optionalTest.get().getOwner())) return Result.wrapErrorResult(new PermissionDeniedError());
        return Result.wrapSuccessfulResult(optionalTest.get());
    }

    @GetMapping
    public @ResponseBody Result<Set<Test>> getAllTest (@RequestParam String sessionId,@RequestParam Integer taskId) {
        String userId=template.opsForValue().get(sessionId);
        if(userId==null)  return Result.wrapErrorResult(new InvalidSessionIdError());
        Optional<Task> optionalTask=taskRepository.findById(taskId);
        if(!optionalTask.isPresent()) return Result.wrapErrorResult(new TaskNotExistedError());
        if(!optionalTask.get().getOwner().equals(userId))
            return Result.wrapErrorResult(new PermissionDeniedError());
        return Result.wrapSuccessfulResult(optionalTask.get().getTestSet());
    }

    @DeleteMapping(path="/{id}")
    public @ResponseBody Result<String> deleteTest(@RequestParam String sessionId,@PathVariable Integer id) {
        String userId=template.opsForValue().get(sessionId);
        if(userId==null)  return Result.wrapErrorResult(new InvalidSessionIdError());
        Optional<Test> optionalTest=testRepository.findById(id);
        if(!optionalTest.isPresent()) return Result.wrapErrorResult(new TesterNotExistedError());
        if(!optionalTest.get().getOwner().equals(userId))
            return Result.wrapErrorResult(new PermissionDeniedError());
        testRepository.delete(optionalTest.get());
        return Result.wrapSuccessfulResult("Deleted");
    }
}
