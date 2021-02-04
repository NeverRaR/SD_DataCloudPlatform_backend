package com.neverrar.datacloudplatform.backend.request;

import lombok.Data;

@Data
public class UpdateProjectRequest {
    private String name;

    private String description;
}
