package com.neverrar.datacloudplatform.backend.view;

import com.neverrar.datacloudplatform.backend.model.Video;
import lombok.Data;

@Data
public class VideoTag {
    private Integer id;

    private String name;

    private String url;

    public VideoTag(Video video){
        this.id=video.getId();
        this.name=video.getName();
        this.url=video.getUrl();
    }
}
