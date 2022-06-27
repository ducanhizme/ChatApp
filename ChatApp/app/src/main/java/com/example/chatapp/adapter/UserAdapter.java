package com.example.chatapp.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.R;
import com.example.chatapp.databinding.ItemCardviewBinding;
import com.example.chatapp.model.UserModel;
import com.example.chatapp.service.Constant;
import com.example.chatapp.service.FireBaseHelper;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserAdapter extends  RecyclerView.Adapter<UserAdapter.UserViewHolder>{

    public ArrayList<UserModel> userList = new ArrayList<>();
    public ArrayList<UserModel> friends = new ArrayList<>();
    public Dialog dialog;
    public Context mContext;


    public UserAdapter(Context mContext){
        this.mContext = mContext;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateUserList(ArrayList<UserModel> users){
        userList.clear();
        notifyDataSetChanged();
        userList.addAll(users);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateFilterList(ArrayList<UserModel> users){
        this.userList =  users;
        notifyDataSetChanged();
    }

    public void updateFriendsList(ArrayList<UserModel> friends){
        this.friends.clear();
        this.friends.addAll(friends);
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
        holder.isFriend(userList.get(position));
        holder.isYou(user.getId());
        holder.canItemClick(position);



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

        @SuppressLint("SetTextI18n")
        private void isFriend(UserModel user){
            for(UserModel e : friends){
                if(e.getId().equalsIgnoreCase(user.getId())){
                    binding_.friend.setText("Friend");
                }
            }
        }

        private void canItemClick(int position){
             String str = (String) binding_.friend.getText();
             if(str.equals("")){
                 binding_.userCard.setOnClickListener(view ->{
                     addFriendDialog(userList,position);
                 });
             }
        }

        @SuppressLint("SetTextI18n")
        private void isYou(String id) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                Log.d("id", user.getUid());
                if (user.getUid().equalsIgnoreCase(id)) {
                    binding_.friend.setText("You");
                }
            }
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
                    addBtn.setEnabled(false);
                });
            }
        }
    }
}
