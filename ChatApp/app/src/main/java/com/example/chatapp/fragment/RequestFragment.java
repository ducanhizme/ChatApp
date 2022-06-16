package com.example.chatapp.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chatapp.R;
import com.example.chatapp.adapter.RequestAdapter;
import com.example.chatapp.databinding.FragmentRequestBinding;
import com.example.chatapp.model.RequestFriend;
import com.example.chatapp.service.FireBaseHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class RequestFragment extends Fragment {
    public FragmentRequestBinding binding_;
    public List<RequestFriend> listRequest = new ArrayList<>();


    public RequestFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listRequest = new ArrayList<>();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        listRequest = FireBaseHelper.getRequestFriends(user.getUid());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding_ = FragmentRequestBinding.inflate(inflater,container,false);
        LinearLayoutManager ln = new LinearLayoutManager(getContext());
        binding_.recyclerview.setLayoutManager(ln);
        RequestAdapter ra= new RequestAdapter(getContext());
        ra.updateRequestList(listRequest);
        binding_.recyclerview.setAdapter(ra);
        return binding_.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}