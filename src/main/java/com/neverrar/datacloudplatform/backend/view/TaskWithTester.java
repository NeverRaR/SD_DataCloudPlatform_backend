package com.neverrar.datacloudplatform.backend.view;

import com.neverrar.datacloudplatform.backend.model.Tester;
import lombok.Data;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Data
public class TaskWithTester {
    private Integer taskId;
    private String taskName;
    private List<TesterWithTest> ownerTester;
}
