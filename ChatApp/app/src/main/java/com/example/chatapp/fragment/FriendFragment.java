package com.example.chatapp.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.chatapp.databinding.FragmentFriendBinding;


public class FriendFragment extends Fragment {
    public FragmentFriendBinding binding_;

    public FriendFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding_=FragmentFriendBinding.inflate(inflater,container,false);
        return binding_.getRoot();
    }
}