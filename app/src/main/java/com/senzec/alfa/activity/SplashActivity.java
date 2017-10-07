package com.senzec.alfa.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.senzec.alfa.R;
import com.senzec.alfa.utils.SharedPrefClass;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if(new SharedPrefClass(SplashActivity.this).IS_LOGIN() == true){

            startActivity(new Intent(SplashActivity.this, GroupFeedActivity.class));
        }else if(new SharedPrefClass(SplashActivity.this).IS_LOGIN() == false){
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
        }
     //   startActivity(new Intent(SplashActivity.this, EditProfileActivity.class));

    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
