package com.neverrar.datacloudplatform.backend.request;

import lombok.Data;

import java.util.List;

@Data
public class ImportMainDataRequest {
    private Integer testId;
    private List<MainDataRequest> dataList;
}
