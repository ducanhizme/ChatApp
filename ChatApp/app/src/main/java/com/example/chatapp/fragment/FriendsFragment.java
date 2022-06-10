package com.example.chatapp.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SearchView;

import com.example.chatapp.adapter.UserAdapter;
import com.example.chatapp.databinding.FragmentFriendsBinding;
import com.example.chatapp.model.UserModel;
import com.example.chatapp.service.FireBaseHelper;

import java.util.ArrayList;

public class FriendsFragment extends Fragment {
    private FragmentFriendsBinding binding_;
    private UserAdapter ua;
    private ArrayList<UserModel> mlist;

    public FriendsFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding_ = FragmentFriendsBinding.inflate(inflater,container,false);
        LinearLayoutManager ln = new LinearLayoutManager(getContext());
        binding_.recyclerview.setLayoutManager(ln);
        mlist = FireBaseHelper.getListUser();
        ua = new UserAdapter(mlist,getContext());
        binding_.recyclerview.setAdapter(ua);
        return binding_.getRoot();



    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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

    private void filter(String s) {
        ArrayList<UserModel> userFilter = new ArrayList<>();
        for(UserModel user :mlist){
            if(user.getName().toLowerCase().contains(s.toLowerCase())){
                userFilter.add(user);
            }
        }
        ua.filterList(userFilter);
    }

}