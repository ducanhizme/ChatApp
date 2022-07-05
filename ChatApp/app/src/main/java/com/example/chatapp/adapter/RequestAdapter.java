package com.example.chatapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.chatapp.R;
import com.example.chatapp.databinding.ItemRequestBinding;
import com.example.chatapp.mInterface.IRecycleViewClick;
import com.example.chatapp.model.RequestFriend;
import com.example.chatapp.model.UserModel;
import com.example.chatapp.service.Constant;
import com.example.chatapp.service.FireBaseHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.RequestViewHolder>{
    public ArrayList<RequestFriend> listRequest = new ArrayList<>();
    public Context mContext;
    IRecycleViewClick iRecycleViewClick;

    public RequestAdapter(Context mContext,IRecycleViewClick iRecycleViewClick){
        this.mContext = mContext;
        this.iRecycleViewClick = iRecycleViewClick;
    }

    public RequestAdapter(Context mContext,IRecycleViewClick iRecycleViewClick,ArrayList<RequestFriend> data){
        this.mContext = mContext;
        this.iRecycleViewClick = iRecycleViewClick;
        this.listRequest = data;
    }

    public ArrayList<RequestFriend> getListRequest() {
        return listRequest;
    }

    @NonNull
    @Override
    public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ItemRequestBinding binding_ = ItemRequestBinding.inflate(inflater,parent,false);
        return new RequestViewHolder(binding_);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestViewHolder holder, int position) {
        RequestFriend rf = listRequest.get(position);
        if(rf == null) return;
        initRowItems(holder,rf);
        onAddOnClick(holder,rf);
        onCancelClick(holder,rf);

    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateRequestList(ArrayList<RequestFriend> mListRequest){
        this.listRequest.clear();
        notifyDataSetChanged();
        this.listRequest.addAll(mListRequest);
    }

    public void onCancelClick(RequestViewHolder holder,RequestFriend rf){
        holder.binding_.cancel.setOnClickListener( v->{
            handleRemoveRequest(rf);
            iRecycleViewClick.removeItem(rf);
        });
    }

    public void initRowItems(RequestViewHolder holder,RequestFriend rf){
        holder.binding_.userNameRequest.setText(rf.getUser().getName());
        Picasso.get().load(rf.getUser().getImage()).into(holder.binding_.imageUser);
            if (rf.getType().equalsIgnoreCase(Constant.SENDER))
               holder.binding_.accept.setText(R.string.acceptRequest);
            else
               holder.binding_.accept.setText(R.string.pendingRequest);
    }

    public void onAddOnClick(RequestViewHolder holder,RequestFriend rf){
        if(holder.binding_.accept.getText().equals(mContext.getString(R.string.acceptRequest))){
            holder.binding_.accept.setOnClickListener(v->{
                handleAddFriends(rf);
                handleRemoveRequest(rf);
                iRecycleViewClick.removeItem(rf);
            });
        }
    }

    private void handleRemoveRequest(RequestFriend rf) {
        FireBaseHelper.removeRequest(currentUserModel().getId());
        FireBaseHelper.removeRequest(rf.getUser().getId());
    }

    private void handleAddFriends(RequestFriend rf){
        FireBaseHelper.addFriend(rf.getUser(),rf.getUser().getId(),currentUserModel().getId());
        FireBaseHelper.addFriend(currentUserModel(),currentUserModel().getId(),rf.getUser().getId());
    }

    private UserModel currentUserModel() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currentUserId="";
        String name ="";
        String email="";
        String image ="";
        if (user != null) {
            currentUserId = user.getUid();
            name = user.getDisplayName();
            email = user.getEmail();
            image = String.valueOf(user.getPhotoUrl());
        }
        return new UserModel(currentUserId,name,email,image);

    }

    @Override
    public int getItemCount() {
        return listRequest.size() ;
    }


    public static class RequestViewHolder extends RecyclerView.ViewHolder{
        ItemRequestBinding binding_;
        public RequestViewHolder(@NonNull ItemRequestBinding binding_) {
            super(binding_.getRoot());
            this.binding_ = binding_;
        }

//        public void bindView(RequestFriend rf){
////
////
//            initRequestCard(rf);
//        }
//
//        private void initRequestCard(RequestFriend rf) {
//            binding_.userNameRequest.setText(rf.getUser().getName());
//            Picasso.get().load(rf.getUser().getImage()).into(binding_.imageUser);
//            if (rf.getType().equalsIgnoreCase(Constant.SENDER)) {
//                binding_.accept.setText(R.string.acceptRequest);
//                FireBaseHelper.addFriend(rf.getUser(),rf.getUser().getId(),currentUserId());
////                removeItem();
//            }else{
//                binding_.accept.setText(R.string.pendingRequest);
//            }
//        }
//
//
//        public RequestViewHolder linkAdapter(RequestAdapter adapter){
//            this.adapter = adapter;
//             return this;
//        }


//        private int removeItem() {
//            int removeItemIndex = getAbsoluteAdapterPosition();
//            String userClickedId = adapter.listRequest.get(removeItemIndex).getUser().getId();
//            FireBaseHelper.removeRequest(userClickedId);
//            adapter.listRequest.remove(removeItemIndex);
//            adapter.notifyItemRemoved(removeItemIndex);
//            return removeItemIndex;
//        }

        private void removeItem() {
//            FireBaseHelper.removeRequest(currentUserId());
//            int removeItemIndex = getAbsoluteAdapterPosition();
////            ArrayList<RequestFriend> listNew = new ArrayList<>(adapter.listRequest);
////            listNew.remove(removeItemIndex);
////            updateRequestList(listNew);
//            String userClickedId = adapter.listRequest.get(removeItemIndex).getUser().getId();
//            FireBaseHelper.removeRequest(userClickedId);
//            adapter.listRequest.remove(removeItemIndex);
//            Log.d("size", "" + adapter.listRequest.size());
//            adapter.notifyItemRemoved(removeItemIndex);
//            adapter.notifyItemRangeChanged(removeItemIndex, adapter.listRequest.size());

        }



    }

}
