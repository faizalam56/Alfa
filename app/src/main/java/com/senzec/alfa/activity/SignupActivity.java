package com.senzec.alfa.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.senzec.alfa.R;
import com.senzec.alfa.adapter.SignupParameter;
import com.senzec.alfa.font.FontChangeCrawler;
import com.senzec.alfa.model.signup.SignupModel;
import com.senzec.alfa.parse_api_adapter.ApiClient;
import com.senzec.alfa.parse_api_adapter.ApiInterface;
import com.senzec.alfa.utils.Consts;
import com.senzec.alfa.utils.SharedPrefClass;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    View view;
    EditText et_profile,et_email,et_password,et_mobile;
    Button btn_submit,btn_signup,btn_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_signup);
        FontChangeCrawler fontChanger = new FontChangeCrawler(this.getAssets(), "opensansregular.ttf","opensansregular.ttf");
        fontChanger.replaceFonts((ViewGroup) this.findViewById(android.R.id.content));
        init();
        btn_signup.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
    }

    private  void init(){
        et_profile = (EditText) findViewById(R.id.et_profile);
        et_email = (EditText) findViewById(R.id.et_email);
        et_password = (EditText) findViewById(R.id.et_password);
        et_mobile = (EditText) findViewById(R.id.et_mobile);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_signup = (Button) findViewById(R.id.btn_signup);
        btn_login = (Button) findViewById(R.id.btn_login);

        btn_signup.setTextColor(getResources().getColor(R.color.colorYellow));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_signup:
                break;
            case R.id.btn_login:
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                break;
            case R.id.btn_submit:
                checkValidation();
                break;
        }
    }

    public void checkValidation(){
        if(TextUtils.isEmpty(et_profile.getText().toString().trim()) == false){
            if(isValidEmail(et_email.getText().toString().trim())){
                if(TextUtils.isEmpty(et_password.getText().toString().trim()) == false){
                    if(et_mobile.getText().toString().trim().length()==10){
                        performSignup();
                    }else{
                        et_mobile.setError("10 digit");
                    }
                }else{
                    et_password.setError("Can't Empty");
                }
            }else{
                et_email.setError("Not A Valid Mail");
            }

        }else {
            et_profile.setError("Can't Empty");
        }
    }
    public void performSignup(){

        SignupParameter signupParameter = new SignupParameter();

        signupParameter.type = "user";
        signupParameter.user_name = et_profile.getText().toString();
        signupParameter.email_id = et_email.getText().toString();
        signupParameter.password = et_password.getText().toString();
        signupParameter.phone_number = et_mobile.getText().toString();

        ApiInterface apiInterface = ApiClient.getClient(Consts.BASE_URL).create(ApiInterface.class);
        apiInterface.signupResponse(signupParameter).enqueue(new Callback<SignupModel>() {
            @Override
            public void onResponse(Call<SignupModel> call, Response<SignupModel> response) {

                if(response.isSuccessful() && response.code() == 200){
                    Toast.makeText(SignupActivity.this, "Signup Success", Toast.LENGTH_LONG).show();
                    //     new SharedPrefClass(view.getContext()).setLogininfo(response.body().getToken());
                    new SharedPrefClass(SignupActivity.this).setLogininfo(response.body().getResult().getId());
                    startActivity(new Intent(SignupActivity.this, EditProfileActivity.class));
                }else if(response.isSuccessful() && response.code() == 204){
                    Toast.makeText(SignupActivity.this, "Email Already Exist!", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(SignupActivity.this, "Signup Confused", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<SignupModel> call, Throwable t) {
                call.cancel();
                Toast.makeText(SignupActivity.this, "Signup Failed", Toast.LENGTH_LONG).show();
            }
        });

    }

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }
}
