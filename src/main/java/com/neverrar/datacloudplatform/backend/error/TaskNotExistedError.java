package com.neverrar.datacloudplatform.backend.error;

public class TaskNotExistedError implements ServiceError{

    @Override
    public String getMessage() {
        return "Task is not existed!";
    }

    @Override
    public Integer getCode() {return 5;}
}