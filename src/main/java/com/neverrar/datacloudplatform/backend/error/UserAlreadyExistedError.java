package com.neverrar.datacloudplatform.backend.error;

public class UserAlreadyExistedError implements ServiceError{

    @Override
    public String getMessage() {
        return "User already existed!";
    }

    @Override
    public Integer getCode() {return 9;}
}
