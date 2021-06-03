package com.neverrar.datacloudplatform.backend.controller;

import com.neverrar.datacloudplatform.backend.error.InvalidSessionIdError;
import com.neverrar.datacloudplatform.backend.model.User;
import com.neverrar.datacloudplatform.backend.service.AuthenticationService;
import com.neverrar.datacloudplatform.backend.service.LogEventDataService;
import com.neverrar.datacloudplatform.backend.util.Result;
import com.neverrar.datacloudplatform.backend.view.AllLogEventDataByTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller // This means that this class is a Controller
@RequestMapping(path="api/logeventdata") // This means URL's start with /demo (after Application path)
public class LogEventDataController {
    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private LogEventDataService logEventDataService;


    @GetMapping
    public @ResponseBody
    Result<AllLogEventDataByTest> getAllMainDataByTest (@CookieValue(value = "sessionId",
            defaultValue = "noSession") String sessionId, @RequestParam Integer testId) {
        User user=authenticationService.getUser(sessionId);
        if(user==null)  {
            return Result.wrapErrorResult(new InvalidSessionIdError());
        }
        return logEventDataService.getAllLogEventDataDataByTest(testId,user);
    }
}
