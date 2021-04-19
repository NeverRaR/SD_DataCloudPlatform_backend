package com.neverrar.datacloudplatform.backend.service;

import com.neverrar.datacloudplatform.backend.error.PermissionDeniedError;
import com.neverrar.datacloudplatform.backend.error.TestNotExistedError;
import com.neverrar.datacloudplatform.backend.error.TesterNotExistedError;
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

    public @Transactional
    Result<ImportDataResult> importMainData (ImportMainDataRequest body, User user) {
        Optional<Test> optionalTest=testRepository.findById(body.getTestId());
        if(!optionalTest.isPresent()) {
            return Result.wrapErrorResult(new TesterNotExistedError());
        }
        if(!optionalTest.get().getOwner().getId().equals(user.getId())){
            return Result.wrapErrorResult(new PermissionDeniedError());
        }

        for(MainDataRequest dataRequest : body.getDataList()){
            MainData mainData=wrapMainData(dataRequest);
            mainData.setTest(optionalTest.get());
            mainDataRepository.save(mainData);
        }
        ImportDataResult result=new ImportDataResult();
        result.setDataCount(body.getDataList().size());
        result.setTestId(body.getTestId());
        result.setImportTime(new Date());
        return Result.wrapSuccessfulResult(result);
    }

    public Result<AllMainDataByTest> getAllMainDataByTest(Integer testId,User user){
        Optional<Test> optionalTest=testRepository.findById(testId);
        if(!optionalTest.isPresent()) {
            return Result.wrapErrorResult(new TesterNotExistedError());
        }
        if(!optionalTest.get().getOwner().getId().equals(user.getId())){
            return Result.wrapErrorResult(new PermissionDeniedError());
        }
        AllMainDataByTest allMainDataByTest=new AllMainDataByTest();
        List<MainDataInformation> list=new LinkedList<>();
        for(MainData mainData: optionalTest.get().getMainDataSet()){
            MainDataInformation mainDataInformation=new MainDataInformation(mainData);
            list.add(mainDataInformation);
        }
        allMainDataByTest.setTestId(testId);
        allMainDataByTest.setList(list);
        return Result.wrapSuccessfulResult(allMainDataByTest);
    }


    private MainData wrapMainData(MainDataRequest dataRequest){
        MainData mainData=new MainData();
        mainData.setAccelerate(dataRequest.getAccelerate());
        mainData.setAngleSpeed(dataRequest.getAngleSpeed());
        mainData.setDataTime(dataRequest.getDataTime());
        mainData.setTurnAround(dataRequest.getTurnAround());
        mainData.setRoadDepartures(dataRequest.getRoadDepartures());
        mainData.setDistanceStartingTime(dataRequest.getDistanceStartingTime());
        mainData.setFootWeight(dataRequest.getFootWeight());
        mainData.setLeftLineDistance(dataRequest.getLeftLineDistance());
        mainData.setRightLineDistance(dataRequest.getRightLineDistance());
        mainData.setRoadCurvature(dataRequest.getRoadCurvature());
        mainData.setSpeed(dataRequest.getSpeed());
        mainData.setSteerTurn(dataRequest.getSteerTurn());
        return mainData;
    }
}
