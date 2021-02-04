package com.neverrar.datacloudplatform.backend.view;

import com.neverrar.datacloudplatform.backend.model.Tester;
import lombok.Data;



@Data
public class TesterInformation {
    private Integer id;

    private String name;

    private String gender;

    private String education;

    private Double drivingYears;

    private Integer age;

    public TesterInformation(Tester tester){
        this.id=tester.getId();
        this.name=tester.getName();
        this.gender=tester.getGender();
        this.education=tester.getEducation();
        this.drivingYears=tester.getDrivingYears();
        this.age=tester.getAge();
    }
}
