package com.neverrar.datacloudplatform.backend.model;

import sun.applet.Main;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity // This tells Hibernate to make a table out of this class
public class Test {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="test_time")
    private Date testTime;

    @Column(name="test_name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="tester_id")
    private Tester tester;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="task_id")
    private Task task;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="owner_id")
    private User owner;

    @OneToMany(mappedBy = "test",fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    private Set<MainData> mainDataSet;

    @OneToMany(mappedBy = "test",fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    private Set<Video> videoSet;

    @OneToMany(mappedBy = "test",fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    private Set<LogEventData> logEventDataSet;

    @OneToMany(mappedBy = "test",fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    private Set<InteractionBehaviourData> interactionBehaviourDataSet;

    @OneToMany(mappedBy = "test",fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    private Set<MarkData> markDataSet;

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

    public User getOwner() {
        return owner;
    }

    public User OwnerInstance() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public  Tester getTester() {
        return tester;
    }

    public void setTester(Tester tester) {
        this.tester = tester;
    }

    public Tester TesterInstance() {
        return tester;
    }

    public Task getTask() {
        return task;
    }

    public Task TaskInstance() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Set<LogEventData> getLogEventDataSet() {
        return logEventDataSet;
    }

    public void setLogEventDataSet(Set<LogEventData> logEventDataSet) {
        this.logEventDataSet = logEventDataSet;
    }

    public Set<MarkData> getMarkDataSet() {
        return markDataSet;
    }

    public void setMarkDataSet(Set<MarkData> markDataSet) {
        this.markDataSet = markDataSet;
    }

    public Set<MainData> getMainDataSet() {
        return mainDataSet;
    }

    public void setMainDataSet(Set<MainData> mainDataSet) {
        this.mainDataSet = mainDataSet;
    }

    public Set<InteractionBehaviourData> getInteractionBehaviourDataSet() {
        return interactionBehaviourDataSet;
    }

    public void setInteractionBehaviourDataSet(Set<InteractionBehaviourData> interactionBehaviourDataSet) {
        this.interactionBehaviourDataSet = interactionBehaviourDataSet;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Video> getVideoSet() {
        return videoSet;
    }

    public void setVideoSet(Set<Video> videoSet) {
        this.videoSet = videoSet;
    }
}
