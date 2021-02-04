package com.neverrar.datacloudplatform.backend.error;

public class PermissionDeniedError implements ServiceError{

    @Override
    public String getMessage() {
        return "Permission is denied!";
    }

    @Override
    public Integer getCode() {return 3;}
}