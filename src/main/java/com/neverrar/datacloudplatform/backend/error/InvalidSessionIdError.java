package com.neverrar.datacloudplatform.backend.error;

public class InvalidSessionIdError implements ServiceError{

    @Override
    public String getMessage() {
        return "SessionId is Invalid!";
    }
}
