package com.example.chatapp.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.chatapp.adapter.UserAdapter;

import com.example.chatapp.databinding.FragmentUserBinding;
import com.example.chatapp.model.UserModel;
import com.example.chatapp.service.Constant;
import com.example.chatapp.service.FireBaseHelper;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserFragment extends Fragment {
    private FragmentUserBinding binding_;
    private UserAdapter ua ;
    private ArrayList<UserModel> mlist=  new ArrayList<>();
    private Dialog dialog;

    public UserFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding_ = FragmentUserBinding.inflate(inflater);
        initViews();
        getDataFromFb();
        return binding_.getRoot();
    }

    private void getDataFromFb() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference(Constant.USER_REFERENCE);
        ref.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mlist.clear();
                for(DataSnapshot data : snapshot.getChildren()){
                    UserModel user = data.getValue(UserModel.class);
                    mlist.add(user);
                    ua.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(),error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void initViews() {
        setUpRcv();
        setUpSearchView();
    }

    private void setUpSearchView() {
        binding_.sv.clearFocus();
        binding_.sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filter(s);
                return true;
            }
        });
    }

    private void setUpRcv() {
        ua = new UserAdapter(getContext(), mlist);
        binding_.recyclerview.setAdapter(ua);
        LinearLayoutManager ln = new LinearLayoutManager(getContext());
        binding_.recyclerview.setLayoutManager(ln);
        binding_.recyclerview.setHasFixedSize(true);

    }


    private void filter(String s) {
        List<UserModel> userFilter = new ArrayList<>();
        for(UserModel user :mlist){
            if(user.getName().toLowerCase().contains(s.toLowerCase())){
                userFilter.add(user);
            }
        }
        ua.updateUserList(userFilter);
    }

}