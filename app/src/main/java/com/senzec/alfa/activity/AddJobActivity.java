package com.senzec.alfa.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.senzec.alfa.R;
import com.senzec.alfa.font.FontChangeCrawler;
import com.senzec.alfa.model.add_job.AddJobRequest;
import com.senzec.alfa.model.add_job.AddJobResponse;
import com.senzec.alfa.model.groupfeed.GroupFeedRequest;
import com.senzec.alfa.model.groupfeed.GroupFeedResponseModel;
import com.senzec.alfa.multiselectspinner.KeyPairBoolData;
import com.senzec.alfa.multiselectspinner.MultiSpinnerSearch;
import com.senzec.alfa.multiselectspinner.SpinnerListener;
import com.senzec.alfa.parse_api_adapter.ApiClient;
import com.senzec.alfa.parse_api_adapter.ApiInterface;
import com.senzec.alfa.utils.Consts;
import com.senzec.alfa.utils.ProgressClass;
import com.senzec.alfa.utils.SharedPrefClass;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class AddJobActivity extends AppCompatActivity implements View.OnClickListener {

    String TAG = getClass().getName();
    Button btn_add_job;
    ImageView iv_back;
    MultiSpinnerSearch multiSpinnerGroupName;
    List<KeyPairBoolData> groupNameList;
    private ApiInterface apiInterface;
    List<GroupFeedResponseModel.Result> result;
    EditText mJobProfile,mJobDepartment,mSalary,mLocation,mWorkExperience,mCompanyName,mEmailContact,mDescription;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        overridePendingTransition(0, android.R.anim.slide_in_left);
        setContentView(R.layout.activity_add_job);

        apiInterface = ApiClient.getClient(Consts.BASE_URL).create(ApiInterface.class);

        init();
        btn_add_job.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        FontChangeCrawler fontChanger = new FontChangeCrawler(this.getAssets(), "opensansregular.ttf","opensansregular.ttf");
        fontChanger.replaceFonts((ViewGroup) this.findViewById(android.R.id.content));
        callGroupFeedApi();

    }

    private void init(){
        iv_back = (ImageView) findViewById(R.id.iv_back);
        btn_add_job = (Button) findViewById(R.id.btn_add_job);

        multiSpinnerGroupName = (MultiSpinnerSearch) findViewById(R.id.spnr_groupName);

        mJobProfile = (EditText) findViewById(R.id.et_job_profile);
        mJobDepartment = (EditText) findViewById(R.id.et_job_department);
        mSalary = (EditText) findViewById(R.id.et_salary);
        mLocation = (EditText) findViewById(R.id.et_location);
        mWorkExperience = (EditText) findViewById(R.id.et_work_experience);
        mCompanyName = (EditText) findViewById(R.id.et_company_name);
        mEmailContact = (EditText) findViewById(R.id.et_email_contact);
        mDescription = (EditText) findViewById(R.id.et_description);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_add_job:
//                startActivity(new Intent(AddJobActivity.this,CompaniesActivity.class));
                if(mJobProfile.getText().toString().trim().length()==0){
                    mJobProfile.requestFocus();
                    Toast.makeText(this,"Please enter the profile",Toast.LENGTH_SHORT).show();
                }else if(mJobDepartment.getText().toString().trim().length()==0){
                    mJobDepartment.requestFocus();
                    Toast.makeText(this,"Please enter the job department",Toast.LENGTH_SHORT).show();
                }else if(mSalary.getText().toString().trim().length()==0){
                    mSalary.requestFocus();
                    Toast.makeText(this,"Please enter the salary",Toast.LENGTH_SHORT).show();
                }else if(mLocation.getText().toString().trim().length()==0){
                    mLocation.requestFocus();
                    Toast.makeText(this,"Please enter the location",Toast.LENGTH_SHORT).show();
                }else if(mWorkExperience.getText().toString().trim().length()==0){
                    mWorkExperience.requestFocus();
                    Toast.makeText(this,"Please enter the experience",Toast.LENGTH_SHORT).show();
                }else if(mCompanyName.getText().toString().trim().length()==0){
                    mCompanyName.requestFocus();
                    Toast.makeText(this,"Please enter the company name",Toast.LENGTH_SHORT).show();
                }else if(mEmailContact.getText().toString().trim().length()==0){
                    mEmailContact.requestFocus();
                    Toast.makeText(this,"Please enter the email contact",Toast.LENGTH_SHORT).show();
                }else if(mDescription.getText().toString().trim().length()==0){
                    mDescription.requestFocus();
                    Toast.makeText(this,"Please enter the description",Toast.LENGTH_SHORT).show();
                }else{
                    callAddJobApi(getAddJobParameter());
                }
                break;
            case R.id.iv_back:
                AddJobActivity.this.finish();
                break;
        }
    }

    @Override
    public void finish() {
        super.finish();
//        overridePendingTransition(0, android.R.anim.slide_out_right);
        overridePendingTransition(0, android.R.anim.slide_out_right);

    }

    private void callGroupFeedApi(){
        ProgressClass.getProgressInstance().startProgress(AddJobActivity.this);
        GroupFeedRequest request = new GroupFeedRequest();
        request.user_id = new SharedPrefClass(AddJobActivity.this).getLoginInfo();

        Call<GroupFeedResponseModel> call1 = apiInterface.findGroupFeed(request);
        call1.enqueue(new Callback<GroupFeedResponseModel>() {
            @Override
            public void onResponse(Call<GroupFeedResponseModel> call, retrofit2.Response<GroupFeedResponseModel> response) {


                if(response.isSuccessful()&&response.code()==200) {
                    GroupFeedResponseModel resource = response.body();
                    result=resource.result;
                    listOfGroupName(result);
                    ProgressClass.getProgressInstance().stopProgress();
                } else {
                    Toast.makeText(AddJobActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                    ProgressClass.getProgressInstance().stopProgress();
                }
            }

            @Override
            public void onFailure(Call<GroupFeedResponseModel> call, Throwable t) {
                call.cancel();
                Toast.makeText(AddJobActivity.this, "Something Wrong from server", Toast.LENGTH_SHORT).show();
                ProgressClass.getProgressInstance().stopProgress();
            }
        });
    }

    private void listOfGroupName(List<GroupFeedResponseModel.Result> result){

//        List<String> list = Arrays.asList(getResources().getStringArray(R.array.groupname));
        List<String> listGroupName = Arrays.asList(groupName(result));
        List<String> listGroupId = Arrays.asList(groupId(result));
        groupNameList = new ArrayList<>();
        for(int i=0;i<listGroupName.size();i++){
            KeyPairBoolData h = new KeyPairBoolData();
//            h.setId(i + 1);
            h.setId(listGroupId.get(i));
            h.setName(listGroupName.get(i));
            h.setSelected(false);
            groupNameList.add(h);
        }
        System.out.println("Selected..."+groupNameList.toString());
        multiSpinnerGroupName.setItems(groupNameList, -1, new SpinnerListener() {

            @Override
            public void onItemsSelected(List<KeyPairBoolData> items) {

                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).isSelected()) {
                        Log.i(TAG, i + " : " + items.get(i).getName() + " : " + items.get(i).isSelected());
                    }
                }
            }
        });
        multiSpinnerGroupName.setLimit(2, new MultiSpinnerSearch.LimitExceedListener() {
            @Override
            public void onLimitListener(KeyPairBoolData data) {
                Toast.makeText(AddJobActivity.this,
                        "Topic Selection Limit exceed ", Toast.LENGTH_LONG).show();
            }
        });
    }

    private String[] groupName(List<GroupFeedResponseModel.Result> result){
        List<GroupFeedResponseModel.Result> resource=result;
        String[] groupName = new String[resource.size()];
        if(resource!=null){
            for(int i=0;i<resource.size();i++){
                GroupFeedResponseModel.Result result1 =resource.get(i);
                groupName[i]=result1.group_name;
            }
            return groupName;
        }
        return groupName;
    }
    private String[] groupId(List<GroupFeedResponseModel.Result> result){
        List<GroupFeedResponseModel.Result> resource=result;
        String[] groupId = new String[resource.size()];
        if(resource!=null){
            for(int i=0;i<resource.size();i++){
                GroupFeedResponseModel.Result result1 =resource.get(i);
                groupId[i]=result1._id;
            }
            return groupId;
        }
        return groupId;
    }
    private AddJobRequest getAddJobParameter(){
        AddJobRequest request = new AddJobRequest();
        request.company_name=mCompanyName.getText().toString();
        request.contact_email=mEmailContact.getText().toString();
        request.description=mDescription.getText().toString();
        request.job_experience=mWorkExperience.getText().toString();
        request.job_name=mJobProfile.getText().toString();
        request.job_type=mJobDepartment.getText().toString();
        request.salary=mSalary.getText().toString();
        request.group_id=multiSpinnerGroupName.getSelectedIds();
        request.job_location=mLocation.getText().toString();
        request.user_id=getUserId();
        return request;
    }

    private String getUserId(){
        SharedPrefClass prefClass = new SharedPrefClass(this);
        String userId = prefClass.getLoginInfo();
        return userId;
    }

   /* private List<String> getSelectedGroupId(){
        List<KeyPairBoolData> topicList = multiSpinnerGroupName.getSelectedItems();

        List<String> groupId = new ArrayList<>();
        for(int i=0;i<topicList.size();i++){
            KeyPairBoolData data = topicList.get(i);
            groupId.add(data.getId());
        }
        System.out.println("Top..."+groupId);
        return groupId;
    }*/

    private void callAddJobApi(AddJobRequest addJobRequest){
        ProgressClass.getProgressInstance().startProgress(AddJobActivity.this);

        Call<AddJobResponse> call1 = apiInterface.addJob(addJobRequest);
        call1.enqueue(new Callback<AddJobResponse>() {
            @Override
            public void onResponse(Call<AddJobResponse> call, retrofit2.Response<AddJobResponse> response) {

                if(response.isSuccessful()&&response.code()==200) {
                    AddJobResponse resource = response.body();
                    Toast.makeText(AddJobActivity.this, "Job Added Successfully", Toast.LENGTH_SHORT).show();
                    ProgressClass.getProgressInstance().stopProgress();
                    startActivity(new Intent(AddJobActivity.this,GroupNameActivity.class));

                }else{
                    Toast.makeText(AddJobActivity.this, "Something Wrong from server", Toast.LENGTH_SHORT).show();
                    ProgressClass.getProgressInstance().stopProgress();
                }

            }
            @Override
            public void onFailure(Call<AddJobResponse> call, Throwable t) {
                call.cancel();
                Toast.makeText(AddJobActivity.this, "Something Wrong from server", Toast.LENGTH_SHORT).show();
                ProgressClass.getProgressInstance().stopProgress();
            }
        });
    }
}
