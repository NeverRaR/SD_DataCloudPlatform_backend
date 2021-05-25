package com.neverrar.datacloudplatform.backend.service;


import com.neverrar.datacloudplatform.backend.error.PermissionDeniedError;
import com.neverrar.datacloudplatform.backend.error.TesterNotExistedError;
import com.neverrar.datacloudplatform.backend.error.UnknownError;
import com.neverrar.datacloudplatform.backend.model.Test;
import com.neverrar.datacloudplatform.backend.model.User;
import com.neverrar.datacloudplatform.backend.repository.MainDataRepository;
import com.neverrar.datacloudplatform.backend.repository.MarkDataRepository;
import com.neverrar.datacloudplatform.backend.repository.TestRepository;
import com.neverrar.datacloudplatform.backend.util.Result;
import com.neverrar.datacloudplatform.backend.view.AllMainDataByTest;
import com.neverrar.datacloudplatform.backend.view.AllMarkDataByTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MarkDataService {

    @Autowired
    private MarkDataRepository markDataRepository;

    @Autowired
    private TestRepository testRepository;

    public Result<AllMarkDataByTest> getAllMarkDataByTest(Integer testId, User user){
        try {
            Optional<Test> optionalTest = testRepository.findById(testId);
            if (!optionalTest.isPresent()) {
                return Result.wrapErrorResult(new TesterNotExistedError());
            }
            if (user.getRole().equals(0) && !optionalTest.get().getOwner().getId().equals(user.getId())) {
                return Result.wrapErrorResult(new PermissionDeniedError());
            }
            AllMarkDataByTest allMarkDataByTest = new AllMarkDataByTest(optionalTest.get().getMarkDataSet());
            allMarkDataByTest.setTestId(optionalTest.get().getId());
            return Result.wrapSuccessfulResult(allMarkDataByTest);
        } catch (Exception e){
            e.printStackTrace();
        }
        return Result.wrapErrorResult(new UnknownError());
    }
}
