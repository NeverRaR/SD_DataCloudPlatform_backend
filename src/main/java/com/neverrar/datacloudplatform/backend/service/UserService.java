package com.neverrar.datacloudplatform.backend.service;

import com.neverrar.datacloudplatform.backend.error.UserAlreadyExistedError;
import com.neverrar.datacloudplatform.backend.model.User;
import com.neverrar.datacloudplatform.backend.repository.UserRepository;
import com.neverrar.datacloudplatform.backend.request.RegisterRequest;
import com.neverrar.datacloudplatform.backend.util.HashHelper;
import com.neverrar.datacloudplatform.backend.util.Result;
import com.neverrar.datacloudplatform.backend.view.UserInformation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Transactional
    public User addNewUser (RegisterRequest registerRequest) {
        Optional<User> opt=userRepository.findById(registerRequest.getId());
        if(opt.isPresent()) return null;
        User n = new User();
        n.setNickname(registerRequest.getNickname());
        double seed= ThreadLocalRandom.current().nextDouble();
        n.setSalt(HashHelper.computeSha256Hash(registerRequest.getId()+ seed));
        n.setPassword(HashHelper.computeSha256Hash(registerRequest.getPassword()+n.getSalt()));
        n.setId(registerRequest.getId());
        n.setRole(0);
        userRepository.save(n);
        return n;
    }

}
