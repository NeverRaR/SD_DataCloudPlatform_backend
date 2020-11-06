package com.neverrar.datacloudplatform.backend.request;

import com.neverrar.datacloudplatform.backend.model.Project;

public class ProjectRequest {
    private String sessionId;
    private Project project;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
