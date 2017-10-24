package com.senzec.alfa.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.senzec.alfa.R;
import com.senzec.alfa.adapter.CustomAcademicAdapter;
import com.senzec.alfa.adapter.CustomJobInfoAdapter;
import com.senzec.alfa.adapter.ProfileAcademicAdapter;
import com.senzec.alfa.adapter.ProfileJobInfoAdapter;
import com.senzec.alfa.model.profile_detail.AcademicInfo;
import com.senzec.alfa.model.profile_detail.JobInfo;
import com.senzec.alfa.model.profile_detail.ProfileDetailModel;
import com.senzec.alfa.model.profile_detail.Result;
import com.senzec.alfa.parse_api_adapter.ApiClient;
import com.senzec.alfa.parse_api_adapter.ApiInterface;
import com.senzec.alfa.utils.Consts;
import com.senzec.alfa.utils.SharedPrefClass;
import com.senzec.alfa.utils.cache.DownloadImageTask;
import com.senzec.alfa.utils.cache.ImagesCache;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfiledetailActivity extends Activity {

    ApiInterface apiInterface;
    ListView mAcademicList, mJobInfoList;
    TextView tvName, tvEmail, tvCurrentCompany, tvPhoneNo, tvCV;
    ImageView mProfileIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profiledetail);

        mProfileIV = (ImageView)findViewById(R.id.idProfileImage) ;
        mAcademicList = (ListView)findViewById(R.id.idAcademic);
        mJobInfoList = (ListView)findViewById(R.id.idJobInfo);
        initView();
        funcCallWebApiForProfile();
    }

    @Override
    protected void onStart() {
        super.onStart();

        /*academicListView(3);
        jobListView(2);*/
    }

    private void initView(){

        tvName =  (TextView)findViewById(R.id.idNameTV);
        tvEmail = (TextView)findViewById(R.id.idEmailTV);
        tvCurrentCompany = (TextView)findViewById(R.id.idCurrentCompanyTV);
        tvPhoneNo = (TextView)findViewById(R.id.idPhoneTV);
        tvCV = (TextView)findViewById(R.id.idCvTv);

    }

    private void funcCallWebApiForProfile()
    {
        apiInterface = ApiClient.getClient(Consts.BASE_URL).create(ApiInterface.class);
        String user_id = new SharedPrefClass(ProfiledetailActivity.this).getLoginInfo();
        Call<ProfileDetailModel> call6 = apiInterface.profileDetailResponse(user_id);
        call6.enqueue(new Callback<ProfileDetailModel>() {
            @Override
            public void onResponse(Call<ProfileDetailModel> call, Response<ProfileDetailModel> response) {

                if (response.isSuccessful() && response.code() == 200) {
                    if(response.body().getResponseCode() == 200){

                        ProfileDetailModel profileDetailModel = response.body();
                        funcExtractProfileData(profileDetailModel);
                        Toast.makeText(ProfiledetailActivity.this, "Success", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(ProfiledetailActivity.this, "Confusion", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ProfileDetailModel> call, Throwable t) {
            call.cancel();
                Toast.makeText(ProfiledetailActivity.this, "Failed!", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void funcExtractProfileData(ProfileDetailModel profileDetailModel){
        Result result = profileDetailModel.getResult();
        List<AcademicInfo> academicInfosList = result.getAcademicInfo();
        List<JobInfo> jobInfoList = result.getJobInfo();
        setPersonalInfo(result);
        setAcademicListView(academicInfosList);
        setJobListView(jobInfoList);
    }

    private void setPersonalInfo(Result result){

        result.getProfilePic();
        tvName.setText(result.getUserName());
        tvEmail.setText(result.getEmailId());
        tvCurrentCompany.setText(result.getCurrentCompanyName());
        tvPhoneNo.setText(String.valueOf(result.getPhoneNumber()));
        tvCV.setText(getFileName(result.getCv()));
    }

    private void loadProfileImage(String profileURL){

        if(profileURL != null) {

            //IMAGE CACHE START
            ImagesCache cache = ImagesCache.getInstance();//Singleton instance handled in ImagesCache class.
            cache.initializeCache();
            Bitmap bm = cache.getImageFromWarehouse(profileURL);
            if (bm != null) {
                Glide.with(ProfiledetailActivity.this)
                        .load(bm)
                        .into(mProfileIV);
            } else {
                Glide.with(ProfiledetailActivity.this)
                        .load(profileURL)
                        .into(mProfileIV);

                DownloadImageTask imgTask = new DownloadImageTask(cache, mProfileIV, 300, 300);//Since you are using it from `Activity` call second Constructor.
                imgTask.execute(profileURL);
            }

        }
    }


    private String getFileName(String filePath){
        return filePath.substring(filePath.lastIndexOf("/")+1);
    }

    private void setAcademicListView(List<AcademicInfo> academicInfosList){
        ProfileAcademicAdapter academicAdapter = new ProfileAcademicAdapter(ProfiledetailActivity.this, academicInfosList);
        mAcademicList.setAdapter(academicAdapter);
        academicAdapter.notifyDataSetChanged();
        setListViewHeightBasedOnChildren(mAcademicList);
    }
    private void setJobListView(List<JobInfo> jobInfoList){
        ProfileJobInfoAdapter jobInfoAdapter = new ProfileJobInfoAdapter(ProfiledetailActivity.this, jobInfoList);
        mJobInfoList.setAdapter(jobInfoAdapter);
        jobInfoAdapter.notifyDataSetChanged();
        setListViewHeightBasedOnChildrenLast(mJobInfoList);
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount()-1; i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    public static void setListViewHeightBasedOnChildrenLast(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
            return;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

}
