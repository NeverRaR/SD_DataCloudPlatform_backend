package com.neverrar.datacloudplatform.backend.request;

import lombok.Data;

import java.util.Date;

@Data
public class MainDataRequest {

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
}
