package com.neverrar.datacloudplatform.backend.request;

import com.neverrar.datacloudplatform.backend.model.Project;
import lombok.Data;

import javax.persistence.*;

@Data
public class CreateTesterRequest {

    private String name;

    private String gender;

    private String education;

    private Double drivingYears;

    private Integer age;

    private Integer projectId;
}
