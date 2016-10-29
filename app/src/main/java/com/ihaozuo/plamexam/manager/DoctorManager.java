package com.ihaozuo.plamexam.manager;

import android.app.Activity;
import android.content.SharedPreferences;

import com.ihaozuo.plamexam.bean.DoctorInfoBean;
import com.ihaozuo.plamexam.framework.HZApp;

import java.util.List;

/**
 * Created by hzguest3 on 2016/10/18.
 */
public class DoctorManager {

    private static final String SP_NAME = "DOCTOR";
    private static final String USER_INFO_KEY = "DOCTOR_INFO_KEY";
    private static DoctorManager _instance;
    private SharedPreferences sharedPreferences;
    private List<DoctorInfoBean> doctorInfoList;

    private DoctorManager() {
        if (null == sharedPreferences) {
            sharedPreferences = HZApp.shareApplication().getSharedPreferences(SP_NAME, Activity.MODE_PRIVATE);
        }
    }

    public static DoctorManager getInstance() {
        if (_instance == null) {
            _instance = new DoctorManager();
        }
        return _instance;
    }

    public void setDoctorList(List<DoctorInfoBean> doctorList) {
        doctorInfoList = doctorList;
    }

    public List<DoctorInfoBean> getDoctorList() {
        return doctorInfoList;
    }

    public void clear() {
        sharedPreferences.edit().remove(USER_INFO_KEY).commit();
        _instance = null;
    }
}
