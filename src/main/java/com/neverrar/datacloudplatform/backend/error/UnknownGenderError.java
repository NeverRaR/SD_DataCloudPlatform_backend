package com.neverrar.datacloudplatform.backend.error;

public class UnknownGenderError implements ServiceError{

    @Override
    public String getMessage() {
        return "Unknown Gender!";
    }


    @Override
    public Integer getCode() {return 8;}
}
