package com.neverrar.datacloudplatform.backend.view;

import com.neverrar.datacloudplatform.backend.model.Task;
import com.neverrar.datacloudplatform.backend.model.Test;
import lombok.Data;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


@Data
public class TestInformation {

    private Integer id;
    private Integer testerId;
    private Integer taskId;
    private String testerName;
    private String taskName;
    private String successful;
    private String startTime;
    private String endTime;
    private String testName;
    private Double errorCount;

    public TestInformation(Test test){
        this.taskId=test.getTask().getId();
        this.testerId=test.getTester().getId();
        this.id=test.getId();
        DateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        if(test.getStartTime()!= null)
            this.startTime=dateFormat.format(test.getStartTime());
        if(test.getEndTime()!= null)
            this.endTime=dateFormat.format(test.getEndTime());
        this.testName=test.getName();
        this.testerName=test.getTester().getName();
        this.taskName=test.getTask().getName();
        this.errorCount=test.getErrorCount();
        this.successful=test.getSuccessful();

    }
}
