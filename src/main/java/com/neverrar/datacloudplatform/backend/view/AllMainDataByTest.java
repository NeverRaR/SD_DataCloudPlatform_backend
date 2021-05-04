package com.neverrar.datacloudplatform.backend.view;

import com.neverrar.datacloudplatform.backend.request.MainDataRequest;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AllMainDataByTest implements Serializable {
    private Integer testId;
    private List<MainDataInformation> list;
}
