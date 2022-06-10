package com.example.chatapp.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.databinding.ItemCardviewBinding;
import com.example.chatapp.model.UserModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UserAdapter extends  RecyclerView.Adapter<UserAdapter.UserViewHolder>{

    ArrayList<UserModel> userList = new ArrayList<>();
    Context context;

    public UserAdapter(ArrayList<UserModel> userList,Context context){
        this.userList = userList;
        this.context = context;
    }
    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ItemCardviewBinding binding_ = ItemCardviewBinding.inflate(inflater,parent,false);
        return new UserAdapter.UserViewHolder(binding_);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        UserModel user = userList.get(position);
        if(user == null){
            return ;
        }
        holder.bindViewHolder(user.getName(),user.getEmail(),user.getImage());

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
    public void filterList(ArrayList<UserModel> list){
        this.userList = list;
        notifyDataSetChanged();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder{
        ItemCardviewBinding binding_;
        public UserViewHolder(@NonNull ItemCardviewBinding binding_) {
            super(binding_.getRoot());
            this.binding_ = binding_;
        }

        public void bindViewHolder(String name, String email, String imageUri){
            binding_.username.setText(name);
            binding_.emailUser.setText(email);
            Picasso.get().load(Uri.parse(imageUri)).into(binding_.imageUser);
        }
    }


}
