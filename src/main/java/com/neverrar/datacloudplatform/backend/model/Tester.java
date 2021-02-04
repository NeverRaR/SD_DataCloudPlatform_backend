package com.neverrar.datacloudplatform.backend.model;

import javax.persistence.*;
import java.util.Set;

@Entity // This tells Hibernate to make a table out of this class
public class Tester {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    private String name;

    private String gender;

    private String education;

    @Column(name="driving_years")
    private Double drivingYears;

    private Integer age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="project_id")
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="owner_id")
    private User owner;

    @OneToMany(mappedBy = "tester", fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    private Set<Test> testSet;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEducation(){
        return education;
    }

    public void setEducation(String education){
        this.education = education;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getDrivingYears() {
        return drivingYears;
    }

    public void setDrivingYears(Double drivingYears) {
        this.drivingYears = drivingYears;
    }

    public User getOwner() {
        return owner;
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

    public Set<Test> testSetInstance() {
        return testSet;
    }

    public void setTestSet(Set<Test> testSet) {
        this.testSet = testSet;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}