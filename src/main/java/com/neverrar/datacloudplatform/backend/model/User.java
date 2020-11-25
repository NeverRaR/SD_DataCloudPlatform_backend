package com.neverrar.datacloudplatform.backend.model;


import javax.persistence.*;
import java.util.Set;

@Entity // This tells Hibernate to make a table out of this class
public class User {
    @Id
    private String id;

    private String salt;

    private String nickname;

    private String password;

    private Integer role;

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    private Set<Project> projectSet;

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    private Set<Task> taskSet;

    @OneToMany(mappedBy = "owner",fetch = FetchType.LAZY)
    private Set<Test> testSet;

    @OneToMany(mappedBy = "owner",fetch = FetchType.LAZY)
    private Set<Tester> testerSet;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String username) {
        this.nickname = username;
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

    public Set<Project> projectSetInstance() {
        return projectSet;
    }

    public Set<Task> taskSetInstance() {
        return taskSet;
    }

    public Set<Test> testSetInstance() {
        return testSet;
    }

    public Set<Tester> testerSetInstance() {
        return testerSet;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}