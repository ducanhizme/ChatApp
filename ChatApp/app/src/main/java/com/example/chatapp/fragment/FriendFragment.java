package com.example.chatapp.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.chatapp.activity.ChatActivity;
import com.example.chatapp.adapter.FriendsAdapter;
import com.example.chatapp.databinding.FragmentFriendBinding;
import com.example.chatapp.mInterface.IRecycleViewClick;
import com.example.chatapp.model.RequestFriend;
import com.example.chatapp.model.UserModel;
import com.example.chatapp.service.Constant;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;


public class FriendFragment extends Fragment implements IRecycleViewClick {
    public FragmentFriendBinding binding_;
    public ArrayList<UserModel> listFriends = new ArrayList<>();
    public FriendsAdapter adapter;

    public FriendFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding_=FragmentFriendBinding.inflate(inflater,container,false);
        getListFriendDb();
        initView();
        return binding_.getRoot();
    }

    private void getListFriendDb() {
        FirebaseDatabase fbd = FirebaseDatabase.getInstance();
        DatabaseReference dr = fbd.getReference(Constant.LIST_FRIEND).child(getIdCurrentUser());
        dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot e : snapshot.getChildren()){
                    UserModel user = e.getValue(UserModel.class);
                    listFriends.add(user);
                    adapter.updateFriendsList(listFriends);
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Some thing went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView(){
        binding_.recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        binding_.recyclerview.hasFixedSize();
        adapter = new FriendsAdapter(getContext(),this);
        binding_.recyclerview.setAdapter(adapter);
    }

    @NonNull
    private String getIdCurrentUser() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String id ="";
        if(user != null)
            id = user.getUid();
        else return "" ;
        return id;
    }

    @Override
    public void removeItem(RequestFriend rf) {
    }

    @Override
    public void transIntent(UserModel user) {
        Intent intent = new Intent(getContext(), ChatActivity.class);
        intent.putExtra(Constant.KEY_USER,user);
        startActivity(intent);
    }

}