package com.neverrar.datacloudplatform.backend.controller;

import com.neverrar.datacloudplatform.backend.error.InvalidSessionIdError;
import com.neverrar.datacloudplatform.backend.model.MarkData;
import com.neverrar.datacloudplatform.backend.model.User;
import com.neverrar.datacloudplatform.backend.service.AuthenticationService;
import com.neverrar.datacloudplatform.backend.service.MainDataService;
import com.neverrar.datacloudplatform.backend.service.MarkDataService;
import com.neverrar.datacloudplatform.backend.util.Result;
import com.neverrar.datacloudplatform.backend.view.AllMainDataByTest;
import com.neverrar.datacloudplatform.backend.view.AllMarkDataByTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.yaml.snakeyaml.error.Mark;

@Controller // This means that this class is a Controller
@RequestMapping(path="api/markdata") // This means URL's start with /demo (after Application path)
public class MarkDataController {
    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private MarkDataService markDataService;

    @GetMapping
    public @ResponseBody
    Result<AllMarkDataByTest> getAllMarkDataByTest (@CookieValue(value = "sessionId",
            defaultValue = "noSession") String sessionId, @RequestParam Integer testId) {
        User user=authenticationService.getUser(sessionId);
        if(user==null)  {
            return Result.wrapErrorResult(new InvalidSessionIdError());
        }
        return markDataService.getAllMarkDataByTest(testId,user);
    }
}
