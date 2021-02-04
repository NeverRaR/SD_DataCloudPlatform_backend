package com.neverrar.datacloudplatform.backend.model;

import org.hibernate.procedure.spi.ParameterRegistrationImplementor;

import javax.persistence.*;
import java.util.Date;

@Entity
public class LogEventData {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String type;

    @Column(name = "drive_from")
    private String from;

    @Column(name = "drive_to")
    private String to;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="data_time")
    private Date dataTime;

    private Double duration;

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



    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Date getDataTime() {
        return dataTime;
    }

    public void setDataTime(Date dataTime) {
        this.dataTime = dataTime;
    }

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
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
