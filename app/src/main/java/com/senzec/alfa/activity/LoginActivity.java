package com.senzec.alfa.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.sdsmdg.tastytoast.TastyToast;
import com.senzec.alfa.R;
import com.senzec.alfa.adapter.LoginParameter;
import com.senzec.alfa.font.FontChangeCrawler;
import com.senzec.alfa.model.login.LoginModel;
import com.senzec.alfa.model.login.SocialLoginRequest;
import com.senzec.alfa.model.signup.SocialSignupRequest;
import com.senzec.alfa.model.signup.SocialSignupResponse;
import com.senzec.alfa.parse_api_adapter.ApiClient;
import com.senzec.alfa.parse_api_adapter.ApiInterface;
import com.senzec.alfa.preference.AppPrefs;
import com.senzec.alfa.utils.ConnectivityManagerClass;
import com.senzec.alfa.utils.Consts;
import com.senzec.alfa.utils.cache.DownloadImageTask;
import com.senzec.alfa.utils.cache.ImagesCache;
import com.senzec.alfa.utils.ProgressClass;
import com.senzec.alfa.utils.SharedPrefClass;

import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private final String TAG = getClass().getSimpleName();
    private String txtId,firstName,lastName,email,birthday,gender,name,txtPhoto;
    View view;
    EditText et_login_email,et_login_password;
    Button btn_login_submit,btn_signup,btn_login,btn_facebook_login;
    ImageView iv_facebook_login, mGoogleSignupIV;
    private LoginButton loginButton;
    private Context mContext;
    private CallbackManager callbackManager;
    AppPrefs prefs;
    private ApiInterface apiInterface;
    //Google
    GoogleSignInOptions gso;
    GoogleApiClient mGoogleApiClient;
    SignInButton signInButton;
    int RC_SIGN_IN = 101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);
       /* FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);*/
        apiInterface = ApiClient.getClient(Consts.BASE_URL).create(ApiInterface.class);
        FontChangeCrawler fontChanger = new FontChangeCrawler(this.getAssets(), "opensansregular.ttf","opensansregular.ttf");
        fontChanger.replaceFonts((ViewGroup) this.findViewById(android.R.id.content));
        init();
        initFacebookLogin();

        btn_login.setTextColor(getResources().getColor(R.color.colorYellow));
        btn_login.setOnClickListener(this);
        btn_signup.setOnClickListener(this);
        iv_facebook_login.setOnClickListener(this);
        btn_login_submit.setOnClickListener(this);
        mGoogleSignupIV.setOnClickListener(this);

        prefs = new AppPrefs(this);

        // Google Sign
        // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // Build a GoogleApiClient with access to the Google Sign-In API and the
