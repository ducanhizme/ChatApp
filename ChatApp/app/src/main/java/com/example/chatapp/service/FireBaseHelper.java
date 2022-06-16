package com.example.chatapp.service;

import android.graphics.ColorSpace;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.chatapp.model.RequestFriend;
import com.example.chatapp.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class FireBaseHelper {

    public static void sendDataUser(String userId,String name, String email, String image){
        FirebaseDatabase fbdb = FirebaseDatabase.getInstance();
        DatabaseReference dbr = fbdb.getReference(Constant.USER_REFERENCE).child(Objects.requireNonNull(userId));
        UserModel user = new UserModel(userId,name,email,image);
        dbr.setValue(user.toHashMap());
    }

    public static ArrayList<UserModel> getListUser(){
        ArrayList<UserModel> userList = new ArrayList<>();
        FirebaseDatabase fbdb = FirebaseDatabase.getInstance();
        DatabaseReference dbr = fbdb.getReference(Constant.USER_REFERENCE);
        dbr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data : snapshot.getChildren()){
                    UserModel user = data.getValue(UserModel.class);
                    userList.add(user);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        return userList;
    }
    public static void sendRequest(String idCurrentUser,String id , String type , UserModel user){
        FirebaseDatabase fdb = FirebaseDatabase.getInstance();
        DatabaseReference dbr = fdb.getReference(Constant.REQUEST_REFERENCE).child(idCurrentUser).child(id);
        RequestFriend rf = new RequestFriend(type,user);
        dbr.setValue(rf.toHashMap());
    }

    public static void removeRequest(String id){
        FirebaseDatabase fdb = FirebaseDatabase.getInstance();
        DatabaseReference dbr = fdb.getReference(Constant.REQUEST_REFERENCE).child(id).child("list").child(id);
        dbr.removeValue();
    }

    public static List<RequestFriend> getRequestFriends(String idUser){
        List<RequestFriend> listRequest = new ArrayList<>();
        FirebaseDatabase fdb = FirebaseDatabase.getInstance();
        DatabaseReference dbr = fdb.getReference(Constant.REQUEST_REFERENCE).child(idUser);
        dbr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot e : snapshot.getChildren()){
                    RequestFriend rf = e.getValue(RequestFriend.class);
                    listRequest.add(rf);
                    Log.d("userFirebase",rf.toString());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        return listRequest;
    }
}
