package com.neverrar.datacloudplatform.backend.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class CreateProjectRequest {
    private String name;

    private String description;

    private MultipartFile data;
}
