package com.example.chatapp.model;
import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class UserModel implements Serializable {
    private String id;
    private String name;
    private String email;
    private String image;

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

  /*  public List<UserModel> getListFriends() {
        return listFriends;
    }

    public void setListFriends(List<UserModel> listFriends) {
        this.listFriends = listFriends;
    }*/

    @NonNull
    @Override
    public String toString() {
        return "UserModel{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", image='" + image + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserModel userModel = (UserModel) o;
        return Objects.equals(id, userModel.id) && Objects.equals(name, userModel.name) && Objects.equals(email, userModel.email) && Objects.equals(image, userModel.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, image);
    }
}
