package com.neverrar.datacloudplatform.backend.view;

import com.neverrar.datacloudplatform.backend.model.MainData;
import com.neverrar.datacloudplatform.backend.request.MainDataRequest;
import lombok.Data;
import sun.applet.Main;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Data
public class AllMainDataByTest implements Serializable {
    private Integer projectId;
    private Integer testId;
    private List<MainDataInformation> list;

    public AllMainDataByTest(Set<MainData> mainDataSet){
        list=new LinkedList<>();
        for(MainData mainData:mainDataSet){
            list.add(new MainDataInformation(mainData));
        }
    }
}
