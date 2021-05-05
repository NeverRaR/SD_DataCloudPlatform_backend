package com.neverrar.datacloudplatform.backend.view;

import lombok.Data;

import java.util.List;

@Data
public class AllInteractionBehaviourDataByTest {
    private Integer testId;
    private List<InteractionBehaviourDataInformation> list;
}
