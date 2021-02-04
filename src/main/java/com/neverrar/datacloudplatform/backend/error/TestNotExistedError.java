package com.neverrar.datacloudplatform.backend.error;

public class TestNotExistedError implements ServiceError{

    @Override
    public String getMessage() {
        return "Test is not existed!";
    }

    @Override
    public Integer getCode() {return 7;}
}
