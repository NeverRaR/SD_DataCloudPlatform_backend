package com.neverrar.datacloudplatform.backend.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class InteractionBehaviourData {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String type;

    private String location;

    private String element;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="start_time")
    private Date startTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="end_time")
    private Date endTime;

    @Column(name="start_status")
    private String startStatus;

    @Column(name="end_status")
    private String endStatus;

    @Column(name = "distance_starting_time")
    private Double distanceStartingTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="test_id")
    private Test test;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getElement() {
        return element;
    }

    public void setElement(String element) {
        this.element = element;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getStartStatus() {
        return startStatus;
    }

    public void setStartStatus(String startStatus) {
        this.startStatus = startStatus;
    }

    public String getEndStatus() {
        return endStatus;
    }

    public void setEndStatus(String endStatus) {
        this.endStatus = endStatus;
    }

    public Double getDistanceStartingTime() {
        return distanceStartingTime;
    }

    public void setDistanceStartingTime(Double distanceStartingTime) {
        this.distanceStartingTime = distanceStartingTime;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }
}
