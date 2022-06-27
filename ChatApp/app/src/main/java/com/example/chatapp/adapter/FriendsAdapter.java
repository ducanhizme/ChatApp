package com.example.chatapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.databinding.ItemCardviewBinding;
import com.example.chatapp.mInterface.IRecycleViewClick;
import com.example.chatapp.model.UserModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.FriendsViewHolder> {
    private final Context mContext;
    private  ArrayList<UserModel> listFriends = new ArrayList<>();
    private FriendsViewHolder holder;
    private UserModel friend;
    private IRecycleViewClick IRecycleViewClick;

    public FriendsAdapter(Context mContext,IRecycleViewClick IRecycleViewClick) {
        this.mContext = mContext;
        this.IRecycleViewClick = IRecycleViewClick;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateFriendsList(ArrayList<UserModel> listFriends){
        this.listFriends.clear();
        notifyDataSetChanged();
        this.listFriends.addAll(listFriends);
    }

    @NonNull
    @Override
    public FriendsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ItemCardviewBinding binding_ = ItemCardviewBinding.inflate(inflater,parent,false);
        return new FriendsViewHolder(binding_);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendsViewHolder holder, int position) {
        friend = listFriends.get(position);
        if(this.friend == null) return;
        this.holder = holder;
        initCard();
        holder.binding_.userCard.setOnClickListener(v->{
            this.IRecycleViewClick.transIntent(listFriends.get(position));
        });
    }

    @SuppressLint("SetTextI18n")
    public void initCard(){
       this.holder.binding_.username.setText(friend.getName());
       this.holder.binding_.emailUser.setText(friend.getEmail());
       Picasso.get().load(Uri.parse(friend.getImage())).into(holder.binding_.imageUser);
       this.holder.binding_.friend.setText("Friend");
    }

    @Override
    public int getItemCount() {
        return listFriends.size();
    }

    public static class FriendsViewHolder extends RecyclerView.ViewHolder{
        ItemCardviewBinding binding_;

        public FriendsViewHolder(@NonNull ItemCardviewBinding binding_) {
            super(binding_.getRoot());
            this.binding_ = binding_;
        }
    }










}
