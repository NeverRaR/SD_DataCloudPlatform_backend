package com.neverrar.datacloudplatform.backend.view;

import com.neverrar.datacloudplatform.backend.model.Task;
import com.neverrar.datacloudplatform.backend.model.Test;
import lombok.Data;

import java.util.Date;


@Data
public class TestInformation {

    private Integer id;
    private Integer testerId;
    private Integer taskId;
    private Date testTime;

    public TestInformation(Test test){
        this.taskId=test.getTask().getId();
        this.testerId=test.getTester().getId();
        this.id=test.getId();
        this.testTime=test.getTestTime();
    }
}
