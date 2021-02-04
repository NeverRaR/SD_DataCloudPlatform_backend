package com.neverrar.datacloudplatform.backend.request;

import lombok.Data;

@Data
public class CreateProjectRequest {
    private String name;

    private String description;
}
