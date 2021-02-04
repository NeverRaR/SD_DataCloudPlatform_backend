package com.neverrar.datacloudplatform.backend.request;

import lombok.Data;

@Data
public class RegisterRequest {

    private String id;

    private String nickname;

    private String password;
}
