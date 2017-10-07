package com.senzec.alfa.fragment;

import android.app.Activity;
import android.content.Context;
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
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.senzec.alfa.R;
import com.senzec.alfa.activity.EditProfileActivity;
import com.senzec.alfa.activity.MyProfileActivity;
import com.senzec.alfa.adapter.LoginParameter;
import com.senzec.alfa.adapter.SignupParameter;
import com.senzec.alfa.font.FontChangeCrawler;
import com.senzec.alfa.model.login.LoginModel;
import com.senzec.alfa.model.login.Result;
import com.senzec.alfa.model.signup.SignupModel;
import com.senzec.alfa.parse_api_adapter.ApiClient;
import com.senzec.alfa.parse_api_adapter.ApiInterface;
import com.senzec.alfa.utils.Consts;
import com.senzec.alfa.utils.SharedPrefClass;
import com.senzec.alfa.model.login.LoginModel.*;

import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ravi on 30/8/17.
 */

public class LoginFragment extends Fragment implements View.OnClickListener {
    private final String TAG = getClass().getSimpleName();
    private String txtId,firstName,lastName,email,birthday,gender,name,txtPhoto;
    View view;
    EditText et_login_email,et_login_password;
    Button btn_login_submit,btn_signup,btn_login,btn_facebook_login;
    ImageView iv_facebook_login;
    LoginFragmentCommunicator communicator;
    private LoginButton loginButton;
    private Context mContext;
    private CallbackManager callbackManager;
    public void setLoginFragmentCommunicator(LoginFragmentCommunicator communicator){
        this.communicator = communicator;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_login,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        FontChangeCrawler fontChanger = new FontChangeCrawler(getActivity().getAssets(), "opensansregular.ttf","opensansregular.ttf");
        fontChanger.replaceFonts((ViewGroup) getActivity().findViewById(android.R.id.content));
        init();
        initFacebookLogin();
        btn_login.setOnClickListener(this);
        btn_signup.setOnClickListener(this);
        iv_facebook_login.setOnClickListener(this);
    }

    private void init(){
        et_login_email = view.findViewById(R.id.et_login_email);
        et_login_password = view.findViewById(R.id.et_login_password);
        btn_login_submit = view.findViewById(R.id.btn_login_submit);
        btn_signup = view.findViewById(R.id.btn_signup);
        btn_login = view.findViewById(R.id.btn_login);
        iv_facebook_login = view.findViewById(R.id.iv_facebook_login);

        btn_login.setTextColor(getResources().getColor(R.color.colorYellow));

        //
        btn_login_submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_signup:
                communicator.gotoSignUp();
                break;
            case R.id.btn_login:
                break;
            case R.id.iv_facebook_login:
                loginButton.performClick();
                break;
            case R.id.btn_login_submit:
         //       startActivity(new Intent((Activity)getContext(), MyProfileActivity.class));
                //performLogin();
                checkValidation();
                break;

        }
    }

    private void initFacebookLogin() {
        loginButton = (LoginButton) view.findViewById(R.id.fb_login_button);
//        loginButton.setLoginBehavior(LoginBehavior.WEB_ONLY);
        // initialize facebook sdk
        callbackManager = CallbackManager.Factory.create();
        loginButton.setReadPermissions("public_profile email,user_photos");

       /* loginButton.setReadPermissions(Arrays.asList("public_profile", "user_about_me", "user_location",
                "email", "user_birthday"));*/
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.e(TAG,"facebook login object: "+object.toString());
                                Log.e(TAG,"facebook login responce: "+response.toString());
                                // Application code
                                if (response.getError() != null) {
                                    // handle error
                                    Log.e(TAG, "onCompleted: facebook login " + response.getError());
                                } else {
                                    try {

                                        txtId = object.getString("id");
                                        System.out.println("Id of person is...."+txtId);
                                        try {
                                            URL imageURL = null;
                                            imageURL = new URL("https://graph.facebook.com/" + txtId + "/picture?type=large");
                                            Log.e("image URL", imageURL.toString());
                                            txtPhoto = imageURL.toString();
                                        } catch (MalformedURLException e) {
                                            e.printStackTrace();
                                        }
                                        if(object.has("first_name"))
                                            firstName = object.getString("first_name");
                                        if(object.has("last_name"))
                                            lastName = object.getString("last_name");
                                        if (object.has("email"))
                                            email = object.getString("email");
                                        if (object.has("birthday"))
                                            birthday = object.getString("birthday");
                                        if (object.has("gender"))
                                            gender = object.getString("gender");
                                        if (object.has("name"))
                                            name = object.getString("name");


                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                    Log.d(TAG, "onCompleted: facebook response " + object.toString());
                                }
                            }
                        });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,birthday,name,link,email,gender,locale");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                Toast.makeText(mContext, "CANCEL", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException e) {
                Toast.makeText(mContext, "Error" + e, Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onError: ", e);
            }
        });
    }

    public interface LoginFragmentCommunicator{
        void gotoSignUp();
    }

    public void checkValidation(){

            if(isValidEmail(et_login_email.getText().toString().trim())){
                if(TextUtils.isEmpty(et_login_password.getText().toString().trim()) == false){
                        performLogin();
                }else{
                    et_login_password.setError("Can't Empty");
                }
            }else{
                et_login_email.setError("Not A Valid Mail");
            }

    }

    public void performLogin(){


        LoginParameter loginParameter = new LoginParameter();

        loginParameter.type = "user";
        loginParameter.email_id = et_login_email.getText().toString();
        loginParameter.password = et_login_password.getText().toString();

        ApiInterface apiInterface = ApiClient.getClient(Consts.BASE_URL).create(ApiInterface.class);
        apiInterface.loginResponse(loginParameter).enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                if(response.isSuccessful() && response.code() == 200) {
                    LoginModel loginModel = response.body();
                    if (loginModel.getResponseCode() == 200) {
                        Toast.makeText(view.getContext(), "Login Success", Toast.LENGTH_LONG).show();
                     //   Result result = loginModel.getResult();
                        new SharedPrefClass(view.getContext()).setLogininfo(loginModel.getResult().getId());
                        startActivity(new Intent((Activity)getContext(), EditProfileActivity.class));
                    } else if(loginModel.getResponseCode() == 404)  {
                        Toast.makeText(view.getContext(), "Login Declined", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                Toast.makeText(view.getContext(), "Login Failed", Toast.LENGTH_LONG).show();
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
