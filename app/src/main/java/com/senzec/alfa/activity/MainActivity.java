package com.senzec.alfa.activity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import com.facebook.FacebookSdk;
import com.senzec.alfa.R;
import com.senzec.alfa.fragment.LoginFragment;
import com.senzec.alfa.fragment.SignupFragment;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity implements SignupFragment.SignupFragmentCommunicator,LoginFragment.LoginFragmentCommunicator{

    FragmentManager mManager;
    FragmentTransaction mFragmentTransaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        overridePendingTransition(0, android.R.anim.slide_in_left);
        setContentView(R.layout.activity_main);
        FacebookSdk.sdkInitialize(getApplicationContext());
//        printhashkey();
        mManager=getSupportFragmentManager();
        if(savedInstanceState==null){
            SignupFragment signupFragment = new SignupFragment();
            setFragment(signupFragment,"signupFragment");
            signupFragment.setSignupFragmentCommunicator(this);
        }
    }

    private  void setFragment(Fragment fragment, String tagName){
        mFragmentTransaction = mManager.beginTransaction();
        mFragmentTransaction.replace(R.id.frame_main,fragment,tagName);
        mFragmentTransaction.addToBackStack(tagName);
        mFragmentTransaction.commit();
    }

    @Override
    public void gotoLogin() {
        LoginFragment loginFragment = new LoginFragment();
        setFragment(loginFragment,"loginFragment");
        loginFragment.setLoginFragmentCommunicator(this);
    }

    @Override
    public void gotoSignUp() {
        SignupFragment signupFragment = new SignupFragment();
        setFragment(signupFragment,"signupFragment");
        signupFragment.setSignupFragmentCommunicator(this);
    }



    @Override
    public void onBackPressed() {

        Fragment home= mManager.findFragmentByTag("signupFragment");
        if(home!=null)
        {
            if(home.isVisible())
            {
                //exit your application
                MainActivity.this.finish();
            }else{
                mManager.popBackStack();
            }
        }else {
            super.onBackPressed();// optional depending on your needs
        }
    }

    public void printhashkey(){

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.senzec.alfa",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
