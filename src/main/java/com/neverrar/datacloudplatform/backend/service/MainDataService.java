package com.neverrar.datacloudplatform.backend.service;

import com.alibaba.fastjson.JSON;
import com.neverrar.datacloudplatform.backend.error.PermissionDeniedError;
import com.neverrar.datacloudplatform.backend.error.TestNotExistedError;
import com.neverrar.datacloudplatform.backend.error.TesterNotExistedError;
import com.neverrar.datacloudplatform.backend.error.UnknownError;
import com.neverrar.datacloudplatform.backend.model.MainData;
import com.neverrar.datacloudplatform.backend.model.Project;
import com.neverrar.datacloudplatform.backend.model.Test;
import com.neverrar.datacloudplatform.backend.model.User;
import com.neverrar.datacloudplatform.backend.repository.MainDataRepository;
import com.neverrar.datacloudplatform.backend.repository.TestRepository;
import com.neverrar.datacloudplatform.backend.request.CreateProjectRequest;
import com.neverrar.datacloudplatform.backend.request.ImportMainDataRequest;
import com.neverrar.datacloudplatform.backend.request.MainDataRequest;
import com.neverrar.datacloudplatform.backend.util.Result;
import com.neverrar.datacloudplatform.backend.view.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class MainDataService {

    @Autowired
    private MainDataRepository mainDataRepository;

    @Autowired
    private TestRepository testRepository;

    public Result<AllMainDataByTest> getAllMainDataByTest(Integer testId,User user){
        try {
            Optional<Test> optionalTest = testRepository.findById(testId);
            if (!optionalTest.isPresent()) {
                return Result.wrapErrorResult(new TesterNotExistedError());
            }
            if (user.getRole().equals(0) && !optionalTest.get().getOwner().getId().equals(user.getId())) {
                return Result.wrapErrorResult(new PermissionDeniedError());
            }
            AllMainDataByTest allMainDataByTest = new AllMainDataByTest(optionalTest.get().getMainDataSet());
            allMainDataByTest.setTestId(optionalTest.get().getId());
            return Result.wrapSuccessfulResult(allMainDataByTest);
        } catch (Exception e){
            e.printStackTrace();
        }
        return Result.wrapErrorResult(new UnknownError());
    }

}
