package com.neverrar.datacloudplatform.backend.view;

import com.neverrar.datacloudplatform.backend.model.Task;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Data
public class AllTaskByProject {
    private Integer projectId;
    private List<TaskTag> ownedTask;

    public AllTaskByProject(Set<Task> taskSet){
        ownedTask=new LinkedList<>();
        for(Task task : taskSet){
            TaskTag tag=new TaskTag();
            tag.setName(task.getName());
            tag.setId(task.getId());
            ownedTask.add(tag);
        }
    }
}
