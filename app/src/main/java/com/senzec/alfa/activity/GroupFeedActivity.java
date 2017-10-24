package com.senzec.alfa.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.senzec.alfa.R;
import com.senzec.alfa.adapter.GroupFeedAdapter;
import com.senzec.alfa.font.FontChangeCrawler;
import com.senzec.alfa.model.groupfeed.GroupFeedRequest;
import com.senzec.alfa.model.groupfeed.GroupFeedResponseModel;
import com.senzec.alfa.parse_api_adapter.ApiClient;
import com.senzec.alfa.parse_api_adapter.ApiInterface;
import com.senzec.alfa.preference.AppPrefs;
import com.senzec.alfa.utils.ConnectivityManagerClass;
import com.senzec.alfa.utils.Consts;
import com.senzec.alfa.utils.ProgressClass;
import com.senzec.alfa.utils.SharedPrefClass;
import com.senzec.alfa.utils.cache.DownloadImageTask;
import com.senzec.alfa.utils.cache.ImagesCache;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;

public class GroupFeedActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "GroupFeedActivity";
    AppPrefs prefs;
    RecyclerView rcGroupfeed;
    ImageView iv_menu, mProfileIV, mNavigationIV;
   // ArrayList<GroupfeedModel> groupfeedModelArrayList = null;
    EditText mFeedSearch;
    GroupFeedAdapter adapter;
    private ApiInterface apiInterface;
    List<GroupFeedResponseModel.Result> result;
    Button mGroupBtn, mSubGroupBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        overridePendingTransition(0, android.R.anim.slide_in_left);
        setContentView(R.layout.activity_group_feed);

        mNavigationIV = (ImageView)findViewById(R.id.idNavigationIV);
        mProfileIV = (ImageView)findViewById(R.id.idProfileIV);
        mFeedSearch = (EditText)findViewById(R.id.idFeedSearch);
        mGroupBtn = (Button)findViewById(R.id.idGroupBtn);
        mSubGroupBtn = (Button)findViewById(R.id.idSubGroupBtn);
        rcGroupfeed = (RecyclerView) findViewById(R.id.rv_group_feed);

        prefs = new AppPrefs(GroupFeedActivity.this);
        loadProfileImage();
        if(ConnectivityManagerClass.getInstance().isNetworkAvailable(GroupFeedActivity.this) == true) {
        callGroupFeedApi();
    }else {
            SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(GroupFeedActivity.this, SweetAlertDialog.CUSTOM_IMAGE_TYPE);
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
    protected void onStart() {
        super.onStart();

        FontChangeCrawler fontChanger = new FontChangeCrawler(this.getAssets(), "opensansregular.ttf","opensansregular.ttf");
        fontChanger.replaceFonts((ViewGroup) this.findViewById(android.R.id.content));
        mNavigationIV.setOnClickListener(this);
        mProfileIV.setOnClickListener(GroupFeedActivity.this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rcGroupfeed.setLayoutManager(layoutManager);
    }

    @Override
    public void onClick(View view) {
        switch ((view.getId())){
            case R.id.idProfileIV:
       //         Toast.makeText(GroupFeedActivity.this, "Profile ", Toast.LENGTH_LONG).show();
                startActivity(new Intent(GroupFeedActivity.this, MyProfileActivity.class));
                break;
            case R.id.idNavigationIV:
                Toast.makeText(GroupFeedActivity.this, "Under Development ", Toast.LENGTH_LONG).show();
                break;

        }
    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, android.R.anim.slide_out_right);
    }

    private void callGroupFeedApi(){
        ProgressClass.getProgressInstance().startProgress(GroupFeedActivity.this);
        GroupFeedRequest request = new GroupFeedRequest();
        request.user_id = new SharedPrefClass(GroupFeedActivity.this).getLoginInfo();

        apiInterface = ApiClient.getClient(Consts.BASE_URL).create(ApiInterface.class);
        Call<GroupFeedResponseModel> call1 = apiInterface.findGroupFeed(request);
        call1.enqueue(new Callback<GroupFeedResponseModel>() {
            @Override
            public void onResponse(Call<GroupFeedResponseModel> call, retrofit2.Response<GroupFeedResponseModel> response) {


                if(response.isSuccessful()&&response.code()==200) {
                    GroupFeedResponseModel resource = response.body();
                     result=resource.result;
                    groupFeedList(result);
                    ProgressClass.getProgressInstance().stopProgress();
                    } else {
                        Toast.makeText(GroupFeedActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                        ProgressClass.getProgressInstance().stopProgress();
                    }
                }

            @Override
            public void onFailure(Call<GroupFeedResponseModel> call, Throwable t) {
                call.cancel();
                Toast.makeText(GroupFeedActivity.this, "Something Wrong from server", Toast.LENGTH_SHORT).show();
                ProgressClass.getProgressInstance().stopProgress();
            }
        });
    }

    private void groupFeedList(List<GroupFeedResponseModel.Result> result){
        if(result!=null) {
            adapter = new GroupFeedAdapter(this, result);
            rcGroupfeed.setAdapter(adapter);
            mFeedSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    String strFilter = mFeedSearch.getText().toString().toUpperCase();
//                    adapter.getFilter().filter(strFilter);
                    adapter.getFilter().filter(charSequence.toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {

               /* String strFilter = editable.toString();
                frontHolderAdapter.getFilter().filter(strFilter);*/

                }
            });
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
                Glide.with(GroupFeedActivity.this)
                        .load(bm)
                        .into(mProfileIV);
            } else {
                Glide.with(GroupFeedActivity.this)
                        .load(profileURL)
                        .error(R.drawable.img_profile)
                        .into(mProfileIV);

                DownloadImageTask imgTask = new DownloadImageTask(cache, mProfileIV, 300, 300);//Since you are using it from `Activity` call second Constructor.
                imgTask.execute(profileURL);
            }

        }
    }

}

