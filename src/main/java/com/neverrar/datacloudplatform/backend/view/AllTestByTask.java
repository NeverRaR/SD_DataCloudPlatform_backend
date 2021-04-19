package com.neverrar.datacloudplatform.backend.view;

import com.neverrar.datacloudplatform.backend.model.Project;
import com.neverrar.datacloudplatform.backend.model.Test;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Data
public class AllTestByTask {

    private Integer taskId;
    private List<TestInformation> ownedTest;

    public AllTestByTask(Set<Test> testSet){
        ownedTest = new LinkedList<>();
        for(Test test : testSet){
            ownedTest.add(new TestInformation(test));
        }
    }
}
