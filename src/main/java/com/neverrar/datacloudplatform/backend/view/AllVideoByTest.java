package com.neverrar.datacloudplatform.backend.view;

import com.neverrar.datacloudplatform.backend.model.MainData;
import com.neverrar.datacloudplatform.backend.model.Video;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Data
public class AllVideoByTest {
    private Integer testId;
    private List<VideoTag> list;

    public AllVideoByTest(Set<Video> videoSet){
        list=new LinkedList<>();
        for(Video video: videoSet){
            list.add(new VideoTag(video));
        }
    }
}
