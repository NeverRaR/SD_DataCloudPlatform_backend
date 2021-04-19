package com.neverrar.datacloudplatform.backend.view;

import com.neverrar.datacloudplatform.backend.model.Task;
import com.neverrar.datacloudplatform.backend.model.Tester;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Data
public class AllTesterByProject {

    private Integer projectId;
    private List<TesterTag> ownedTester;

    public AllTesterByProject(Set<Tester> testerSet){
        ownedTester=new LinkedList<>();
        for(Tester tester : testerSet){
            TesterTag tag=new TesterTag();
            tag.setName(tester.getName());
            tag.setId(tester.getId());
            ownedTester.add(tag);
        }
    }
}
