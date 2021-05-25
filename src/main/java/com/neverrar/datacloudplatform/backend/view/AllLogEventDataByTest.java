package com.neverrar.datacloudplatform.backend.view;

import com.neverrar.datacloudplatform.backend.model.InteractionBehaviourData;
import com.neverrar.datacloudplatform.backend.model.LogEventData;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Data
public class AllLogEventDataByTest {

    private Integer testId;
    private List<LogEventDataInformation> list;

    public AllLogEventDataByTest(Set<LogEventData> dataSet){
        list=new LinkedList<>();
        for(LogEventData data:dataSet){
            list.add(new LogEventDataInformation(data));
        }
    }
}
