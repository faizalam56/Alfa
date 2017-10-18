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
    private static final String STORAGE_TYPE = "internal_camera";
    private static final String STORAGE_TYPE_FILE = "internal_camera_file";
    private static final String POST_SENDER_ID = "post_sender_id";
    private static final String POST_ID = "post_id";
    private static final String CURRENT_GROUP = "current_group";
    private static final String CHAT_SENDER_ID = "chat_sender_id";



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
    // STORAGE-TYPE
    public String getStorageType()
    {
        sharedPreferences = _context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        return sharedPreferences.getString(STORAGE_TYPE, null);
    }
    public void setStorageType(String storage)
    {
        sharedPreferences = _context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(STORAGE_TYPE, storage);
        editor.commit();

    }
    // STORAGE-TYPE-FILE
    public String getStorageTypeFile()
    {
        sharedPreferences = _context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(STORAGE_TYPE_FILE, null);
    }
    public void setStorageTypeFile(String storage)
    {
        sharedPreferences = _context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(STORAGE_TYPE_FILE, storage);
        editor.commit();
    }

    public String getReceiverID()
    {
        sharedPreferences = _context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(POST_SENDER_ID, null);
    }
    public void setReceiverID(String storage)
    {
        sharedPreferences = _context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(POST_SENDER_ID, storage);
        editor.commit();
    }

    public String getPostID()
    {
        sharedPreferences = _context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(POST_ID, null);
    }
    public void setPostID(String storage)
    {
        sharedPreferences = _context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(POST_ID, storage);
        editor.commit();
    }

    public String getCurrentGroup()
    {
        sharedPreferences = _context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(CURRENT_GROUP, null);
    }
    public void setCurrentGroup(String storage)
    {
        sharedPreferences = _context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(CURRENT_GROUP, storage);
        editor.commit();
    }

    public String getChatSenderId()
    {
        sharedPreferences = _context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(CHAT_SENDER_ID, null);
    }
    public void setChatSenderId(String storage)
    {
        sharedPreferences = _context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(CHAT_SENDER_ID, storage);
        editor.commit();
    }
}
