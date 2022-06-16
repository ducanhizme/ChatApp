package com.example.chatapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.example.chatapp.R;
import com.example.chatapp.adapter.ViewPagerAdapter;
import com.example.chatapp.databinding.ActivityMainBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private GoogleSignInOptions gso;
    private GoogleSignInClient gsc;
    private ActivityMainBinding binding;
    private String userName;
    private Uri userImage;
    private ViewPagerAdapter vpa;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//        getSupportFragmentManager().beginTransaction().add(R.id.rc_layout,new FriendsFragment()).commit();
        loadFragmemtToTabLayout();
        signOut();
    }

    private void loadFragmemtToTabLayout() {
        vpa = new ViewPagerAdapter(this);
        binding.viewPager.setAdapter(vpa);
        new TabLayoutMediator(binding.tabLayout, binding.viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0:
                        tab.setText("Users");
                        break;
                    case 1:
                        tab.setText("Chat");
                        break;
                    case 2:
                        tab.setText("Friends");
                        break;
                    case 3:
                        tab.setText("Request");
                        break;
                }
            }
        }).attach();
    }

    private void updateUI(GoogleSignInAccount account) {
         userName = account.getDisplayName();
         binding.nameDisplay.setText(userName);
         userImage = account.getPhotoUrl();
         Picasso.get().load(userImage).into(binding.ImgeUser);
         Picasso.get().load(userImage).error(R.drawable.ic_baseline_person_24).into(binding.ImgeUser);
         greet();
    }

    private void greet(){
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        String greet ="Welcome";
        if(hour < 12){
            greet = "Good Morning";
        }else if(hour < 18){
            greet = "Good Afternoon";
        }else if(hour < 24){
            greet ="Good Night";
        }
        binding.greet.setText(greet);
    }

    private void signOut(){
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);

        binding.logoutBtn.setOnClickListener(view ->{
            gsc.signOut().addOnCompleteListener(task->{
                loginActivity();
            });
        });
    }

    private void loginActivity() {
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account!= null)
            updateUI(account);
    }

}