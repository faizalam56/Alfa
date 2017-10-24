package com.senzec.alfa.activity;

import android.content.Intent;
import android.graphics.Bitmap;
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

import com.bumptech.glide.Glide;
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
import com.senzec.alfa.preference.AppPrefs;
import com.senzec.alfa.utils.ConnectivityManagerClass;
import com.senzec.alfa.utils.Consts;
import com.senzec.alfa.utils.SharedPrefClass;
import com.senzec.alfa.utils.cache.DownloadImageTask;
import com.senzec.alfa.utils.cache.ImagesCache;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroupNameActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "GroupNameActivity";
    AppPrefs prefs;
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

        prefs  = new AppPrefs(GroupNameActivity.this);
        loadProfileImage();
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

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        mGroupNameRC.setLayoutManager(layoutManager);
        if(ConnectivityManagerClass.getInstance().isNetworkAvailable(GroupNameActivity.this) == true) {
        getJobPost();
        }else {
            SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(GroupNameActivity.this, SweetAlertDialog.CUSTOM_IMAGE_TYPE);
            sweetAlertDialog.setTitleText("No Network!");
            sweetAlertDialog.setContentText("Press 'OK' to Retry");
            sweetAlertDialog.setCustomImage(R.drawable.ic_disconnected);
            sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    sweetAlertDialog.dismiss();

                }
            })
                    .show();
        }

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

    private void loadProfileImage(){

        String profileURL = prefs.getString(Consts.PROFILE_URL);
        if(profileURL != null) {

            //IMAGE CACHE START
            ImagesCache cache = ImagesCache.getInstance();//Singleton instance handled in ImagesCache class.
            cache.initializeCache();
            Bitmap bm = cache.getImageFromWarehouse(profileURL);
            if (bm != null) {
                Glide.with(GroupNameActivity.this)
                        .load(bm)
                        .into(mProfileIV);
            } else {
                Glide.with(GroupNameActivity.this)
                        .load(profileURL)
                        .error(R.drawable.img_profile)
                        .into(mProfileIV);

                DownloadImageTask imgTask = new DownloadImageTask(cache, mProfileIV, 300, 300);//Since you are using it from `Activity` call second Constructor.
                imgTask.execute(profileURL);
            }

        }
    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, android.R.anim.slide_out_right);
    }
}
