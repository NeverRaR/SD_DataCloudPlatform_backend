package com.neverrar.datacloudplatform.backend.request;

import lombok.Data;

@Data
public class UpdateTaskRequest {
    private String name;

    private String description;

    private String scene;
}
