package com.senzec.alfa.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.senzec.alfa.R;
import com.senzec.alfa.activity.EditProfileActivity;
import com.senzec.alfa.adapter.SignupParameter;
import com.senzec.alfa.font.FontChangeCrawler;
import com.senzec.alfa.model.signup.SignupModel;
import com.senzec.alfa.parse_api_adapter.ApiClient;
import com.senzec.alfa.parse_api_adapter.ApiInterface;
import com.senzec.alfa.utils.Consts;
import com.senzec.alfa.utils.SharedPrefClass;

import java.util.List;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ravi on 30/8/17.
 */

public class SignupFragment extends Fragment implements View.OnClickListener {
    View view;
    EditText et_profile,et_email,et_password,et_mobile;
    Button btn_submit,btn_signup,btn_login;
    SignupFragmentCommunicator communicator;

    public void setSignupFragmentCommunicator(SignupFragmentCommunicator communicator){
        this.communicator = communicator;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_signup,container,false);
     //   startActivity(new Intent(view.getContext(), CollegeListActivity.class));
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init();
        btn_submit.setOnClickListener(this);
        btn_signup.setOnClickListener(this);
        btn_login.setOnClickListener(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FontChangeCrawler fontChanger = new FontChangeCrawler(getActivity().getAssets(), "opensansregular.ttf","opensansregular.ttf");
        fontChanger.replaceFonts((ViewGroup) getActivity().findViewById(android.R.id.content));
    }

    private  void init(){
        et_profile = view.findViewById(R.id.et_profile);
        et_email = view.findViewById(R.id.et_email);
        et_password = view.findViewById(R.id.et_password);
        et_mobile = view.findViewById(R.id.et_mobile);
        btn_submit = view.findViewById(R.id.btn_submit);
        btn_signup = view.findViewById(R.id.btn_signup);
        btn_login = view.findViewById(R.id.btn_login);

        btn_signup.setTextColor(getResources().getColor(R.color.colorYellow));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_signup:
                break;
            case R.id.btn_login:
                communicator.gotoLogin();
                break;
            case R.id.btn_submit:
                checkValidation();
                break;
        }
    }

    public interface SignupFragmentCommunicator{
        void gotoLogin();
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
                    Toast.makeText(view.getContext(), "Signup Success", Toast.LENGTH_LONG).show();
               //     new SharedPrefClass(view.getContext()).setLogininfo(response.body().getToken());
                    new SharedPrefClass(view.getContext()).setLogininfo(response.body().getResult().getId());
                    startActivity(new Intent(view.getContext(), EditProfileActivity.class));
                }else if(response.isSuccessful() && response.code() == 204){
                    Toast.makeText(view.getContext(), "Email Already Exist!", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(view.getContext(), "Signup Confused", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<SignupModel> call, Throwable t) {
                call.cancel();
                Toast.makeText(view.getContext(), "Signup Failed", Toast.LENGTH_LONG).show();
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
