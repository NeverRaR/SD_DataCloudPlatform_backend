package com.neverrar.datacloudplatform.backend.view;

import com.neverrar.datacloudplatform.backend.model.LogEventData;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class LogEventDataInformation {

    private String type;

    private String from;

    private String to;

    private String time;

    private Double duration;

    private Double distanceStartingTime;

    public LogEventDataInformation(LogEventData data){
        this.type=data.getType();
        this.distanceStartingTime=data.getDistanceStartingTime();
        DateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        this.time=dateFormat.format(data.getDataTime());
        this.from=data.getFrom();
        this.to=data.getTo();
        this.duration=data.getDuration();
    }
}
