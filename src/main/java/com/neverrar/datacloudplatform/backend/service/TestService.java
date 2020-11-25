package com.neverrar.datacloudplatform.backend.service;

import com.neverrar.datacloudplatform.backend.error.*;
import com.neverrar.datacloudplatform.backend.model.Task;
import com.neverrar.datacloudplatform.backend.model.Test;
import com.neverrar.datacloudplatform.backend.model.Tester;
import com.neverrar.datacloudplatform.backend.model.User;
import com.neverrar.datacloudplatform.backend.repository.TaskRepository;
import com.neverrar.datacloudplatform.backend.repository.TestRepository;
import com.neverrar.datacloudplatform.backend.repository.TesterRepository;
import com.neverrar.datacloudplatform.backend.util.Request;
import com.neverrar.datacloudplatform.backend.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

@Service
public class TestService {
    @Autowired
    private TesterRepository testerRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TestRepository testRepository;


    public Result<String> addNewTest (Test test,String userId) {

        Optional<Tester> optionalTester=testerRepository.findById(test.getTester());
        if(!optionalTester.isPresent()) return Result.wrapErrorResult(new TesterNotExistedError());
        if(!optionalTester.get().getOwner().equals(userId))
            return Result.wrapErrorResult(new PermissionDeniedError());
        Optional<Task> optionalTask=taskRepository.findById(test.getTask());
        if(!optionalTask.isPresent()) return Result.wrapErrorResult(new TaskNotExistedError());
        if(!optionalTask.get().getOwner().equals(userId))
            return Result.wrapErrorResult(new PermissionDeniedError());
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

    public Result<Test> getTest (String userId,Integer id) {
        if(userId==null)  return Result.wrapErrorResult(new InvalidSessionIdError());
        Optional<Test> optionalTest= testRepository.findById(id);
        if(!optionalTest.isPresent()) return Result.wrapErrorResult(new TestNotExistedError());
        if(!userId.equals(optionalTest.get().getOwner())) return Result.wrapErrorResult(new PermissionDeniedError());
        return Result.wrapSuccessfulResult(optionalTest.get());
    }

    public Result<Set<Test>> getAllTest (String userId, Integer taskId) {
        Optional<Task> optionalTask=taskRepository.findById(taskId);
        if(!optionalTask.isPresent()) return Result.wrapErrorResult(new TaskNotExistedError());
        if(!optionalTask.get().getOwner().equals(userId))
            return Result.wrapErrorResult(new PermissionDeniedError());
        return Result.wrapSuccessfulResult(optionalTask.get().testSetInstance());
    }

    public Result<String> deleteTest(String userId,Integer id) {
        Optional<Test> optionalTest=testRepository.findById(id);
        if(!optionalTest.isPresent()) return Result.wrapErrorResult(new TesterNotExistedError());
        if(!optionalTest.get().getOwner().equals(userId))
            return Result.wrapErrorResult(new PermissionDeniedError());
        testRepository.delete(optionalTest.get());

                       //mongoDB级联删除

        return Result.wrapSuccessfulResult("Deleted");
    }
}
