package com.example.chatapp.model;
import java.util.ArrayList;
import java.util.HashMap;

public class UserModel {
    private String id;
    private String name;
    private String email;
    private String image;
    private ArrayList<UserModel> listFriends;

    public UserModel() {}

    public UserModel(String id, String name, String email, String image) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public HashMap<String,String> toHashMap() {
        HashMap<String,String> hm = new HashMap<>();
        hm.put("id",id);
        hm.put("name",name);
        hm.put("email",email);
        hm.put("image",image);
        return hm;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", image='" + image + '\'' +
                ", listFriends=" + listFriends +
                '}';
    }
}
