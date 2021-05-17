package com.neverrar.datacloudplatform.backend.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class MainData {
    @Id
    @TableGenerator(name="ID_GENERATOR",
            table="ID_GENERATOR",
            pkColumnName="PK_NAME",
            pkColumnValue="MAINDATA_ID",
            valueColumnName="PK_VALUE",
            allocationSize=1)
    @GeneratedValue(strategy=GenerationType.TABLE,
            generator="ID_GENERATOR")
    private Integer id;

    @Temporal(TemporalType.DATE)
    @Column(name="data_date")
    private Date dataDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="data_time")
    private Date dataTime;

    private Double speed;

    private Double accelerate;

    @Column(name="turn_around")
    private Double turnAround;

    @Column(name = "left_line_distance")
    private Double leftLineDistance;

    @Column(name = "right_line_distance")
    private Double rightLineDistance;

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


    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public Date getTime() {
        return dataTime;
    }

    public void setTime(Date dataTime) {
        this.dataTime = dataTime;
    }

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public Double getAccelerate() {
        return accelerate;
    }

    public void setAccelerate(Double accelerate) {
        this.accelerate = accelerate;
    }

    public Double getTurnAround() {
        return turnAround;
    }

    public void setTurnAround(Double turnAround) {
        this.turnAround = turnAround;
    }

    public Double getLeftLineDistance() {
        return leftLineDistance;
    }

    public void setLeftLineDistance(Double leftLineDistance) {
        this.leftLineDistance = leftLineDistance;
    }

    public Double getRightLineDistance() {
        return rightLineDistance;
    }

    public void setRightLineDistance(Double rightLineDistance) {
        this.rightLineDistance = rightLineDistance;
    }

    public Double getDistanceStartingTime() {
        return distanceStartingTime;
    }

    public void setDistanceStartingTime(Double distanceStartingTime) {
        this.distanceStartingTime = distanceStartingTime;
    }

    public Date getDate() {
        return dataDate;
    }

    public void setDate(Date dataDate) {
        this.dataDate = dataDate;
    }
}
