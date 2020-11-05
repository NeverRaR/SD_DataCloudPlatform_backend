package com.neverrar.datacloudplatform.backend.model;


import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity // This tells Hibernate to make a table out of this class
public class Project {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    private String name;

    private String description;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="create_time")
    private Date createTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="last_modified")
    private Date lastModified; //时间

    @ManyToOne
    @JoinColumn(name="owner_id")
    private User owner;

    @OneToMany(mappedBy = "project",cascade = {CascadeType.REMOVE}, fetch = FetchType.EAGER)
    private Set<Task> taskSet;

    @OneToMany(mappedBy = "project",cascade = {CascadeType.REMOVE}, fetch = FetchType.EAGER)
    private Set<Tester> testerSet;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }
}