package com.neverrar.datacloudplatform.backend.controller;

import com.neverrar.datacloudplatform.backend.error.*;
import com.neverrar.datacloudplatform.backend.model.Project;
import com.neverrar.datacloudplatform.backend.model.Tester;
import com.neverrar.datacloudplatform.backend.model.User;
import com.neverrar.datacloudplatform.backend.repository.ProjectRepository;
import com.neverrar.datacloudplatform.backend.repository.TesterRepository;
import com.neverrar.datacloudplatform.backend.request.CreateTesterRequest;
import com.neverrar.datacloudplatform.backend.request.UpdateTesterRequest;
import com.neverrar.datacloudplatform.backend.service.AuthenticationService;
import com.neverrar.datacloudplatform.backend.service.TesterService;
import com.neverrar.datacloudplatform.backend.util.Request;
import com.neverrar.datacloudplatform.backend.util.Result;
import com.neverrar.datacloudplatform.backend.view.TesterInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@Controller // This means that this class is a Controller
@RequestMapping(path="api/testers") // This means URL's start with /demo (after Application path)
public class TesterController {

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    private TesterService testerService;

    @PostMapping // Map ONLY POST Requests
    public @ResponseBody
    Result<TesterInformation> addNewTester (@CookieValue(value = "sessionId",
            defaultValue = "noSession") String sessionId, @RequestBody CreateTesterRequest body) {
        User user=authenticationService.getUser(sessionId);
        if(user==null)  {
            return Result.wrapErrorResult(new InvalidSessionIdError());
        }
        return testerService.addNewTester(body, user);
    }

    @GetMapping("/{id}")
    public @ResponseBody Result<TesterInformation> getTester (@CookieValue(value = "sessionId",
            defaultValue = "noSession") String sessionId,@PathVariable Integer id) {
        User user=authenticationService.getUser(sessionId);
        if(user==null)  {
            return Result.wrapErrorResult(new InvalidSessionIdError());
        }
        return testerService.getTester(user,id);
    }


    @PutMapping("/{id}")
    public @ResponseBody Result<TesterInformation> updateTester (@CookieValue(value = "sessionId",
            defaultValue = "noSession") String sessionId, @RequestBody UpdateTesterRequest body
            , @PathVariable Integer id) {
        User user=authenticationService.getUser(sessionId);
        if(user==null)  {
            return Result.wrapErrorResult(new InvalidSessionIdError());
        }
        return testerService.updateTester(body, id,user);
    }


    @DeleteMapping(path="/{id}")
    public @ResponseBody Result<String> deleteTester(@CookieValue(value = "sessionId",
            defaultValue = "noSession") String sessionId,@PathVariable Integer id) {
        User user=authenticationService.getUser(sessionId);
        if(user==null)  {
            return Result.wrapErrorResult(new InvalidSessionIdError());
        }
        return testerService.deleteTester(user,id);
    }
}