// options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(LoginActivity.this)
                .enableAutoManage(LoginActivity.this /* FragmentActivity */, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

                        new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("Oops...")
                                .setContentText("Something went wrong! Try Again")
                                .show();
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    private void init(){
        et_login_email = (EditText) findViewById(R.id.et_login_email);
        et_login_password = (EditText) findViewById(R.id.et_login_password);
        btn_login_submit = (Button) findViewById(R.id.btn_login_submit);
        btn_signup = (Button) findViewById(R.id.btn_signup);
        btn_login = (Button) findViewById(R.id.btn_login);
        iv_facebook_login = (ImageView) findViewById(R.id.iv_facebook_login);
        mGoogleSignupIV = (ImageView) findViewById(R.id.sign_in_button);
        //

    }

    @Override
    public void onClick(View view) {
        if(ConnectivityManagerClass.getInstance().isNetworkAvailable(LoginActivity.this) == true) {
            switch (view.getId()) {
                case R.id.btn_signup:
                    startActivity(new Intent(LoginActivity.this, SignupActivity.class));
                    onFacebookLogout();
                    break;
                case R.id.btn_login:
                    break;
                case R.id.iv_facebook_login:
                    if (prefs.getBoolean(Consts.IS_SOCIAL_LOGGED)) {
                        callSocialLoginApi();
                    } else {
                        loginButton.performClick();
                    }
                    break;
                case R.id.btn_login_submit:

                    checkValidation();
                    break;
                case R.id.sign_in_button:
                    Toast.makeText(view.getContext(), "Google", Toast.LENGTH_LONG).show();
                    signIn();
                    break;

            }
        }else {
            SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.CUSTOM_IMAGE_TYPE);
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
                String profileUrl = null;
                if(response.isSuccessful() && response.code() == 200) {
                    LoginModel loginModel = response.body();
                    if (loginModel.getResponseCode() == 200) {
                        TastyToast.makeText(getApplicationContext(), "Login Successful !", TastyToast.LENGTH_LONG,
                                TastyToast.SUCCESS);
                        try {
                            profileUrl = loginModel.getResult().getProfilePic().trim();
                            if (profileUrl != null) {
                                if(prefs.getString(Consts.PROFILE_URL) != null){
                                    prefs.remove(Consts.PROFILE_URL);
                                    prefs.putString(Consts.PROFILE_URL, Consts.BASE_URL + profileUrl);
                                }else{
                                    prefs.putString(Consts.PROFILE_URL, Consts.BASE_URL + profileUrl);
                                }
                            }
                        }catch (NullPointerException npe){
                            Log.e(TAG, "#Error : "+npe, npe);
                        }
                        new SharedPrefClass(LoginActivity.this).setLogininfo(loginModel.getResult().getId());
                        startActivity(new Intent(LoginActivity.this, ProfiledetailActivity.class));
                    } else if(loginModel.getResponseCode() == 404)  {
                        Toast.makeText(LoginActivity.this, "Login Declined", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_LONG).show();
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

    @Override
    public void onStop() {
        super.onStop();

    }

    // Google
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();
            String photoUrl = "";
          try {
              photoUrl = acct.getPhotoUrl().toString();
          }catch (NullPointerException npe){
              Log.e(TAG, "#Error : "+npe, npe);
          }
            prefs.putString(Consts.TAG_USERID, acct.getId());
            prefs.putString(Consts.TAG_MEDIA,"google");
            prefs.putString(Consts.TAG_PROFILE_PICTURE, photoUrl);
            prefs.putString(Consts.TAG_USERNAME, acct.getDisplayName());
            prefs.putString(Consts.TAG_GENDER,gender);
         //   prefs.putString(Consts.TAG_FULL_NAME,name);
            prefs.putString(Consts.TAG_USEREMAIL, acct.getEmail());

//                                        startActivity(new Intent(LoginActivity.this,MyProfileActivity.class));
            callSocialSignupApi(socialSignupParameter());
        } else {
            GoogleSignInAccount acct = result.getSignInAccount();
            TastyToast.makeText(getApplicationContext(), "Authentication failed ! Try again ", TastyToast.LENGTH_LONG,
                    TastyToast.ERROR);
        }
    }
    //Facebook
    private void initFacebookLogin() {
        loginButton = (LoginButton)findViewById(R.id.fb_login_button);
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

                                        prefs.putString(Consts.TAG_USERID, txtId);
                                        prefs.putString(Consts.TAG_MEDIA,"facebook");
                                        prefs.putString(Consts.TAG_PROFILE_PICTURE,txtPhoto);
                                        prefs.putString(Consts.TAG_USERNAME,firstName);
                                        prefs.putString(Consts.TAG_GENDER,gender);
                                        prefs.putString(Consts.TAG_FULL_NAME,name);
                                        prefs.putString(Consts.TAG_USEREMAIL,email);

//                                        startActivity(new Intent(LoginActivity.this,MyProfileActivity.class));
                                        callSocialSignupApi(socialSignupParameter());

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
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       callbackManager.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }/*else {
            TastyToast.makeText(getApplicationContext(), "Authentication Failed, Try Again!", TastyToast.LENGTH_LONG,
                    TastyToast.CONFUSING);
        }*/
    }

    private void onFacebookLogout() {
        try {
            LoginManager.getInstance().logOut();
            prefs.remove(Consts.TAG_MEDIA);
            prefs.remove(Consts.TAG_USERID);
            prefs.remove(Consts.TAG_MEDIA);
            prefs.putBoolean(Consts.IS_SOCIAL_LOGGED,false);

            disconnectFromFacebook();
        } catch (Exception e) {
            Log.e("LoginActivity", "onFacebookLogout: ", e);
        }
    }

    private void disconnectFromFacebook() {
        Toast.makeText(this,"Logout Success",Toast.LENGTH_SHORT).show();
        if (AccessToken.getCurrentAccessToken() == null) {
            return; // already logged out
        }

        new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE, new GraphRequest
                .Callback() {
            @Override
            public void onCompleted(GraphResponse graphResponse) {

                AccessToken.setCurrentAccessToken(null);
                LoginManager.getInstance().logOut();


            }
        }).executeAsync();
    }

    private SocialSignupRequest socialSignupParameter( ){
        SocialSignupRequest requestParameter = new SocialSignupRequest();
        requestParameter.social_id = prefs.getString(Consts.TAG_USERID);
        requestParameter.social_type = prefs.getString(Consts.TAG_MEDIA);
        requestParameter.type = "user";
        requestParameter.social_type = prefs.getString(Consts.TAG_USEREMAIL);
        requestParameter.password = "123456"; // constant passwod
        requestParameter.user_name = prefs.getString(Consts.TAG_USERNAME);
        requestParameter.phone_number = "123456";  // constant phone number
        return requestParameter;
    }

    private void callSocialSignupApi(SocialSignupRequest request){
        ProgressClass.getProgressInstance().startProgress(LoginActivity.this);

        Call<SocialSignupResponse> call1 = apiInterface.socialSignUp(request);
        call1.enqueue(new Callback<SocialSignupResponse>() {
            @Override
            public void onResponse(Call<SocialSignupResponse> call, Response<SocialSignupResponse> response) {

                ProgressClass.getProgressInstance().stopProgress();
                if(response.isSuccessful()&&response.code()==200) {
                    SocialSignupResponse resource = response.body();
                    if(resource.response_code.equals("200")) {
                        Toast.makeText(LoginActivity.this, "Social Signup successfull", Toast.LENGTH_SHORT).show();
                        SocialSignupResponse.Result result = resource.result;

                        prefs.putBoolean(Consts.IS_SOCIAL_LOGGED, true);
                        new SharedPrefClass(LoginActivity.this).setLogininfo(result._id);
                    }else{
                        ProgressClass.getProgressInstance().stopProgress();
                        callSocialLoginApi();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SocialSignupResponse> call, Throwable t) {
                call.cancel();
                Toast.makeText(LoginActivity.this, "Something Wrong from server", Toast.LENGTH_SHORT).show();
                ProgressClass.getProgressInstance().stopProgress();
            }
        });
    }

    private void callSocialLoginApi(){
        ProgressClass.getProgressInstance().startProgress(LoginActivity.this);

        SocialLoginRequest request = new SocialLoginRequest();
        request.social_id = prefs.getString(Consts.TAG_USERID);

        Call<SocialSignupResponse> call1 = apiInterface.socialLogin(request);
        call1.enqueue(new Callback<SocialSignupResponse>() {
            @Override
            public void onResponse(Call<SocialSignupResponse> call, Response<SocialSignupResponse> response) {

                if(response.isSuccessful()&&response.code()==200) {
                    SocialSignupResponse resource = response.body();
                    Toast.makeText(LoginActivity.this, "Social Login successfull", Toast.LENGTH_SHORT).show();
                    ProgressClass.getProgressInstance().stopProgress();
                } else {
                    Toast.makeText(LoginActivity.this, "Something Wrong", Toast.LENGTH_SHORT).show();
                    ProgressClass.getProgressInstance().stopProgress();
                }
            }

            @Override
            public void onFailure(Call<SocialSignupResponse> call, Throwable t) {
                call.cancel();
                Toast.makeText(LoginActivity.this, "Something Wrong from server", Toast.LENGTH_SHORT).show();
                ProgressClass.getProgressInstance().stopProgress();
            }
        });
    }

    public void funcSweetAlert(){
        new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("Oops...")
                .setContentText("Something went wrong! Try Again")
                .show();
    }
}
