package com.example.chatapp.mInterface;

import com.example.chatapp.model.RequestFriend;
import com.example.chatapp.model.UserModel;

public interface IRecycleViewClick {
    void removeItem(RequestFriend rf);
    void transIntent(UserModel user);
}
