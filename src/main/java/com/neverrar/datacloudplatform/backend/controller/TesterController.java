package com.neverrar.datacloudplatform.backend.controller;

import com.neverrar.datacloudplatform.backend.error.*;
import com.neverrar.datacloudplatform.backend.model.Project;
import com.neverrar.datacloudplatform.backend.model.Tester;
import com.neverrar.datacloudplatform.backend.model.User;
import com.neverrar.datacloudplatform.backend.repository.ProjectRepository;
import com.neverrar.datacloudplatform.backend.repository.TesterRepository;
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
    private ProjectRepository projectRepository;

    @Autowired
    private TesterRepository testerRepository;

    @PostMapping // Map ONLY POST Requests
    public @ResponseBody
    Result<String> addNewTester (@RequestBody  Request<Tester> request) {
        String userId=template.opsForValue().get(request.getSessionId());
        if(userId==null)  return Result.wrapErrorResult(new InvalidSessionIdError());
        Optional<Project> optionalProject=projectRepository.findById(request.getData().getProject());
        if(!optionalProject.isPresent()) return Result.wrapErrorResult(new ProjectNotExistedError());
        if(!optionalProject.get().getOwner().equals(userId))
            return Result.wrapErrorResult(new PermissionDeniedError());
        String gender=request.getData().getGender();
        if(gender.equals("male")||gender.equals("female")) {
            Tester tester = request.getData();
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

    @GetMapping("/{id}")
    public @ResponseBody Result<Tester> getTester (@RequestParam String sessionId,@PathVariable Integer id) {
        String userId=template.opsForValue().get(sessionId);
        if(userId==null)  return Result.wrapErrorResult(new InvalidSessionIdError());
        Optional<Tester> optionalTester= testerRepository.findById(id);
        if(!optionalTester.isPresent()) return Result.wrapErrorResult(new TesterNotExistedError());
        if(!userId.equals(optionalTester.get().getOwner())) return Result.wrapErrorResult(new PermissionDeniedError());
        return Result.wrapSuccessfulResult(optionalTester.get());
    }

    @GetMapping
    public @ResponseBody Result<Set<Tester>> getAllTester (@RequestParam String sessionId,@RequestParam Integer projectId) {
        String userId=template.opsForValue().get(sessionId);
        if(userId==null)  return Result.wrapErrorResult(new InvalidSessionIdError());
        Optional<Project> optionalProject=projectRepository.findById(projectId);
        if(!optionalProject.isPresent()) return Result.wrapErrorResult(new ProjectNotExistedError());
        if(!optionalProject.get().getOwner().equals(userId))
            return Result.wrapErrorResult(new PermissionDeniedError());
        return Result.wrapSuccessfulResult(optionalProject.get().getTesterSet());
    }

    @PutMapping("/{id}")
    public @ResponseBody Result<Tester> updateTester (@RequestBody Request<Tester> request, @PathVariable Integer id) {
        String userId=template.opsForValue().get(request.getSessionId());
        if(userId==null)  return Result.wrapErrorResult(new InvalidSessionIdError());
        Optional<Tester> optionalTester=testerRepository.findById(id);
        if(!optionalTester.isPresent()) return Result.wrapErrorResult(new TesterNotExistedError());
        if(!optionalTester.get().getOwner().equals(userId))
            return Result.wrapErrorResult(new PermissionDeniedError());
        String gender=request.getData().getGender();
        if(gender.equals("male")||gender.equals("female")) {
            Tester tester = request.getData();
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


    @DeleteMapping(path="/{id}")
    public @ResponseBody Result<String> deleteTester(@RequestParam String sessionId,@PathVariable Integer id) {
        String userId=template.opsForValue().get(sessionId);
        if(userId==null)  return Result.wrapErrorResult(new InvalidSessionIdError());
        Optional<Tester> optionalTester=testerRepository.findById(id);
        if(!optionalTester.isPresent()) return Result.wrapErrorResult(new TesterNotExistedError());
        if(!optionalTester.get().getOwner().equals(userId))
            return Result.wrapErrorResult(new PermissionDeniedError());
        testerRepository.delete(optionalTester.get());
        return Result.wrapSuccessfulResult("Deleted");
    }
}
