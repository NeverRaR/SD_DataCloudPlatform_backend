package com.neverrar.datacloudplatform.backend.service;

import com.neverrar.datacloudplatform.backend.model.User;
import com.neverrar.datacloudplatform.backend.repository.UserRepository;
import com.neverrar.datacloudplatform.backend.util.HashHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@Service
public class AuthenticationService {
    @Autowired
    private StringRedisTemplate template;

    @Autowired
    private UserRepository userDAO;

    public User getUser(String sessionId) {

        if(sessionId == null) return null;
        String id=template.opsForValue().get(sessionId);
        if(id == null) {
            return null;
        }
        Optional<User> optionalUser= userDAO.findById(id);

        if(!optionalUser.isPresent()) return null;
        return  optionalUser.get();
    }

    public String createSessionId(String username,String password){
        Optional<User> optionalUser = userDAO.findById(username);
        if(!optionalUser.isPresent()){
            return null;
        }
        User user=optionalUser.get();
        String passwordHashed= HashHelper.computeSha256Hash(password+user.getSalt());
        if(!user.getPassword().equals(passwordHashed)){
            return null;
        }

        double seed= ThreadLocalRandom.current().nextDouble();
        String sessionId=HashHelper.computeMD5Hash(user.getId()+ seed);
        template.opsForValue().set(sessionId,user.getId().toString(),72, TimeUnit.HOURS);

        return sessionId;

    }

    public void invalidateSessionId(String sessionId){
        template.delete(sessionId);
    }

}