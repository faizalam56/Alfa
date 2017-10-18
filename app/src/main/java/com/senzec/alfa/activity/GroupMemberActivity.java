package com.senzec.alfa.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.senzec.alfa.R;
import com.senzec.alfa.adapter.GroupMemberAdapter;
import com.senzec.alfa.font.FontChangeCrawler;
import com.senzec.alfa.utils.CircleImageView;

public class GroupMemberActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView mGroupMemberListRC;
    ImageView mBackIV, mProfileIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        overridePendingTransition(0, android.R.anim.slide_in_left);
        setContentView(R.layout.activity_group_member);

        mBackIV = (ImageView) findViewById(R.id.iv_back);
        mProfileIV = (ImageView)findViewById(R.id.idProfileIV);
        mGroupMemberListRC = (RecyclerView) findViewById(R.id.rv_groupmember_list);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FontChangeCrawler fontChanger = new FontChangeCrawler(this.getAssets(), "opensansregular.ttf","opensansregular.ttf");
        fontChanger.replaceFonts((ViewGroup) this.findViewById(android.R.id.content));
        mBackIV.setOnClickListener(this);
        mProfileIV.setOnClickListener(this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mGroupMemberListRC.setLayoutManager(layoutManager);
        GroupMemberAdapter adapter = new GroupMemberAdapter(this);
        mGroupMemberListRC.setAdapter(adapter);

    }

    @Override
    public void onClick(View view) {
        switch ((view.getId())){
            case R.id.iv_back:
                GroupMemberActivity.this.finish();
                break;
        case R.id.idProfileIV:
            startActivity(new Intent(GroupMemberActivity.this, MyProfileActivity.class));
                break;
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, android.R.anim.slide_out_right);
    }
}

