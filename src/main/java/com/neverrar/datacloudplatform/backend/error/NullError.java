package com.neverrar.datacloudplatform.backend.error;

public class NullError implements ServiceError{

    @Override
    public String getMessage() {
        return "Something is null!";
    }
}
