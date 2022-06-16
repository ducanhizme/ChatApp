package com.example.chatapp.fragment;

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

import com.example.chatapp.adapter.UserAdapter;

import com.example.chatapp.databinding.FragmentUserBinding;
import com.example.chatapp.model.UserModel;
import com.example.chatapp.service.FireBaseHelper;

import java.util.ArrayList;
import java.util.List;

public class UserFragment extends Fragment {
    private FragmentUserBinding binding_;
    private UserAdapter ua;
    private List<UserModel> mlist = new ArrayList<>();
    private Dialog dialog;

    public UserFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mlist = new ArrayList<>();
        mlist = FireBaseHelper.getListUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding_ = FragmentUserBinding.inflate(inflater,container,false);
        LinearLayoutManager ln = new LinearLayoutManager(getContext());
        binding_.recyclerview.setLayoutManager(ln);
        binding_.recyclerview.setHasFixedSize(true);
        ua = new UserAdapter(getContext());
        ua.updateUserList(mlist);
        binding_.recyclerview.setAdapter(ua);
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
        return binding_.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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