package com.neverrar.datacloudplatform.backend.view;

import com.neverrar.datacloudplatform.backend.model.MainData;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
public class MainDataInformation implements Serializable {

    private String date;

    private String time;

    private Double speed;

    private Double accelerate;

    private Double turnAround;

    private Double leftLineDistance;

    private Double rightLineDistance;

    private Double distanceStartingTime;

}
