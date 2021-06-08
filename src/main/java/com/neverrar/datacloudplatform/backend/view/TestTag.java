package com.neverrar.datacloudplatform.backend.view;

import com.neverrar.datacloudplatform.backend.model.Test;
import lombok.Data;

@Data
public class TestTag {
    private Integer testId;
    private String testName;
    private Double testMinute;

    public TestTag(Test test){
        this.testId=test.getId();
        this.testName=test.getName();
        this.testMinute=(test.getEndTime().getTime()-test.getStartTime().getTime())/(1000*60.0);
    }
}
