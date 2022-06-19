package com.example.chatapp.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.R;
import com.example.chatapp.databinding.ItemCardviewBinding;
import com.example.chatapp.databinding.PopupDialogBinding;
import com.example.chatapp.model.UserModel;
import com.example.chatapp.service.Constant;
import com.example.chatapp.service.FireBaseHelper;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.net.URI;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class UserAdapter extends  RecyclerView.Adapter<UserAdapter.UserViewHolder>{

    public ArrayList<UserModel> userList = new ArrayList<UserModel>();
    public Dialog dialog;
    public Context mContext;


    public UserAdapter(Context mContext, ArrayList<UserModel> userList){
        this.mContext = mContext;
        this.userList = userList;
    }


    @SuppressLint("NotifyDataSetChanged")
    public void updateUserList(List<UserModel> users){
        userList.clear();
        notifyDataSetChanged();
        userList.addAll(users);
    }
    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemCardviewBinding binding_ = ItemCardviewBinding.inflate(inflater,parent,false);
        return new UserViewHolder(binding_);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        UserModel user = userList.get(position);
        if(user == null){
            return;
        }
        holder.bindViewHolder(user.getId(),user.getName(),user.getEmail(),user.getImage());
        holder.binding_.userCard.setOnClickListener(view ->{
            if(holder.isYou(user.getId())){
                holder.addFriendDialog(userList,position);
            }
        });
//        holder.setOnClickListener(this.listener);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        ItemCardviewBinding binding_;
        String state = "nothing";


        public UserViewHolder(@NonNull ItemCardviewBinding binding_) {
            super(binding_.getRoot());
            this.binding_ = binding_;
        }

        public void bindViewHolder(String id, String name, String email, String imageUri) {
            binding_.username.setText(name);
            binding_.emailUser.setText(email);
            Picasso.get().load(Uri.parse(imageUri)).into(binding_.imageUser);
        }

        private boolean isYou(String id) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                Log.d("id", user.getUid());
                if (user.getUid().equalsIgnoreCase(id)) {
                    binding_.friend.setText("You");
                    return false;
                }
            }
            return true;
        }


        private void addFriendDialog(List<UserModel> list, int position) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                UserModel userCurrentModel = new UserModel(user.getUid(), user.getDisplayName(), user.getEmail(), String.valueOf(user.getPhotoUrl()));
                dialog = new Dialog(mContext);
                dialog.setContentView(R.layout.popup_dialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                ShapeableImageView imge = dialog.findViewById(R.id.avatarDialog);
                TextView nameDialog = dialog.findViewById(R.id.user_name_dialog);
                AppCompatButton cancelBtn = dialog.findViewById(R.id.cancel_btn);
                AppCompatButton addBtn = dialog.findViewById(R.id.add_btn);
                String uriImage = list.get(position).getImage();
                String name = list.get(position).getName();
                Picasso.get().load(Uri.parse(uriImage)).into(imge);
                nameDialog.setText(name);
                dialog.show();
                cancelBtn.setOnClickListener(view_ -> {
                    dialog.dismiss();
                });
                if (state.equals("pending")) {
                    addBtn.setText("Pending");
                }
                addBtn.setOnClickListener(view_ -> {
                    addBtn.setText("Pending");
                    FireBaseHelper.sendRequest(user.getUid(), list.get(position).getId(), Constant.RECEIVER, list.get(position));
                    FireBaseHelper.sendRequest(list.get(position).getId(), user.getUid(), Constant.SENDER, userCurrentModel);
                    state = "pending";
                    Log.e("state", state);
                });
            }
        }
    }
}
