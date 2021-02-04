package com.neverrar.datacloudplatform.backend.error;

public class ProjectNotExistedError implements ServiceError{

    @Override
    public String getMessage() {
        return "Project is not existed!";
    }

    @Override
    public Integer getCode() {return 4;}
}
