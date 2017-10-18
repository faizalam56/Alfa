package com.senzec.alfa.activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import com.senzec.alfa.R;
import com.senzec.alfa.model.profile_detail.ProfileDetailModel;
import com.senzec.alfa.parse_api_adapter.ApiClient;
import com.senzec.alfa.parse_api_adapter.ApiInterface;
import com.senzec.alfa.utils.Consts;
import com.senzec.alfa.utils.SharedPrefClass;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfiledetailActivity extends Activity {

    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profiledetail);

        funcUserProfile();
    }

    private void funcUserProfile()
    {
        apiInterface = ApiClient.getClient(Consts.BASE_URL).create(ApiInterface.class);
        String user_id = new SharedPrefClass(ProfiledetailActivity.this).getLoginInfo();
        Call<ProfileDetailModel> call6 = apiInterface.profileDetailResponse(user_id);
        call6.enqueue(new Callback<ProfileDetailModel>() {
            @Override
            public void onResponse(Call<ProfileDetailModel> call, Response<ProfileDetailModel> response) {

                if (response.isSuccessful() && response.code() == 200) {
                    if(response.body().getResponseCode() == 200){
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
}
