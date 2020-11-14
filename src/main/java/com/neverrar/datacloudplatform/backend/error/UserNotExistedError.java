package com.neverrar.datacloudplatform.backend.error;

public class UserNotExistedError implements ServiceError{

    @Override
    public String getMessage() {
        return "User isn't existed!";
    }
}
