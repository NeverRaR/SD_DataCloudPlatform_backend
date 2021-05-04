package com.neverrar.datacloudplatform.backend.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "ID_GENERATOR")
public class IDGenerator {
    @Id
    @Column(name = "PK_NAME")
    private String  name;

    @Column(name = "PK_VALUE")
    private Integer value;
}
