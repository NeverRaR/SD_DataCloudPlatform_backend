package com.neverrar.datacloudplatform.backend.view;

import lombok.Data;

import java.util.List;

@Data
public class TesterWithTest {
    private Integer testerId;
    private String testerName;
    private List<TestTag>  ownedTest;
}
