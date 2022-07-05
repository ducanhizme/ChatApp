package com.example.chatapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.chatapp.R;
import com.example.chatapp.adapter.ChatAdapter;
import com.example.chatapp.databinding.ActivityChatBinding;
import com.example.chatapp.model.ChatModel;
import com.example.chatapp.model.UserModel;
import com.example.chatapp.service.Constant;
import com.example.chatapp.service.FireBaseHelper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class ChatActivity extends AppCompatActivity {
    private ActivityChatBinding binding ;
    private UserModel userChat;
    private ChatAdapter adapter;
    private ArrayList<ChatModel> list = new ArrayList<>();
    private String receiverRoom;
    private String senderRoom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.background));
        setContentView(binding.getRoot());
        getUserIntent();
        initView();
        getDataFromDb();

    }

    private void getDataFromDb() {
        FirebaseDatabase fd = FirebaseDatabase.getInstance();
        DatabaseReference ref = fd.getReference().child(Constant.CHAT_REFERENCE).child(senderRoom).child("message");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot e : snapshot.getChildren()){
                    ChatModel chatmodel = e.getValue(ChatModel.class);
                    if (chatmodel != null) {
                        list.add(chatmodel);
                    }
                }
                adapter.updateChatList(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getUserIntent() {
        Intent intent = getIntent();
        userChat= (UserModel) intent.getSerializableExtra(Constant.KEY_USER);
        senderRoom = userChat.getId()+ getCurrentId();
        receiverRoom = getCurrentId()+ userChat.getId();
        Log.e("user", userChat.toString());
        intent.removeExtra(Constant.KEY_USER);
    }

    private void initView(){
        initRc();
        showInformationUser();
        sendButton();
        backButton();
    }

    private void initRc() {
        binding.recycleViewChat.setHasFixedSize(true);
        LinearLayoutManager ln = new LinearLayoutManager(this);
        ln.setStackFromEnd(true);
        binding.recycleViewChat.setLayoutManager(ln);
        adapter = new ChatAdapter(this,getCurrentId());
        binding.recycleViewChat.setAdapter(adapter);
    }

    private void sendButton() {
        binding.sendBtn.setOnClickListener( v->{
            String msg = binding.chatMsg.getText().toString();
            String sender = getCurrentId();
            String receiver = userChat.getId();
            ChatModel chat = new ChatModel(sender,receiver,msg);
            FirebaseDatabase fd = FirebaseDatabase.getInstance();
            DatabaseReference dr = fd.getReference(Constant.CHAT_REFERENCE);
            dr.child(senderRoom).child("message").push().setValue(chat).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    dr.child(receiverRoom).child("message").push().setValue(chat);
                }
            });
            binding.chatMsg.setText("");
        });
    }

    private void backButton() {
        binding.backBtn.setOnClickListener(v->{
            finish();
        });
    }

    private void showInformationUser() {
        Picasso.get().load(Uri.parse(userChat.getImage())).into(binding.imageChat);
        binding.nameChat.setText(userChat.getName());
    }

    @NonNull
    private String getCurrentId(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            return user.getUid();
        }
        return "";
    }
}