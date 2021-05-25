package com.neverrar.datacloudplatform.backend.view;

import com.neverrar.datacloudplatform.backend.model.MainData;
import com.neverrar.datacloudplatform.backend.model.MarkData;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Data
public class AllMarkDataByTest {

    private Integer testId;
    private List<MarkDataInformation> list;

    public AllMarkDataByTest(Set<MarkData> markDataSet){
        list=new LinkedList<>();
        for(MarkData markData:markDataSet){
            list.add(new MarkDataInformation(markData));
        }
    }
}
