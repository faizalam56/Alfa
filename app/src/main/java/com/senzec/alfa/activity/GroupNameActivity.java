package com.senzec.alfa.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.senzec.alfa.R;
import com.senzec.alfa.adapter.GroupNameAdapter;
import com.senzec.alfa.adapter.GroupNameParameter;
import com.senzec.alfa.adapter.SignupParameter;
import com.senzec.alfa.font.FontChangeCrawler;
import com.senzec.alfa.model.group_name.GroupNameWebApiModel;
import com.senzec.alfa.model.group_name.Result;
import com.senzec.alfa.model.signup.SignupModel;
import com.senzec.alfa.parse_api_adapter.ApiClient;
import com.senzec.alfa.parse_api_adapter.ApiInterface;
import com.senzec.alfa.utils.Consts;
import com.senzec.alfa.utils.SharedPrefClass;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroupNameActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView mGroupNameRC;
    GroupNameAdapter adapter;
    ImageView mBackIV, mProfileIV;
    TextView mGroupNameTV;
    FloatingActionButton mFloatingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        overridePendingTransition(0, android.R.anim.slide_in_left);
        setContentView(R.layout.activity_group_name);

        mBackIV = (ImageView)findViewById(R.id.idBackIV);
        mGroupNameTV = (TextView)findViewById(R.id.idGroupNameTV);
        mProfileIV = (ImageView)findViewById(R.id.idProfileIV);
        mGroupNameRC = (RecyclerView) findViewById(R.id.rv_group_feed);
        mFloatingButton = (FloatingActionButton) findViewById(R.id.idFab);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FontChangeCrawler fontChanger = new FontChangeCrawler(this.getAssets(), "opensansregular.ttf","opensansregular.ttf");
        fontChanger.replaceFonts((ViewGroup) this.findViewById(android.R.id.content));
        mBackIV.setOnClickListener(this);
        mGroupNameTV.setOnClickListener(GroupNameActivity.this);
        mProfileIV.setOnClickListener(GroupNameActivity.this);
        mFloatingButton.setOnClickListener(this);

        getJobPost();

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mGroupNameRC.setLayoutManager(layoutManager);
        /*adapter = new GroupNameAdapter(GroupNameActivity.this);
        mGroupNameRC.setAdapter(adapter);*/

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.idProfileIV:
        //        Toast.makeText(GroupNameActivity.this, "Profile ", Toast.LENGTH_LONG).show();
                startActivity(new Intent(GroupNameActivity.this, MyProfileActivity.class));
                break;
            case R.id.idGroupNameTV:
        //        Toast.makeText(GroupNameActivity.this, "Profile ", Toast.LENGTH_LONG).show();
                startActivity(new Intent(GroupNameActivity.this, GroupMemberActivity.class));
                break;
            case R.id.idBackIV:
        //        Toast.makeText(GroupNameActivity.this, "Profile ", Toast.LENGTH_LONG).show();
                startActivity(new Intent(GroupNameActivity.this, GroupFeedActivity.class));
                break;
            case R.id.idFab:
        //        Toast.makeText(GroupNameActivity.this, "Profile ", Toast.LENGTH_LONG).show();
                startActivity(new Intent(GroupNameActivity.this, AddJobActivity.class));
                break;
        }
    }


    public void getJobPost(){

        GroupNameParameter  groupNameParameter = new GroupNameParameter();
    //    String currentGroupName = getIntent().getStringExtra("current_group");
        String currentGroupName = new SharedPrefClass(GroupNameActivity.this).getCurrentGroup();
        groupNameParameter.group_name = currentGroupName;


        ApiInterface apiInterface = ApiClient.getClient(Consts.BASE_URL).create(ApiInterface.class);
        apiInterface.getAllJobPost(groupNameParameter).enqueue(new Callback<GroupNameWebApiModel>() {
            @Override
            public void onResponse(Call<GroupNameWebApiModel> call, Response<GroupNameWebApiModel> response) {

                if(response.isSuccessful() && response.code() == 200){
                    if(response.body().getResponseCode() == 200) {
                  //      Toast.makeText(GroupNameActivity.this, "Success", Toast.LENGTH_LONG).show();

                        List<Result> groupNameResultList = response.body().getResults();

                        adapter = new GroupNameAdapter(GroupNameActivity.this, groupNameResultList);
                        mGroupNameRC.setAdapter(adapter);
                    }
                }else {
                    Toast.makeText(GroupNameActivity.this, " Confused", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<GroupNameWebApiModel> call, Throwable t) {
                call.cancel();
                Toast.makeText(GroupNameActivity.this, "Failed", Toast.LENGTH_LONG).show();
            }
        });

    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, android.R.anim.slide_out_right);
    }
}
