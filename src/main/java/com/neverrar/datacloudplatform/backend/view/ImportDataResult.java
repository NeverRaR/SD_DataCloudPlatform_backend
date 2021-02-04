package com.neverrar.datacloudplatform.backend.view;

import lombok.Data;

import java.util.Date;

@Data
public class ImportDataResult {

    private Date importTime;

    private Integer dataCount;

    private Integer testId;
}
