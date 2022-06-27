package com.example.chatapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatapp.databinding.MsgLeftBinding;
import com.example.chatapp.databinding.MsgRightBinding;
import com.example.chatapp.model.ChatModel;

import java.util.ArrayList;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ArrayList<ChatModel> chatList = new ArrayList<>();
    private final int MSG_RIGHT =0;
    private final int MSG_LEFT =1;
    private String senderId;


    public ChatAdapter(Context mContext,String senderId){
        this.context = mContext;
        this.senderId = senderId;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateChatList(ArrayList<ChatModel> list){
        this.chatList.clear();
        notifyDataSetChanged();
        this.chatList.addAll(list);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == MSG_RIGHT)
            return new MsgRight(MsgRightBinding.inflate(LayoutInflater.from(context),parent,false));
        else
            return new MsgLeft(MsgLeftBinding.inflate(LayoutInflater.from(context),parent,false));

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position)== MSG_RIGHT){
            ((MsgRight)holder).showMsgRight(chatList.get(position));
        }else{
            ((MsgLeft)holder).showMsgLeft(chatList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (chatList.get(position).getSender().equals(senderId))
            return MSG_RIGHT;
        else
            return MSG_LEFT;
    }

    public class MsgRight extends RecyclerView.ViewHolder{
        MsgRightBinding bindingRight;

        public MsgRight(MsgRightBinding bindingRight) {
            super(bindingRight.getRoot());
            this.bindingRight = bindingRight;
        }

        public void showMsgRight(ChatModel chat){
            bindingRight.rightMsg.setText(chat.getMessage());
        }
    }

    public class MsgLeft extends RecyclerView.ViewHolder{
        MsgLeftBinding bindingLeft;
        public MsgLeft(@NonNull MsgLeftBinding bindingLeft) {
            super(bindingLeft.getRoot());
            this.bindingLeft = bindingLeft;
        }

        public void showMsgLeft(ChatModel chat){
            bindingLeft.leftMsg.setText(chat.getMessage());
        }
    }

}
