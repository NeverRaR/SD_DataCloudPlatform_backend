package com.neverrar.datacloudplatform.backend.view;

import com.neverrar.datacloudplatform.backend.model.Project;
import lombok.Data;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Data
public class AllProjectInfoByUser {

    private String userId;
    private List<ProjectTag> ownedProjects;
    public AllProjectInfoByUser(Iterable<Project> projectSet){
        ownedProjects = new LinkedList<>();
        for(Project project : projectSet){
            ProjectTag tag=new ProjectTag();
            tag.setId(project.getId());
            tag.setName(project.getName());
            ownedProjects.add(tag);
        }
    }
}
