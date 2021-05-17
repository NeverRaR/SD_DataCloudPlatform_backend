package com.neverrar.datacloudplatform.backend.service;

import com.neverrar.datacloudplatform.backend.error.PermissionDeniedError;
import com.neverrar.datacloudplatform.backend.error.TesterNotExistedError;
import com.neverrar.datacloudplatform.backend.error.UnknownError;
import com.neverrar.datacloudplatform.backend.model.Test;
import com.neverrar.datacloudplatform.backend.model.User;
import com.neverrar.datacloudplatform.backend.model.Video;
import com.neverrar.datacloudplatform.backend.repository.TestRepository;
import com.neverrar.datacloudplatform.backend.repository.VideoRepository;
import com.neverrar.datacloudplatform.backend.util.Result;
import com.neverrar.datacloudplatform.backend.view.AllMainDataByTest;
import com.neverrar.datacloudplatform.backend.view.VideoByTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VideoService {

    @Autowired
    VideoRepository videoRepository;

    @Autowired
    private TestRepository testRepository;

    public Result<VideoByTest> getVideoByTest(Integer testId, User user,String videoName){
        try {
            Optional<Test> optionalTest = testRepository.findById(testId);
            if (!optionalTest.isPresent()) {
                return Result.wrapErrorResult(new TesterNotExistedError());
            }
            if (!optionalTest.get().getOwner().getId().equals(user.getId())) {
                return Result.wrapErrorResult(new PermissionDeniedError());
            }
            VideoByTest videoByTest=new VideoByTest();
            videoByTest.setTestId(testId);
            videoByTest.setVideoName(videoName);
            Test test=optionalTest.get();
            for(Video video:test.getVideoSet()){
                if(video.getName().equals(videoName)) {
                    videoByTest.setVideoUrl(video.getUrl());
                    break;
                }
            }
            return Result.wrapSuccessfulResult(videoByTest);
        } catch (Exception e){
            e.printStackTrace();
        }
        return Result.wrapErrorResult(new UnknownError());
    }
}
