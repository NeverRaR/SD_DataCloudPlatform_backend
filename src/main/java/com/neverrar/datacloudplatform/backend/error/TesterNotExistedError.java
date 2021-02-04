package com.neverrar.datacloudplatform.backend.error;

public class TesterNotExistedError implements ServiceError{

    @Override
    public String getMessage() {
        return "Tester is not existed!";
    }

    @Override
    public Integer getCode() {return 6;}
}
