package com.example.chatapp.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.chatapp.fragment.FriendFragment;
import com.example.chatapp.fragment.RequestFragment;
import com.example.chatapp.fragment.UserFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new UserFragment();
            case 1:
                return new FriendFragment();
            case 2:
                return new RequestFragment();
        }
        return new UserFragment();
    }

    @Override
    public int getItemCount() {
        return 3;
    }


}
