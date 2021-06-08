package com.neverrar.datacloudplatform.backend.service;

import com.neverrar.datacloudplatform.backend.error.*;
import com.neverrar.datacloudplatform.backend.model.Task;
import com.neverrar.datacloudplatform.backend.model.Test;
import com.neverrar.datacloudplatform.backend.model.Tester;
import com.neverrar.datacloudplatform.backend.model.User;
import com.neverrar.datacloudplatform.backend.repository.TaskRepository;
import com.neverrar.datacloudplatform.backend.repository.TestRepository;
import com.neverrar.datacloudplatform.backend.repository.TesterRepository;
import com.neverrar.datacloudplatform.backend.request.CreateTestRequest;
import com.neverrar.datacloudplatform.backend.util.Request;
import com.neverrar.datacloudplatform.backend.util.Result;
import com.neverrar.datacloudplatform.backend.view.AllProjectInfoByUser;
import com.neverrar.datacloudplatform.backend.view.TestInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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


    @Transactional
    public Result<TestInformation> addNewTest (CreateTestRequest body, User user) {

        Optional<Tester> optionalTester=testerRepository.findById(body.getTesterId());
        if(!optionalTester.isPresent()) {
            return Result.wrapErrorResult(new TesterNotExistedError());
        }
        if(user.getRole().equals(0)&& !optionalTester.get().getOwner().getId().equals(user.getId())){
            return Result.wrapErrorResult(new PermissionDeniedError());
        }
        Optional<Task> optionalTask=taskRepository.findById(body.getTaskId());
        if(!optionalTask.isPresent()) {
            return Result.wrapErrorResult(new TaskNotExistedError());
        }
        if(user.getRole().equals(0)&& !optionalTask.get().getOwner().getId().equals(user.getId())) {
            return Result.wrapErrorResult(new PermissionDeniedError());
        }
        Test test=new Test();
        test.setOwner(user);
        test.setTask(optionalTask.get());
        test.setTester(optionalTester.get());
        testRepository.save(test);

        return Result.wrapSuccessfulResult(new TestInformation(test));
    }

    public Result<TestInformation> getTest (User user,Integer id) {
        Optional<Test> optionalTest= testRepository.findById(id);
        if(!optionalTest.isPresent()) {
            return Result.wrapErrorResult(new TestNotExistedError());
        }
        if(user.getRole().equals(0) && !user.getId().equals(optionalTest.get().getOwner().getId())) {
            return Result.wrapErrorResult(new PermissionDeniedError());
        }
        return Result.wrapSuccessfulResult(new TestInformation(optionalTest.get()));
    }



    public Result<String> deleteTest(User user,Integer id) {
        Optional<Test> optionalTest=testRepository.findById(id);
        if(!optionalTest.isPresent()) {
            return Result.wrapErrorResult(new TesterNotExistedError());
        }
        if(user.getRole().equals(0) && !optionalTest.get().getOwner().getId().equals(user.getId())){
            return Result.wrapErrorResult(new PermissionDeniedError());
        }
        testRepository.delete(optionalTest.get());
        return Result.wrapSuccessfulResult("Deleted");
    }
}
