package com.neverrar.datacloudplatform.backend.view;

import com.neverrar.datacloudplatform.backend.model.InteractionBehaviourData;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class InteractionBehaviourDataInformation {
    private String type;

    private String location;

    private String element;

    private String startTime;

    private String endTime;

    private String startStatus;

    private String endStatus;

    private Double distanceStartingTime;

    public InteractionBehaviourDataInformation(InteractionBehaviourData data){
        this.type=data.getType();
        this.location=data.getLocation();
        this.element=data.getElement();
        DateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        this.startTime=dateFormat.format(data.getStartTime());
        this.endTime=dateFormat.format(data.getEndTime());
        this.startStatus=data.getStartStatus();
        this.endStatus=data.getEndStatus();
        this.distanceStartingTime=data.getDistanceStartingTime();

    }


}
