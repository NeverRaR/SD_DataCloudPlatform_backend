package com.neverrar.datacloudplatform.backend.service;

import com.alibaba.fastjson.JSON;
import com.neverrar.datacloudplatform.backend.error.PermissionDeniedError;
import com.neverrar.datacloudplatform.backend.error.TesterNotExistedError;
import com.neverrar.datacloudplatform.backend.error.UnknownError;
import com.neverrar.datacloudplatform.backend.model.Test;
import com.neverrar.datacloudplatform.backend.model.User;
import com.neverrar.datacloudplatform.backend.repository.InteractionBehaviourDataRepository;
import com.neverrar.datacloudplatform.backend.repository.MainDataRepository;
import com.neverrar.datacloudplatform.backend.repository.TestRepository;
import com.neverrar.datacloudplatform.backend.util.Result;
import com.neverrar.datacloudplatform.backend.view.AllInteractionBehaviourDataByTest;
import com.neverrar.datacloudplatform.backend.view.AllMainDataByTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.util.Optional;

@Service
public class InteractionBehaviourDataService {
    @Autowired
    private InteractionBehaviourDataRepository interactionBehaviourDataRepository;

    @Autowired
    private TestRepository testRepository;

    public Result<AllInteractionBehaviourDataByTest> getAllInteractionBehaviourDataByTest(Integer testId, User user){
        try {
            Optional<Test> optionalTest = testRepository.findById(testId);
            if (!optionalTest.isPresent()) {
                return Result.wrapErrorResult(new TesterNotExistedError());
            }
            if (!optionalTest.get().getOwner().getId().equals(user.getId())) {
                return Result.wrapErrorResult(new PermissionDeniedError());
            }
            AllInteractionBehaviourDataByTest allInteractionBehaviourDataByTest =
                    new AllInteractionBehaviourDataByTest(optionalTest.get().getInteractionBehaviourDataSet());
            allInteractionBehaviourDataByTest.setTestId(optionalTest.get().getId());
            return Result.wrapSuccessfulResult(allInteractionBehaviourDataByTest);
        } catch (Exception e){
            e.printStackTrace();
        }
        return Result.wrapErrorResult(new UnknownError());
    }
}
