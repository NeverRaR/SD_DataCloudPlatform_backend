package com.neverrar.datacloudplatform.backend.view;

import com.neverrar.datacloudplatform.backend.model.Task;
import lombok.Data;
import org.springframework.util.StopWatch;

@Data
public class TaskInformation {

    private Integer id;

    private String name;

    private String description;

    private String scene;

    public TaskInformation(Task task){
        this.id=task.getId();
        this.name=task.getName();
        this.description=task.getDescription();
        this.scene=task.getScene();
    }
}
