package com.neverrar.datacloudplatform.backend.view;

import lombok.Data;

import java.util.List;

@Data
public class ProjectWithTask {
    private Integer projectId;
    private String projectName;
    private List<TaskWithTester> ownedTask;
}
