package com.neverrar.datacloudplatform.backend.error;

public class TaskNotExistedError implements ServiceError{

    @Override
    public String getMessage() {
        return "Task is not existed!";
    }
}