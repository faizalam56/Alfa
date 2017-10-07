package com.senzec.alfa.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Divakar on 7/6/2017.
 */

public class SharedPrefClass {

    Context _context;
    private static final String TAG = SharedPrefClass.class.getSimpleName();
    SharedPreferences sharedPreferences ;
    SharedPreferences.Editor editor ;

    private static final String PREF_NAME = "ProplinPref";
    private static final String LOGIN_KEY = "login_info";


    public SharedPrefClass(Context _context)
    {
        this._context = _context;
    }



    public boolean IS_LOGIN()
    {
        boolean staus = false;

        if(getLoginInfo().length()>0){
            staus = true;
        }
        return staus;
    }

    public void setLogininfo(String token)
    {
        sharedPreferences = _context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(LOGIN_KEY, token);
        editor.commit();
    }
    public String getLoginInfo()
    {
        sharedPreferences = _context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(LOGIN_KEY, "");
    }
}
