package com.senzec.alfa.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.senzec.alfa.R;
import com.senzec.alfa.adapter.CustomAcademicAdapter;
import com.senzec.alfa.adapter.CustomJobInfoAdapter;
import com.senzec.alfa.font.FontChangeCrawler;
import com.senzec.alfa.model.academic.AcademicJobModel;
import com.senzec.alfa.parse_api_adapter.ApiClientJSON;
import com.senzec.alfa.parse_api_adapter.ApiInterface;
import com.senzec.alfa.to_json.AcademicMenuItem;
import com.senzec.alfa.to_json.AcademicWithMenu;
import com.senzec.alfa.to_json.JobMenuItem;
import com.senzec.alfa.utils.Consts;
import com.senzec.alfa.utils.ProgressClass;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher{

    Button mProfileContinueBtn, mAddAcademicBtn, mAddJobBtn;
    ListView mAcademicList, mJobInfoList;
    int countAcademic=1, countJob = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        mAcademicList = (ListView)findViewById(R.id.idAcademic);
        mJobInfoList = (ListView)findViewById(R.id.idJobInfo);

        mAddAcademicBtn = (Button)findViewById(R.id.idAddAcademic);
        mAddJobBtn = (Button)findViewById(R.id.idAddJob);
        mProfileContinueBtn = (Button)findViewById(R.id.btn_profile_continue) ;

        // FONT
        FontChangeCrawler fontChanger = new FontChangeCrawler(getAssets(), "opensansregular.ttf","opensansregular.ttf");
        fontChanger.replaceFonts((ViewGroup) findViewById(android.R.id.content));
    }

    @Override
    protected void onStart() {
        super.onStart();

        academicListView(countAcademic);
        jobListView(countJob);
        // CLICK-EVENT
        mAddAcademicBtn.setOnClickListener(this);
        mAddJobBtn.setOnClickListener(this);
        mProfileContinueBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.idAddAcademic:
                countAcademic++;
                academicListView(countAcademic);
                break;
            case R.id.idAddJob:
                countJob++;
                jobListView(countJob);
                break;
            case R.id.btn_profile_continue:
          //      startActivity(new Intent(EditProfileActivity.this, CollegeListActivity.class));
                submitAcademicJobInfo();
      //          Toast.makeText(EditProfileActivity.this, "Academic : "+countAcademic+", Job : "+countJob, Toast.LENGTH_LONG).show();
                break;
        }
    }


    public void academicListView(int countAcademic){
        CustomAcademicAdapter academicAdapter = new CustomAcademicAdapter(EditProfileActivity.this, countAcademic);
        mAcademicList.setAdapter(academicAdapter);
        academicAdapter.notifyDataSetChanged();
        setListViewHeightBasedOnChildren(mAcademicList);
    }
    public void jobListView(int countJob){
        CustomJobInfoAdapter jobInfoAdapter = new CustomJobInfoAdapter(EditProfileActivity.this, countJob);
        mJobInfoList.setAdapter(jobInfoAdapter);
        jobInfoAdapter.notifyDataSetChanged();
        setListViewHeightBasedOnChildren(mJobInfoList);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }
    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    //    etDegree.setBackgroundDrawable(getResources().getDrawable(R.drawable.rounded_rect_yellow));
    }
    @Override
    public void afterTextChanged(Editable editable) {
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
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

    public void submitAcademicJobInfo(){
      //  String s = mAcademicList.getItemAtPosition(0).toString();
        List<AcademicMenuItem> academic_info = new ArrayList<>();
        List<JobMenuItem> job_info = new ArrayList<>();
        View parentViewAcademic = null;
        View parentViewJob = null;

        for (int i = 0; i < countAcademic; i++) {
            parentViewAcademic = getViewByPosition(i, mAcademicList);
         //   parentViewAcademic = (View) mAcademicList.getParent();
            String strDegree = ((TextView) parentViewAcademic.findViewById(R.id.idDegreeET)).getText().toString().trim();
            String strInstitute = ((TextView) parentViewAcademic.findViewById(R.id.idInstituteName)).getText().toString().trim();
            String strAcademicJY = ((TextView) parentViewAcademic.findViewById(R.id.idJoinYearAcademicET)).getText().toString().trim();
            String strAcademicFY = ((TextView) parentViewAcademic.findViewById(R.id.idFinalYearAcademicET)).getText().toString().trim();

            academic_info.add(new AcademicMenuItem(strDegree, strInstitute, Integer.parseInt(strAcademicJY), Integer.parseInt(strAcademicFY)));
        }

        for (int i = 0; i < countJob; i++) {
            parentViewJob = getViewByPosition(i, mJobInfoList);
            //  parentViewJob = (View) mJobInfoList.getParent();
            String strDesignation = ((TextView) parentViewJob.findViewById(R.id.idDesignationET)).getText().toString().trim();
            String strCompany = ((TextView) parentViewJob.findViewById(R.id.idCompanyName)).getText().toString().trim();
            String strJobJY = ((TextView) parentViewJob.findViewById(R.id.idJoinYearJobET)).getText().toString().trim();
            String strJobFY = ((TextView) parentViewJob.findViewById(R.id.idFinalYearJobET)).getText().toString().trim();

            job_info.add(new JobMenuItem(strDesignation, Integer.parseInt(strJobJY), strCompany, Integer.parseInt(strJobFY)));
        }

        //    convertToJSON( strDegree, strInstitute, strAcademicJY, strAcademicFY, strDesignation, strCompany, strJobJY, strJobFY);
     //   Toast.makeText(EditProfileActivity.this, "output : "+strDegree+strInstitute+strAcademicJY+strAcademicFY+"="+strDesignation+strCompany+strJobJY+strJobFY, Toast.LENGTH_LONG).show();

        AcademicWithMenu profileMenu =
                new AcademicWithMenu("59d6166ad4689273cbafb9c6", academic_info, job_info);

        Gson gson = new Gson();
        String profileJson = gson.toJson(profileMenu);
        System.out.print(profileJson);

        sendJsonRequest(profileJson);
    }

    public View getViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition
                + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }

    public void sendJsonRequest(String profileJson){

    //    Toast.makeText(EditProfileActivity.this, "Progress Started", Toast.LENGTH_LONG).show();
        ProgressClass.getProgressInstance().startProgress(EditProfileActivity.this);
        ApiInterface apiInterface = ApiClientJSON.getClient(Consts.BASE_URL).create(ApiInterface.class);
        apiInterface.getUser(profileJson).enqueue(new Callback<AcademicJobModel>() {
            @Override
            public void onResponse(Call<AcademicJobModel> call, Response<AcademicJobModel> response) {
                if(response.isSuccessful() && response.code() == 200) {
                    AcademicJobModel academicJobModel = response.body();
                    if (academicJobModel.getResponseCode() == 200) {
                        ProgressClass.getProgressInstance().stopProgress();
                        Toast.makeText(EditProfileActivity.this, "Login Success", Toast.LENGTH_LONG).show();
                        new SweetAlertDialog(EditProfileActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                                .setTitleText("Good job!")
                                .setContentText("Data save succesfully!")
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        sDialog.dismissWithAnimation();
                                        startActivity(new Intent(EditProfileActivity.this, CollegeListActivity.class));
                                    }
                                })
                                .show();

                    } else if(academicJobModel.getResponseCode() == 404)  {
                        Toast.makeText(EditProfileActivity.this, "Login Declined", Toast.LENGTH_LONG).show();
                    }
                }
             //   Toast.makeText(EditProfileActivity.this, "Confused", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<AcademicJobModel> call, Throwable t) {
                call.cancel();
                Toast.makeText(EditProfileActivity.this, "Login Failed", Toast.LENGTH_LONG).show();
            }
        });

    }
}
