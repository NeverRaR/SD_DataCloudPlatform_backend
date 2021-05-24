package com.neverrar.datacloudplatform.backend.controller;

import com.neverrar.datacloudplatform.backend.error.InvalidSessionIdError;
import com.neverrar.datacloudplatform.backend.model.User;
import com.neverrar.datacloudplatform.backend.service.AuthenticationService;
import com.neverrar.datacloudplatform.backend.service.MainDataService;
import com.neverrar.datacloudplatform.backend.service.VideoService;
import com.neverrar.datacloudplatform.backend.util.Result;
import com.neverrar.datacloudplatform.backend.view.AllMainDataByTest;
import com.neverrar.datacloudplatform.backend.view.AllVideoByTest;
import com.neverrar.datacloudplatform.backend.view.VideoByTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller // This means that this class is a Controller
@RequestMapping(path="api/video") // This means URL's start with /demo (after Application path)
public class VideoController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private VideoService videoService;

    @GetMapping
    public @ResponseBody
    Result<AllVideoByTest> getAllMainDataByTest (@CookieValue(value = "sessionId",
            defaultValue = "noSession") String sessionId, @RequestParam Integer testId) {
        User user=authenticationService.getUser(sessionId);
        if(user==null)  {
            return Result.wrapErrorResult(new InvalidSessionIdError());
        }
        return videoService.getVideoByTest(testId,user);
    }
}
