package com.senzec.alfa.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.senzec.alfa.R;
import com.senzec.alfa.adapter.GroupMemberAdapter;
import com.senzec.alfa.font.FontChangeCrawler;
import com.senzec.alfa.preference.AppPrefs;
import com.senzec.alfa.utils.Consts;
import com.senzec.alfa.utils.cache.DownloadImageTask;
import com.senzec.alfa.utils.cache.ImagesCache;

public class GroupMemberActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "GroupMemberActivity";
    AppPrefs prefs;
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
        prefs = new AppPrefs(GroupMemberActivity.this);
        loadProfileImage();
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

    private void loadProfileImage(){

        String profileURL = prefs.getString(Consts.PROFILE_URL);
        if(profileURL != null) {

            //IMAGE CACHE START
            ImagesCache cache = ImagesCache.getInstance();//Singleton instance handled in ImagesCache class.
            cache.initializeCache();
            Bitmap bm = cache.getImageFromWarehouse(profileURL);
            if (bm != null) {
                Glide.with(GroupMemberActivity.this)
                        .load(bm)
                        .into(mProfileIV);
            } else {
                Glide.with(GroupMemberActivity.this)
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

