package com.example.chatapp.service;

import android.graphics.ColorSpace;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.chatapp.model.ChatModel;
import com.example.chatapp.model.RequestFriend;
import com.example.chatapp.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference(Constant.USER_REFERENCE);
        ref.addValueEventListener(new ValueEventListener() {
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
        DatabaseReference dbr = fdb.getReference(Constant.REQUEST_REFERENCE).child(id);
        dbr.removeValue();
    }

    public static void addFriend(UserModel user, String id, String idCurrent){
        FirebaseDatabase fd = FirebaseDatabase.getInstance();
        DatabaseReference dr = fd.getReference(Constant.LIST_FRIEND).child(idCurrent).child(id);
        dr.setValue(user.toHashMap());

    }

    public static ArrayList<RequestFriend> getRequestFriends(String idUser){
        ArrayList<RequestFriend> listRequest = new ArrayList<>();
        FirebaseDatabase fdb = FirebaseDatabase.getInstance();
        DatabaseReference dbr = fdb.getReference(Constant.REQUEST_REFERENCE).child(idUser);
        dbr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot e : snapshot.getChildren()){
                    RequestFriend rf = e.getValue(RequestFriend.class);
                    listRequest.add(rf);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        return listRequest;
    }

//    @NonNull
//    public static ArrayList<UserModel> getListFriends(String idCurrentUser){
//        ArrayList<UserModel> list = new ArrayList<>();
//
//        return list;
//    }

    public static void sendMessage(ChatModel chatM){
        FirebaseDatabase fd = FirebaseDatabase.getInstance();
        DatabaseReference dr = fd.getReference(Constant.CHAT_REFERENCE);
        dr.push().setValue(chatM.toHashMap());
    }

}
