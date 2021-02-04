package com.neverrar.datacloudplatform.backend.request;

import lombok.Data;

@Data
public class UpdateTesterRequest {
    private String name;

    private String gender;

    private String education;

    private Double drivingYears;

    private Integer age;
}
