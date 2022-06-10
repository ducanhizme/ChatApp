package com.example.chatapp.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.chatapp.fragment.ChatFragment;
import com.example.chatapp.fragment.FriendsFragment;
import com.example.chatapp.model.UserModel;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentStateAdapter {
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new FriendsFragment();
            case 1:
                return new ChatFragment();
        }
        return new FriendsFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }


}
