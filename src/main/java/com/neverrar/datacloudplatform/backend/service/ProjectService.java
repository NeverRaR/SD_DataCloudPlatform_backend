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
import com.neverrar.datacloudplatform.backend.util.Request;
import com.neverrar.datacloudplatform.backend.util.Result;
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
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TesterRepository testerRepository;

    @Autowired
    private TestRepository testRepository;




    public Result<String> addNewProject (Project data,String userId) {
        Date date=new Date();
        Project project=data;
        project.setCreateTime(date);
        project.setLastModified(date);
        User user=new User();
        user.setId(userId);
        project.setOwner(user);
        projectRepository.save(project);
        return Result.wrapSuccessfulResult("Saved");
    }

    public Result<Project> getProject (String userId, Integer id) {
        Optional<Project> optionalProject= projectRepository.findById(id);
        if(!optionalProject.isPresent()) return Result.wrapErrorResult(new ProjectNotExistedError());
        if(!userId.equals(optionalProject.get().getOwner())) return Result.wrapErrorResult(new PermissionDeniedError());
        return Result.wrapSuccessfulResult(optionalProject.get());
    }

    public Result<Set<Project>> getAllProject (String userId) {
        Optional<User> optionalUser= userRepository.findById(userId);
        return optionalUser.map(user -> Result.wrapSuccessfulResult(user.getProjectSet()))
                .orElseGet(() -> Result.wrapErrorResult(new UserNotExistedError()));
    }

    public  Result<Project> updateProject (Project data, Integer id,String userId) {
        Optional<Project> optionalProject= projectRepository.findById(id);
        if(!optionalProject.isPresent()) return Result.wrapErrorResult(new ProjectNotExistedError());
        if(!userId.equals(optionalProject.get().getOwner())) return Result.wrapErrorResult(new PermissionDeniedError());
        Date date=new Date();
        Project project=new Project();
        project.setId(id);
        project.setLastModified(date);
        User user=new User();
        user.setId(userId);
        project.setOwner(user);
        project.setDescription(data.getDescription());
        project.setName(data.getName());
        projectRepository.save(project);
        return Result.wrapSuccessfulResult(project);
    }

    @Transactional
    public Result<String> deleteProject(String userId,Integer id) {
        Optional<Project> optionalProject= projectRepository.findById(id);
        if(!optionalProject.isPresent()) return Result.wrapErrorResult(new ProjectNotExistedError());
        if(!userId.equals(optionalProject.get().getOwner()))
            return Result.wrapErrorResult(new PermissionDeniedError());
        Set<Task> taskSet=optionalProject.get().getTaskSet();
        for(Task task:taskSet){
            testRepository.deleteByTask(task);
        }
        testerRepository.deleteByProject(optionalProject.get());
        taskRepository.deleteByProject(optionalProject.get());
        projectRepository.delete(optionalProject.get());
        return Result.wrapSuccessfulResult("Deleted");
    }
}
