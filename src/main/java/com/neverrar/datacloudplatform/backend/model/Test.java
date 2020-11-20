package com.neverrar.datacloudplatform.backend.model;

import javax.persistence.*;
import java.util.Date;

@Entity // This tells Hibernate to make a table out of this class
public class Test {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @Temporal(TemporalType.DATE)
    @Column(name="test_time")
    private Date testTime;

    @ManyToOne
    @JoinColumn(name="tester_id")
    private Tester tester;

    @ManyToOne
    @JoinColumn(name="task_id")
    private Task task;

    @ManyToOne
    @JoinColumn(name="owner_id")
    private User owner;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Date getTestTime() {
        return testTime;
    }

    public void setTestTime(Date testTime) {
        this.testTime = testTime;
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

    public  Integer getTester() {
        return tester.getId();
    }

    public void setTester(Tester tester) {
        this.tester = tester;
    }

    public Tester TesterInstance() {
        return tester;
    }

    public Integer getTask() {
        return task.getId();
    }

    public Task TaskInstance() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }
}
