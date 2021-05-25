package com.neverrar.datacloudplatform.backend.view;

import com.neverrar.datacloudplatform.backend.model.LogEventData;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Data
public class LogEventDataInformation {

    private String type;

    private String from;

    private String to;

    private Date time;

    private Double duration;

    private Double distanceStartingTime;

    public LogEventDataInformation(LogEventData data){
        this.type=data.getType();
        this.distanceStartingTime=data.getDistanceStartingTime();
        this.from=data.getFrom();
        this.to=data.getTo();
        this.duration=data.getDuration();
    }
}
