package com.neverrar.datacloudplatform.backend.service;

import com.neverrar.datacloudplatform.backend.error.*;
import com.neverrar.datacloudplatform.backend.model.Project;
import com.neverrar.datacloudplatform.backend.model.Test;
import com.neverrar.datacloudplatform.backend.model.Tester;
import com.neverrar.datacloudplatform.backend.model.User;
import com.neverrar.datacloudplatform.backend.repository.ProjectRepository;
import com.neverrar.datacloudplatform.backend.repository.TestRepository;
import com.neverrar.datacloudplatform.backend.repository.TesterRepository;
import com.neverrar.datacloudplatform.backend.request.CreateTaskRequest;
import com.neverrar.datacloudplatform.backend.request.CreateTesterRequest;
import com.neverrar.datacloudplatform.backend.request.UpdateTesterRequest;
import com.neverrar.datacloudplatform.backend.util.Result;
import com.neverrar.datacloudplatform.backend.view.TesterInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Service
public class TesterService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TesterRepository testerRepository;

    @Autowired
    private TestRepository testRepository;

    @Transactional
    public Result<TesterInformation> addNewTester (CreateTesterRequest body, User user) {
        Optional<Project> optionalProject=projectRepository.findById(body.getProjectId());
        if(!optionalProject.isPresent()) {
            return Result.wrapErrorResult(new ProjectNotExistedError());
        }
        if(!optionalProject.get().getOwner().getId().equals(user.getId())) {
            return Result.wrapErrorResult(new PermissionDeniedError());
        }
        String gender=body.getGender();
        Tester tester=new Tester();
        if("male".equals(gender)|| "female".equals(gender)) {
            tester.setOwner(user);
            tester.setProject(optionalProject.get());
            tester.setDrivingYears(body.getDrivingYears());
            tester.setEducation(body.getEducation());
            tester.setGender(body.getGender());
            tester.setName(body.getName());
            tester.setAge(body.getAge());
            testerRepository.save(tester);
            return Result.wrapSuccessfulResult(new TesterInformation(tester));
        }
        return Result.wrapErrorResult(new UnknownGenderError());
    }

    public Result<TesterInformation> getTester ( User user, Integer id) {
        Optional<Tester> optionalTester= testerRepository.findById(id);
        if(!optionalTester.isPresent()) {
            return Result.wrapErrorResult(new TesterNotExistedError());
        }
        if(!user.getId().equals(optionalTester.get().getOwner().getId())) {
            return Result.wrapErrorResult(new PermissionDeniedError());
        }
        return Result.wrapSuccessfulResult(new TesterInformation(optionalTester.get()));
    }


    @Transactional
    public Result<TesterInformation> updateTester (UpdateTesterRequest body, Integer id, User user) {
        Optional<Tester> optionalTester=testerRepository.findById(id);
        if(!optionalTester.isPresent()) {
            return Result.wrapErrorResult(new TesterNotExistedError());
        }
        String gender=body.getGender();
        Tester tester=optionalTester.get();
        if("male".equals(gender)|| "female".equals(gender)) {
            tester.setDrivingYears(body.getDrivingYears());
            tester.setEducation(body.getEducation());
            tester.setGender(body.getGender());
            tester.setName(body.getName());
            tester.setAge(body.getAge());
            testerRepository.save(tester);
            return Result.wrapSuccessfulResult(new TesterInformation(tester));
        }
        return Result.wrapErrorResult(new UnknownGenderError());
    }

    @Transactional
    public Result<String> deleteTester(User user,Integer id) {

        Optional<Tester> optionalTester=testerRepository.findById(id);
        if(!optionalTester.isPresent()) {
            return Result.wrapErrorResult(new TesterNotExistedError());
        }
        if(!optionalTester.get().getOwner().getId().equals(user.getId())) {
            return Result.wrapErrorResult(new PermissionDeniedError());
        }
        testerRepository.delete(optionalTester.get());
        return Result.wrapSuccessfulResult("Deleted");
    }
}
