package com.neverrar.datacloudplatform.backend.view;

import com.neverrar.datacloudplatform.backend.model.InteractionBehaviourData;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Data
public class InteractionBehaviourDataInformation {
    private String type;

    private String location;

    private String element;

    private Date startTime;

    private Date endTime;

    private String startStatus;

    private String endStatus;

    private Double distanceStartingTime;

    public InteractionBehaviourDataInformation(InteractionBehaviourData data){
        this.type=data.getType();
        this.location=data.getLocation();
        this.element=data.getElement();
        this.startTime=data.getStartTime();
        this.endTime=data.getEndTime();
        this.startStatus=data.getStartStatus();
        this.endStatus=data.getEndStatus();
        this.distanceStartingTime=data.getDistanceStartingTime();

    }


}
