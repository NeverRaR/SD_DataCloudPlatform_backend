package com.neverrar.datacloudplatform.backend.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class InteractionBehaviourData {

    @Id
    @TableGenerator(name="ID_GENERATOR",
            table="ID_GENERATOR",
            pkColumnName="PK_NAME",
            pkColumnValue="IBDATA_ID",
            valueColumnName="PK_VALUE",
            allocationSize=1)
    @GeneratedValue(strategy=GenerationType.TABLE,
            generator="ID_GENERATOR")
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="test_id")
    private Test test;

    private String path;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
