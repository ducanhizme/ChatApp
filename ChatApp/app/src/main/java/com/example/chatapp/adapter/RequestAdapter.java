package com.example.chatapp.adapter;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.databinding.ItemRequestBinding;
import com.example.chatapp.model.RequestFriend;
import com.example.chatapp.service.Constant;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.RequestViewHolder>{
    public ArrayList<RequestFriend> listRequest = new ArrayList<>();
    public Context mContext;

    public RequestAdapter(Context mContext/*, List<RequestFriend> mListRequest*/){
        this.mContext = mContext;
      //  this.listRequest = (ArrayList<RequestFriend>) mListRequest;
    }


    @SuppressLint("NotifyDataSetChanged")
    public void updateRequestList(List<RequestFriend> mListRequest){
        listRequest.clear();
        notifyDataSetChanged();
        this.listRequest.addAll(mListRequest);
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
        holder.bindView(listRequest.get(position));
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

        public void bindView(RequestFriend rf){
            binding_.username.setText(rf.getUser().getName());
            binding_.emailUser.setText(rf.getUser().getEmail());
            Picasso.get().load(rf.getUser().getImage()).into(binding_.imageUser);
            if (rf.getType().equalsIgnoreCase(Constant.RECEIVER)) {
            }else{
                binding_.accept.setText("Pending");
            }

        }
    }
}
