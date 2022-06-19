package com.example.chatapp.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.chatapp.R;
import com.example.chatapp.adapter.RequestAdapter;
import com.example.chatapp.databinding.FragmentRequestBinding;
import com.example.chatapp.model.RequestFriend;
import com.example.chatapp.model.UserModel;
import com.example.chatapp.service.Constant;
import com.example.chatapp.service.FireBaseHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RequestFragment extends Fragment {
    private FragmentRequestBinding binding_;
    private final ArrayList<RequestFriend> listRequest = new ArrayList<>();
    RequestAdapter adapter;

    public RequestFragment() {}


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding_ = FragmentRequestBinding.inflate(inflater);
        initViews();
        getUserFromFb();
        return binding_.getRoot();
    }

    private void getUserFromFb() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            String uid = user.getUid();
            FirebaseDatabase fdb = FirebaseDatabase.getInstance();
            DatabaseReference dbr = fdb.getReference(Constant.REQUEST_REFERENCE).child(uid);
            dbr.addValueEventListener(new ValueEventListener() {
                @SuppressLint("NotifyDataSetChanged")
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot e : snapshot.getChildren()) {
                        RequestFriend rf = e.getValue(RequestFriend.class);
                         listRequest.add(rf);
                         adapter.updateRequestList(listRequest);
                       // adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void initViews() {
        listRequest.add(
                new RequestFriend( "sdsdsds", new UserModel("1212","1212","1212","https://lh3.googleusercontent.com/a/AATXAJxQdj0ADSblju8tCAhrCrbTdHTdT02PIehbENPN=s96-c"))
        );
        binding_.recyclerview.setLayoutManager( new LinearLayoutManager(getContext()));
        adapter= new RequestAdapter(getContext()/*,listRequest*/);
        binding_.recyclerview.setAdapter(adapter);
        binding_.recyclerview.setHasFixedSize(true);

    }

}