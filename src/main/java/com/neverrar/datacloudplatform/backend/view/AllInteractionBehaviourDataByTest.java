package com.neverrar.datacloudplatform.backend.view;

import com.neverrar.datacloudplatform.backend.model.InteractionBehaviourData;
import com.neverrar.datacloudplatform.backend.model.MainData;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Data
public class AllInteractionBehaviourDataByTest {
    private Integer testId;
    private List<InteractionBehaviourDataInformation> list;

    public AllInteractionBehaviourDataByTest(Set<InteractionBehaviourData> dataSet){
        list=new LinkedList<>();
        for(InteractionBehaviourData data:dataSet){
            list.add(new InteractionBehaviourDataInformation(data));
        }
    }
}
