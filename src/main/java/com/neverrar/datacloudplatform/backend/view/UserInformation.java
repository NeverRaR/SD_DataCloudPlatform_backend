package com.neverrar.datacloudplatform.backend.view;

import com.neverrar.datacloudplatform.backend.model.User;

public class UserInformation {
    private User user;
    public UserInformation(User user){
        this.user=user;
    }
    public int getRole(){
        return user.getRole();
    }
    public String getNickname(){
        return user.getNickname();
    }
    public String getId(){
        return user.getId();
    }
}
