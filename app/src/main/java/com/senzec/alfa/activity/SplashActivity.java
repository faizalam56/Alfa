package com.senzec.alfa.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.senzec.alfa.R;
import com.senzec.alfa.socket_chat.SocketChatActivity;
import com.senzec.alfa.utils.SharedPrefClass;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(0, android.R.anim.slide_in_left);
        setContentView(R.layout.activity_splash);

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

       /* if(new SharedPrefClass(SplashActivity.this).getLoginInfo().length()>0){
            startActivity(new Intent(SplashActivity.this, GroupFeedActivity.class));
        }else {
            startActivity(new Intent(SplashActivity.this, MainActivity.class));
        }*/

        startActivity(new Intent(SplashActivity.this, SocketChatActivity.class));
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, android.R.anim.slide_out_right);
    }
}
