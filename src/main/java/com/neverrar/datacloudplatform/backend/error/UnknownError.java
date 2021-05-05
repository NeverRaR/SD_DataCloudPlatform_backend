package com.neverrar.datacloudplatform.backend.error;

public class UnknownError implements ServiceError{

    @Override
    public String getMessage() {
        return "Unknown Error!";
    }

    @Override
    public Integer getCode() {return -1;}
}
