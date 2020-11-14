package com.neverrar.datacloudplatform.backend.model;


import javax.persistence.*;
import java.util.Set;

@Entity // This tells Hibernate to make a table out of this class
public class User {
    @Id
    private String id;

    private String username;

    private String password;

    private Integer role;

    @OneToMany(mappedBy = "owner",cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private Set<Project> projectSet;

    @OneToMany(mappedBy = "owner",cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private Set<Task> taskSet;

    @OneToMany(mappedBy = "owner",cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private Set<Test> testSet;

    @OneToMany(mappedBy = "owner",cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private Set<Tester> testerSet;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public Set<Project> getProjectSet() {
        return projectSet;
    }

    public void setProjectSet(Set<Project> projectSet) {
        this.projectSet = projectSet;
    }

    public Set<Task> getTaskSet() {
        return taskSet;
    }

    public void setTaskSet(Set<Task> taskSet) {
        this.taskSet = taskSet;
    }

    public Set<Test> getTestSet() {
        return testSet;
    }

    public void setTestSet(Set<Test> testSet) {
        this.testSet = testSet;
    }

    public Set<Tester> getTesterSet() {
        return testerSet;
    }

    public void setTesterSet(Set<Tester> testerSet) {
        this.testerSet = testerSet;
    }
}