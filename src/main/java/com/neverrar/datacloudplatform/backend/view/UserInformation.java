package com.neverrar.datacloudplatform.backend.view;

import com.neverrar.datacloudplatform.backend.model.User;
import lombok.Data;

@Data
public class UserInformation {
    private Integer role;
    private String nickName;
    private String id;
    public UserInformation(User user){

        this.role=user.getRole();
        this.nickName=user.getNickname();
        this.id=user.getId();
    }

}
