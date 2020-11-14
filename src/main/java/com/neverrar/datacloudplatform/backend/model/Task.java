package com.neverrar.datacloudplatform.backend.model;


import javax.persistence.*;
import java.util.Set;

@Entity // This tells Hibernate to make a table out of this class
public class Task {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    private String name;

    private String description;

    private String scene;

    @Column(name="test_count")
    private Integer testCount;

    @ManyToOne
    @JoinColumn(name="project_id")
    private Project project;

    @ManyToOne
    @JoinColumn(name="owner_id")
    private User owner;

    @OneToMany(mappedBy = "task",cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private Set<Test> testSet;

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

    public String getScene(){
        return scene;
    }

    public void setScene(String scene){
        this.scene=scene;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTestCount() {
        return testCount;
    }

    public void setTestCount(Integer testCount) {
        this.testCount = testCount;
    }

    public String getOwner() {
        return owner.getId();
    }

    public User OwnerInstance() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Integer getProject() {
        return project.getId();
    }

    public Project ProjectInstance() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}