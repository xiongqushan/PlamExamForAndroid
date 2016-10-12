package com.ihaozuo.plamexam.manager;

import android.app.Activity;
import android.content.SharedPreferences;

import com.ihaozuo.plamexam.framework.HZApp;

/**
 * Created by hzguest3 on 2016/10/11.
 */
public class UserManager {
    private static final String SP_NAME = "USER";
    private static final String USER_INFO_KEY = "USER_INFO_KEY";
    private static UserManager _instance;
    private SharedPreferences sharedPreferences;
    private UserManager() {
        if (null == sharedPreferences) {
            sharedPreferences = HZApp.shareApplication().getSharedPreferences(SP_NAME, Activity.MODE_PRIVATE);
        }
    }

    public static UserManager getInstance() {
        if (_instance == null) {
            _instance = new UserManager();
        }
        return _instance;
    }


    public boolean exist(){
        return false;
//        return getDoctorInfo()!=null;
    }

    public void clear(){
        sharedPreferences.edit().remove(USER_INFO_KEY).commit();
        _instance = null;
    }
}
