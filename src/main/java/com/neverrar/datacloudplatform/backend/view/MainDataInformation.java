package com.neverrar.datacloudplatform.backend.view;

import com.neverrar.datacloudplatform.backend.model.MainData;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * "yyyy-MM-dd"
 * "HH:mm:ss.SSS"
 * "yyyy-MM-dd HH:mm:ss.SSS"
 */

@Data
public class MainDataInformation implements Serializable {

    private Date date;

    private String time;

    private Double speed;

    private Double accelerate;

    private Double turnAround;

    private Double leftLineDistance;

    private Double rightLineDistance;

    private Double distanceStartingTime;


    public MainDataInformation(MainData mainData){

        DateFormat dateFormat= new SimpleDateFormat("HH:mm:ss.SSS");
        this.time=dateFormat.format(mainData.getTime());
        this.date=mainData.getDate();
        this.speed=mainData.getSpeed();
        this.accelerate=mainData.getAccelerate();
        this.turnAround=mainData.getTurnAround();
        this.leftLineDistance=mainData.getLeftLineDistance();
        this.rightLineDistance=mainData.getRightLineDistance();
        this.distanceStartingTime=mainData.getDistanceStartingTime();
    }
}
