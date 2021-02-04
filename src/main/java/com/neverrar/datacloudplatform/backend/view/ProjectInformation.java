package com.neverrar.datacloudplatform.backend.view;

import com.neverrar.datacloudplatform.backend.model.Project;
import com.neverrar.datacloudplatform.backend.model.User;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Data
public class ProjectInformation {

    private Integer id;

    private String name;

    private String description;

    private Date createTime;

    private Date lastModified;

    public ProjectInformation(Project project){
        this.id=project.getId();
        this.name=project.getName();
        this.description=project.getDescription();
        this.createTime=project.getCreateTime();
        this.lastModified=project.getLastModified();
    }


}
