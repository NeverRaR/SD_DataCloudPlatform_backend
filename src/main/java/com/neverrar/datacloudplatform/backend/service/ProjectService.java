package com.neverrar.datacloudplatform.backend.service;

import com.neverrar.datacloudplatform.backend.config.DataPathConfig;
import com.neverrar.datacloudplatform.backend.error.InvalidSessionIdError;
import com.neverrar.datacloudplatform.backend.error.PermissionDeniedError;
import com.neverrar.datacloudplatform.backend.error.ProjectNotExistedError;
import com.neverrar.datacloudplatform.backend.error.UserNotExistedError;
import com.neverrar.datacloudplatform.backend.model.*;
import com.neverrar.datacloudplatform.backend.repository.*;
import com.neverrar.datacloudplatform.backend.request.CreateProjectRequest;
import com.neverrar.datacloudplatform.backend.request.UpdateProjectRequest;
import com.neverrar.datacloudplatform.backend.util.DataParser;
import com.neverrar.datacloudplatform.backend.util.Request;
import com.neverrar.datacloudplatform.backend.util.Result;
import com.neverrar.datacloudplatform.backend.view.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.print.PrinterJob;
import java.util.*;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private TesterRepository testerRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private MainDataRepository mainDataRepository;

    @Autowired
    private DataPathConfig dataPathConfig;

    @Autowired
    private InteractionBehaviourDataRepository interactionBehaviourDataRepository;

    @Autowired
    private MarkDataRepository markDataRepository;

    @Autowired
    private LogEventDataRepository logEventDataRepository;

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private OSSService ossService;

    @Transactional
    public  Result<ProjectInformation> addNewProject (MultipartFile data, User user) {

        DataParser dataParser=new DataParser();
        dataParser.setPath(dataPathConfig.getBasePath());
        dataParser.setProjectRepository(projectRepository);
        dataParser.setTaskRepository(taskRepository);
        dataParser.setTesterRepository(testerRepository);
        dataParser.setTestRepository(testRepository);
        dataParser.setMainDataRepository(mainDataRepository);
        dataParser.setInteractionBehaviourDataRepository(interactionBehaviourDataRepository);
        dataParser.setOssService(ossService);
        dataParser.setVideoRepository(videoRepository);
        dataParser.setMarkDataRepository(markDataRepository);
        dataParser.setLogEventDataRepository(logEventDataRepository);


        dataParser.setOwner(user);
        dataParser.parseZip(data);
        dataParser.upload();
        dataParser.clear();
        return Result.wrapSuccessfulResult(new ProjectInformation(dataParser.getProject()));

        /*
        Project project=new Project();
        Date date=new Date();
        project.setCreateTime(date);
        project.setLastModified(date);
        project.setName(data.getName());
        project.setDescription(request.getDescription());
        project.setOwner(user);
        projectRepository.save(project);
        return Result.wrapSuccessfulResult(new ProjectInformation(project));
         */
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


    public Result<AllMainDataByTest> getLatestMain(User user){
        Set<Project> projectSet=user.projectSetInstance();
        if(projectSet==null&&projectSet.isEmpty()) {
            return null;
        }
        Project latestProject=projectSet.iterator().next();
        Integer maxId=latestProject.getId();
        for(Project project : projectSet){
            if(project.getId()>maxId) {
                latestProject=project;
                maxId=project.getId();
            }
        }
        Test test=latestProject.taskSetInstance().iterator().next().testSetInstance().iterator().next();
        AllMainDataByTest allMainDataByTest = new AllMainDataByTest(test.getMainDataSet());
        allMainDataByTest.setTestId(test.getId());
        return Result.wrapSuccessfulResult(allMainDataByTest);
    }

    public Result<AllProjectInfoByUser> getOwnedProject (User user) {
        if (user == null) {
            return Result.wrapErrorResult(new InvalidSessionIdError());
        }
        AllProjectInfoByUser result;
        if(user.getRole()==1){
            result = new AllProjectInfoByUser(projectRepository.findAll());
        }
        else {
            result = new AllProjectInfoByUser(user.projectSetInstance());
        }
        result.setUserId(user.getId());
        return Result.wrapSuccessfulResult(result);
    }

    public Result<AllTaskByProject> getOwnedTask (User user,Integer id) {
        Optional<Project> optionalProject= projectRepository.findById(id);
        if(!optionalProject.isPresent()) {
            return Result.wrapErrorResult(new ProjectNotExistedError());
        }
        if(user.getRole().equals(0)&& !user.getId().equals(optionalProject.get().getOwner().getId())) {
            return Result.wrapErrorResult(new PermissionDeniedError());
        }
        AllTaskByProject result=new AllTaskByProject(optionalProject.get().taskSetInstance());
        result.setProjectId(optionalProject.get().getId());
        return Result.wrapSuccessfulResult(result);

    }

    public Result<ProjectWithTask> getProjectStructure(User user,Integer id){
        Optional<Project> optionalProject= projectRepository.findById(id);
        if(!optionalProject.isPresent()) {
            return Result.wrapErrorResult(new ProjectNotExistedError());
        }
        if(user.getRole().equals(0)&& !user.getId().equals(optionalProject.get().getOwner().getId())) {
            return Result.wrapErrorResult(new PermissionDeniedError());
        }
        Project project=optionalProject.get();
        ProjectWithTask projectWithTask=new ProjectWithTask();
        projectWithTask.setProjectName(project.getName());
        projectWithTask.setProjectId(project.getId());
        projectWithTask.setOwnedTask(new LinkedList<>());
        for(Task task: project.taskSetInstance() ){
            HashMap<Integer, Tester> ownedTester=new HashMap<>();
            for(Test test :task.testSetInstance()){
                Tester tester=test.getTester();
                if(ownedTester.containsKey(tester.getId())){
                    continue;
                }
                ownedTester.put(test.getId(),tester);
            }
            TaskWithTester taskWithTester=new TaskWithTester();
            taskWithTester.setTaskId(task.getId());
            taskWithTester.setTaskName(task.getName());
            taskWithTester.setOwnerTester(new LinkedList<>());
            projectWithTask.getOwnedTask().add(taskWithTester);
            for(Tester tester:ownedTester.values()){
                TesterWithTest testerWithTest=new TesterWithTest();
                testerWithTest.setTesterId(tester.getId());
                testerWithTest.setTesterName(tester.getName());
                testerWithTest.setOwnedTest(new LinkedList<>());
                taskWithTester.getOwnerTester().add(testerWithTest);
                for(Test test:tester.testSetInstance()){
                    TestTag testTag=new TestTag(test);
                    testerWithTest.getOwnedTest().add(testTag);
                }
            }
        }
        return Result.wrapSuccessfulResult(projectWithTask);
    }

    public Result<AllTesterByProject> getOwnedTester (User user, Integer id) {
        Optional<Project> optionalProject= projectRepository.findById(id);
        if(!optionalProject.isPresent()) {
            return Result.wrapErrorResult(new ProjectNotExistedError());
        }
        if(user.getRole().equals(0)&& !user.getId().equals(optionalProject.get().getOwner().getId())) {
            return Result.wrapErrorResult(new PermissionDeniedError());
        }
        AllTesterByProject result=new AllTesterByProject(optionalProject.get().testerSetInstance());
        result.setProjectId(optionalProject.get().getId());
        return Result.wrapSuccessfulResult(result);

    }


    @Transactional
    public  Result<ProjectInformation> updateProject (UpdateProjectRequest body, Integer id, User user) {
        Optional<Project> optionalProject= projectRepository.findById(id);
        if(!optionalProject.isPresent()) {
            return Result.wrapErrorResult(new ProjectNotExistedError());
        }
        if(user.getRole().equals(0)&& !user.getId().equals(optionalProject.get().getOwner().getId())) {
            return Result.wrapErrorResult(new PermissionDeniedError());
        }
        Project project=optionalProject.get();
        Date date=new Date();
        project.setId(id);
        project.setLastModified(date);
        project.setOwner(user);
        project.setName(body.getName());
        project.setDescription(body.getDescription());
        projectRepository.save(project);
        return Result.wrapSuccessfulResult(new ProjectInformation(project));
    }

    @Transactional
    public Result<String> deleteProject(User user,Integer id) {
        Optional<Project> optionalProject= projectRepository.findById(id);
        if(!optionalProject.isPresent()){
            return Result.wrapErrorResult(new ProjectNotExistedError());
        }
        if(user.getRole().equals(0)&& !user.getId().equals(optionalProject.get().getOwner().getId())) {
            return Result.wrapErrorResult(new PermissionDeniedError());
        }

        projectRepository.delete(optionalProject.get());
        return Result.wrapSuccessfulResult("Deleted");
    }
}
