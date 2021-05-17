package com.neverrar.datacloudplatform.backend.view;

import com.neverrar.datacloudplatform.backend.model.MainData;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
public class MainDataInformation implements Serializable {

    private Date date;

    private Date time;

    private Double speed;

    private Double accelerate;

    private Double turnAround;

    private Double leftLineDistance;

    private Double rightLineDistance;

    private Double distanceStartingTime;


    public MainDataInformation(MainData mainData){
        this.date=mainData.getDate();
        this.time=mainData.getTime();
        this.speed=mainData.getSpeed();
        this.accelerate=mainData.getAccelerate();
        this.turnAround=mainData.getTurnAround();
        this.leftLineDistance=mainData.getLeftLineDistance();
        this.rightLineDistance=mainData.getRightLineDistance();
        this.distanceStartingTime=mainData.getDistanceStartingTime();
    }
}
