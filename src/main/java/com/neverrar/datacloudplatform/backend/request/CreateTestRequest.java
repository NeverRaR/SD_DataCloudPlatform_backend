package com.neverrar.datacloudplatform.backend.request;

import com.neverrar.datacloudplatform.backend.model.Task;
import com.neverrar.datacloudplatform.backend.model.Tester;
import lombok.Data;


@Data
public class CreateTestRequest {

    private Integer testerId;

    private Integer taskId;
}
