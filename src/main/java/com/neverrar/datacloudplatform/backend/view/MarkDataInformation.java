package com.neverrar.datacloudplatform.backend.view;

import com.neverrar.datacloudplatform.backend.model.MainData;
import com.neverrar.datacloudplatform.backend.model.MarkData;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class MarkDataInformation {
    private String name;

    private String color;

    private Double length;

    private String systemTime;

    private Date systemDate;

    private String markInfo;



    public MarkDataInformation(MarkData data){
        this.name=data.getName();
        this.color=data.getColor();
        this.length=data.getLength();
        this.markInfo=data.getMarkInfo();
        DateFormat dateFormat= new SimpleDateFormat("HH:mm:ss.SSS");
        this.systemTime=dateFormat.format(data.getSystemTime());
        this.systemDate=data.getSystemDate();
    }
}
