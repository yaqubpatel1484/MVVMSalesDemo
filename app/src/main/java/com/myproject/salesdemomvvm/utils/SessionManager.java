package com.myproject.salesdemomvvm.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceActivity;

import com.myproject.salesdemomvvm.activities.BaseActivity;
import com.myproject.salesdemomvvm.application.MyApplication;
import com.myproject.salesdemomvvm.login.LoginActivity;

import java.util.HashMap;

public class SessionManager extends PreferenceActivity {

    private SharedPreferences mSessionExecutive;
    private SharedPreferences.Editor mEditor;
    private Context mContext;

    private static final String PREF_NAME = "ExecutivePref";
    private static final String IS_LOGIN = "IsLoggedIn";

    public final String KEY_TID = "tid";
    public final String KEY_NAME = "name";
    public final String KEY_CONTACT = "contact";
    public final String KEY_ADDRESS = "address";
    public final String KEY_DESIGNATION = "designation";
    public final String KEY_HIRE_DATE = "hire_date";
    public final String KEY_PHOTO = "photo";
    public final String KEY_USERNAME = "username";
    public final String KEY_PASSWORD = "password";
    public final String KEY_EMAIL = "email";
    public final String KEY_TOKEN = "token";
    public final String KEY_REGION = "region";

    public SessionManager() {
        int PRIVATE_MODE = 0;
        this.mContext = MyApplication.getInstance();
        mSessionExecutive = mContext.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        mEditor = mSessionExecutive.edit();
        mEditor.apply();
    }

    public void createExecutiveSession(String tid, String name, String contact, String address, String designation, String hiredate, String photo, String username, String password, String email, String token, String region) {


        mEditor.putBoolean(IS_LOGIN, true);
        mEditor.putString(KEY_TID, tid);
        mEditor.putString(KEY_NAME, name);
        mEditor.putString(KEY_CONTACT, contact);
        mEditor.putString(KEY_ADDRESS, address);
        mEditor.putString(KEY_DESIGNATION, designation);
        mEditor.putString(KEY_HIRE_DATE, hiredate);
        mEditor.putString(KEY_PHOTO, photo);
        mEditor.putString(KEY_USERNAME, username);
        mEditor.putString(KEY_PASSWORD, password);
        mEditor.putString(KEY_EMAIL, email);
        mEditor.putString(KEY_TOKEN, token);
        mEditor.putString(KEY_REGION, region);

        mEditor.apply();
    }

    public boolean isExecutiveLogin() {
        return mSessionExecutive.getBoolean(IS_LOGIN, false);
    }

    public void checkExecutiveLogin() {
        if(isExecutiveLogin()){
            Intent intent = new Intent(mContext, BaseActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mContext.startActivity(intent);
        }
    }


    public HashMap<String, String> getExecutiveDetails(){

        HashMap<String , String> user = new HashMap<>();

        user.put(KEY_TID, mSessionExecutive.getString(KEY_TID,null));
        user.put(KEY_NAME, mSessionExecutive.getString(KEY_NAME,null));
        user.put(KEY_CONTACT, mSessionExecutive.getString(KEY_CONTACT, null));
        user.put(KEY_USERNAME, mSessionExecutive.getString(KEY_USERNAME, null));
        user.put(KEY_PASSWORD, mSessionExecutive.getString(KEY_PASSWORD, null));
        user.put(KEY_DESIGNATION, mSessionExecutive.getString(KEY_DESIGNATION, null));
        user.put(KEY_HIRE_DATE, mSessionExecutive.getString(KEY_HIRE_DATE, null));
        user.put(KEY_EMAIL, mSessionExecutive.getString(KEY_EMAIL, null));
        user.put(KEY_ADDRESS, mSessionExecutive.getString(KEY_ADDRESS, null));
        user.put(KEY_REGION, mSessionExecutive.getString(KEY_REGION, null));
        user.put(KEY_PHOTO, mSessionExecutive.getString(KEY_PHOTO, null));

        return user;
    }

    public void logoutExecutive(){
        mEditor.clear();
        mEditor.commit();
        Intent intent = new Intent(mContext, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);
    }

}
