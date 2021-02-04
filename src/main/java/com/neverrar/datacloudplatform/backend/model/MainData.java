package com.neverrar.datacloudplatform.backend.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class MainData {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="data_time")
    private Date dataTime;

    private Double speed;

    private Double accelerate;

    @Column(name="turn_around")
    private Double turnAround;

    @Column(name="steer_turn")
    private Double steerTurn;

    @Column(name="angle_speed")
    private Double angleSpeed;

    @Column(name= "foot_weight")
    private Double footWeight;

    @Column(name = "road_departures")
    private Double roadDepartures;

    @Column(name = "road_curvature")
    private Double roadCurvature;

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

    public Date getDataTime() {
        return dataTime;
    }

    public void setDataTime(Date dataTime) {
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

    public Double getSteerTurn() {
        return steerTurn;
    }

    public void setSteerTurn(Double steerTurn) {
        this.steerTurn = steerTurn;
    }

    public Double getAngleSpeed() {
        return angleSpeed;
    }

    public void setAngleSpeed(Double angleSpeed) {
        this.angleSpeed = angleSpeed;
    }

    public Double getFootWeight() {
        return footWeight;
    }

    public void setFootWeight(Double footWeight) {
        this.footWeight = footWeight;
    }

    public Double getRoadDepartures() {
        return roadDepartures;
    }

    public void setRoadDepartures(Double roadDepartures) {
        this.roadDepartures = roadDepartures;
    }

    public Double getRoadCurvature() {
        return roadCurvature;
    }

    public void setRoadCurvature(Double roadCurvature) {
        this.roadCurvature = roadCurvature;
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

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }
}
