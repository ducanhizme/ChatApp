package com.example.chatapp.service;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.chatapp.model.UserModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
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
                    Log.d("userFirebase",user.toString());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        return userList;
    }
}
