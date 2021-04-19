package com.neverrar.datacloudplatform.backend.error;

public class PermissionDeniedError implements ServiceError{

    @Override
    public String getMessage() {
        return "Permission denied!";
    }

    @Override
    public Integer getCode() {return 3;}
}