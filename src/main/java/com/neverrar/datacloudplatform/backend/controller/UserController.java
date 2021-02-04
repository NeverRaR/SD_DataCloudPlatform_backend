package com.neverrar.datacloudplatform.backend.controller;

import com.neverrar.datacloudplatform.backend.error.*;
import com.neverrar.datacloudplatform.backend.model.User;
import com.neverrar.datacloudplatform.backend.repository.UserRepository;
import com.neverrar.datacloudplatform.backend.request.RegisterRequest;
import com.neverrar.datacloudplatform.backend.service.AuthenticationService;
import com.neverrar.datacloudplatform.backend.service.UserService;
import com.neverrar.datacloudplatform.backend.util.HashHelper;
import com.neverrar.datacloudplatform.backend.util.Result;
import com.neverrar.datacloudplatform.backend.view.UserInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@Controller // This means that this class is a Controller
@RequestMapping(path="api/users") // This means URL's start with /demo (after Application path)
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping // Map ONLY POST Requests
    public @ResponseBody
    Result<UserInformation> addNewUser (@RequestBody RegisterRequest body) {

        User user=userService.addNewUser(body);
        if(user==null) return  Result.wrapErrorResult(new UserAlreadyExistedError());
        return Result.wrapSuccessfulResult(new UserInformation(user));

    }

    @GetMapping
    public @ResponseBody
    Result<UserInformation> getUser (@CookieValue(value = "sessionId",
            defaultValue = "noSession") String sessionId) {
        User user=authenticationService.getUser(sessionId);
        if(user==null) {
            return Result.wrapErrorResult(new InvalidSessionIdError());
        }
        return Result.wrapSuccessfulResult(new UserInformation(user));
    }

    @PostMapping(path="/session")
    public @ResponseBody Result<UserInformation> getLoginToken(@RequestBody User body,
                                                      HttpServletResponse response, HttpServletRequest request) {

        String sessionId=authenticationService.createSessionId(body.getId(),body.getPassword());
        if(sessionId==null){
            response.setStatus(401);
            return null;
        }
        ResponseCookie responseCookie = ResponseCookie.from("sessionId", sessionId)
                .maxAge(3* 24* 60 * 60)
                .httpOnly(true)
                .path("/")
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, responseCookie.toString());

        User user=authenticationService.getUser(sessionId);
        return Result.wrapSuccessfulResult(new UserInformation(user));
    }

    @DeleteMapping(path="/session")
    public @ResponseBody
    Result<String> logout(@CookieValue(value = "sessionId",
            defaultValue = "noSession") String sessionId,HttpServletResponse response){
        authenticationService.invalidateSessionId(sessionId);
        Cookie cookie=new Cookie("sessionId",null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return Result.wrapSuccessfulResult("logout!");
    }
}
