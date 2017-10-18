package com.senzec.alfa.socket_chat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.senzec.alfa.R;

public class SocketChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        overridePendingTransition(0, android.R.anim.slide_in_left);
        setContentView(R.layout.activity_socket_chat);
    }
}
