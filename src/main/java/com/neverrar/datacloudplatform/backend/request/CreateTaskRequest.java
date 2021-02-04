package com.neverrar.datacloudplatform.backend.request;

import lombok.Data;


@Data
public class CreateTaskRequest {
    private String name;

    private String description;

    private String scene;

    private Integer projectId;
}
