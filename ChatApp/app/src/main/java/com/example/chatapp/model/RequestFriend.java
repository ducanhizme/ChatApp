package com.example.chatapp.model;

import java.util.HashMap;

public class RequestFriend {
    private UserModel user;
    private String type;


    public RequestFriend() {
    }

    public RequestFriend(String type, UserModel user) {
        this.user = user;
        this.type = type;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public HashMap toHashMap (){
        HashMap hm = new HashMap<>();
//        hm.put("id",this.user.getId());
//        hm.put("name",this.user.getName());
//        hm.put("email",this.user.getEmail());
//        hm.put("image",this.user.getImage());
        hm.put("user",user);
        hm.put("type",this.type);
        return hm;
    }

    @Override
    public String toString() {
        return "RequestFriend{" +
                "user=" + user.toString()+
                ", type='" + type + '\'' +
                '}';
    }
}
