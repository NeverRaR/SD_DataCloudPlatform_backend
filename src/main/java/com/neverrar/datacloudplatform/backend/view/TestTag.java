package com.neverrar.datacloudplatform.backend.view;

import com.neverrar.datacloudplatform.backend.model.Test;
import lombok.Data;

@Data
public class TestTag {
    private Integer testId;
    private String testName;

    public TestTag(Test test){
        this.testId=test.getId();
        this.testName=test.getName();
    }
}
