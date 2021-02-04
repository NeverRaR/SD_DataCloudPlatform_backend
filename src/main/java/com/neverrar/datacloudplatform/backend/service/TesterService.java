package com.neverrar.datacloudplatform.backend.service;

import com.neverrar.datacloudplatform.backend.error.*;
import com.neverrar.datacloudplatform.backend.model.Project;
import com.neverrar.datacloudplatform.backend.model.Test;
import com.neverrar.datacloudplatform.backend.model.Tester;
import com.neverrar.datacloudplatform.backend.model.User;
import com.neverrar.datacloudplatform.backend.repository.ProjectRepository;
import com.neverrar.datacloudplatform.backend.repository.TestRepository;
import com.neverrar.datacloudplatform.backend.repository.TesterRepository;
import com.neverrar.datacloudplatform.backend.util.Result;
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
    public Result<String> addNewTester (Tester tester,String userId) {
        Optional<Project> optionalProject=projectRepository.findById(tester.getProject());
        if(!optionalProject.isPresent()) return Result.wrapErrorResult(new ProjectNotExistedError());
        if(!optionalProject.get().getOwner().equals(userId))
            return Result.wrapErrorResult(new PermissionDeniedError());
        String gender=tester.getGender();
        if("male".equals(gender)|| "female".equals(gender)) {
            User user = new User();
            user.setId(userId);
            tester.setOwner(user);
            tester.setId(null);
            tester.setProject(optionalProject.get());
            testerRepository.save(tester);
            return Result.wrapSuccessfulResult("Saved");
        }
        return Result.wrapErrorResult(new UnknownGenderError());
    }

    public Result<Tester> getTester ( String userId, Integer id) {
        Optional<Tester> optionalTester= testerRepository.findById(id);
        if(!optionalTester.isPresent()) return Result.wrapErrorResult(new TesterNotExistedError());
        if(!userId.equals(optionalTester.get().getOwner())) return Result.wrapErrorResult(new PermissionDeniedError());
        return Result.wrapSuccessfulResult(optionalTester.get());
    }

    public Result<Set<Tester>> getAllTester (String userId,Integer projectId) {
        Optional<Project> optionalProject=projectRepository.findById(projectId);
        if(!optionalProject.isPresent()) return Result.wrapErrorResult(new ProjectNotExistedError());
        if(!optionalProject.get().getOwner().equals(userId))
            return Result.wrapErrorResult(new PermissionDeniedError());
        return Result.wrapSuccessfulResult(optionalProject.get().testerSetInstance());
    }

    @Transactional
    public Result<Tester> updateTester (Tester tester, Integer id,String userId) {
        Optional<Tester> optionalTester=testerRepository.findById(id);
        if(!optionalTester.isPresent()) return Result.wrapErrorResult(new TesterNotExistedError());
        if(!optionalTester.get().getOwner().equals(userId))
            return Result.wrapErrorResult(new PermissionDeniedError());
        String gender=tester.getGender();
        if(gender.equals("male")||gender.equals("female")) {
            tester.setId(id);
            Project project = new Project();
            project.setId(optionalTester.get().getProject());
            tester.setProject(project);
            User user = new User();
            user.setId(userId);
            tester.setOwner(user);
            testerRepository.save(tester);
            return Result.wrapSuccessfulResult(tester);
        }
        return Result.wrapErrorResult(new UnknownGenderError());
    }

    @Transactional
    public Result<String> deleteTester(String userId,Integer id) {

        Optional<Tester> optionalTester=testerRepository.findById(id);
        if(!optionalTester.isPresent()) return Result.wrapErrorResult(new TesterNotExistedError());
        if(!optionalTester.get().getOwner().equals(userId))
            return Result.wrapErrorResult(new PermissionDeniedError());
        for(Test test: optionalTester.get().testSetInstance()){
                       //mongoDB级联删除
        }
        testRepository.deleteByTesterId(id);
        testerRepository.delete(optionalTester.get());
        return Result.wrapSuccessfulResult("Deleted");
    }
}
