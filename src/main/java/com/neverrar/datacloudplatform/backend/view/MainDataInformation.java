package com.neverrar.datacloudplatform.backend.view;

import com.neverrar.datacloudplatform.backend.model.MainData;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
public class MainDataInformation {

    private Integer id;

    private Date dataTime;

    private Double speed;

    private Double accelerate;

    private Double turnAround;

    private Double steerTurn;

    private Double angleSpeed;

    private Double footWeight;

    private Double roadDepartures;

    private Double roadCurvature;

    private Double leftLineDistance;

    private Double rightLineDistance;

    private Double distanceStartingTime;

    public MainDataInformation(MainData mainData){
        this.id=mainData.getId();
        this.dataTime=mainData.getDataTime();
        this.speed=mainData.getSpeed();
        this.accelerate=mainData.getAccelerate();
        this.turnAround=mainData.getTurnAround();
        this.steerTurn=mainData.getSteerTurn();
        this.angleSpeed=mainData.getAngleSpeed();
        this.footWeight=mainData.getFootWeight();
        this.roadCurvature=mainData.getRoadCurvature();
        this.leftLineDistance=mainData.getLeftLineDistance();
        this.rightLineDistance=mainData.getRightLineDistance();
        this.distanceStartingTime=mainData.getDistanceStartingTime();
    }
}
