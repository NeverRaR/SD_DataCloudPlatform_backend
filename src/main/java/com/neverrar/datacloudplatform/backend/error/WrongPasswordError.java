package com.neverrar.datacloudplatform.backend.error;

public class WrongPasswordError implements ServiceError{

    @Override
    public String getMessage() {
        return "Wrong password!";
    }

    @Override
    public Integer getCode() {return 11;}
}
