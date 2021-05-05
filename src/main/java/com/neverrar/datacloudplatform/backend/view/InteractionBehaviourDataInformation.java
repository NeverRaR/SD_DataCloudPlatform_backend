package com.neverrar.datacloudplatform.backend.view;

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

    private String startTime;

    private String endTime;

    private String startStatus;

    private String endStatus;

    private Double distanceStartingTime;
}
