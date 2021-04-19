package com.neverrar.datacloudplatform.backend.service;

import com.neverrar.datacloudplatform.backend.error.InvalidSessionIdError;
import com.neverrar.datacloudplatform.backend.error.PermissionDeniedError;
import com.neverrar.datacloudplatform.backend.error.ProjectNotExistedError;
import com.neverrar.datacloudplatform.backend.error.UserNotExistedError;
import com.neverrar.datacloudplatform.backend.model.Project;
import com.neverrar.datacloudplatform.backend.model.Task;
import com.neverrar.datacloudplatform.backend.model.Test;
import com.neverrar.datacloudplatform.backend.model.User;
import com.neverrar.datacloudplatform.backend.repository.*;
import com.neverrar.datacloudplatform.backend.request.CreateProjectRequest;
import com.neverrar.datacloudplatform.backend.request.UpdateProjectRequest;
import com.neverrar.datacloudplatform.backend.util.Request;
import com.neverrar.datacloudplatform.backend.util.Result;
import com.neverrar.datacloudplatform.backend.view.AllProjectInfoByUser;
import com.neverrar.datacloudplatform.backend.view.ProjectInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Transactional
    public  Result<ProjectInformation> addNewProject (CreateProjectRequest request, User user) {

        Project project=new Project();
        Date date=new Date();
        project.setCreateTime(date);
        project.setLastModified(date);
        project.setName(request.getName());
        project.setDescription(request.getDescription());
        project.setOwner(user);
        projectRepository.save(project);
        return Result.wrapSuccessfulResult(new ProjectInformation(project));
    }

    public Result<ProjectInformation> getProject (User user, Integer id) {
        Optional<Project> optionalProject= projectRepository.findById(id);
        if(!optionalProject.isPresent()) {
            return Result.wrapErrorResult(new ProjectNotExistedError());
        }
        if(user.getRole()==1) {
            return Result.wrapSuccessfulResult(new ProjectInformation(optionalProject.get()));
        }
        if(!user.getId().equals(optionalProject.get().getOwner().getId())) {
            return Result.wrapErrorResult(new PermissionDeniedError());
        }
        return Result.wrapSuccessfulResult(new ProjectInformation(optionalProject.get()));
    }


    public Result<AllProjectInfoByUser> getOwnedProject (User user) {
        if (user == null) {
            return Result.wrapErrorResult(new InvalidSessionIdError());
        }
        AllProjectInfoByUser result=new AllProjectInfoByUser(user.projectSetInstance());
        result.setUserId(user.getId());
        return Result.wrapSuccessfulResult(result);
    }

    @Transactional
    public  Result<ProjectInformation> updateProject (UpdateProjectRequest body, Integer id, User user) {
        Optional<Project> optionalProject= projectRepository.findById(id);
        if(!optionalProject.isPresent()) {
            return Result.wrapErrorResult(new ProjectNotExistedError());
        }
        if(!user.getId().equals(optionalProject.get().getOwner().getId())) {
            return Result.wrapErrorResult(new PermissionDeniedError());
        }
        Project project=optionalProject.get();
        Date date=new Date();
        project.setId(id);
        project.setLastModified(date);
        project.setOwner(user);
        projectRepository.save(project);
        return Result.wrapSuccessfulResult(new ProjectInformation(project));
    }

    @Transactional
    public Result<String> deleteProject(User user,Integer id) {
        Optional<Project> optionalProject= projectRepository.findById(id);
        if(!optionalProject.isPresent()){
            return Result.wrapErrorResult(new ProjectNotExistedError());
        }
        if(!user.getId().equals(optionalProject.get().getOwner().getId())) {
            return Result.wrapErrorResult(new PermissionDeniedError());
        }
        return Result.wrapSuccessfulResult("Deleted");
    }
}
