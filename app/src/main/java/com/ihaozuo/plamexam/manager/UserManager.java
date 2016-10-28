package com.ihaozuo.plamexam.manager;

import android.app.Activity;
import android.content.SharedPreferences;

import com.ihaozuo.plamexam.bean.UserBean;
import com.ihaozuo.plamexam.framework.HZApp;
import com.ihaozuo.plamexam.util.StringUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by hzguest3 on 2016/10/11.
 */
public class UserManager {
    private static final String SP_NAME = "USER";
    private static final String USER_INFO_KEY = "USER_INFO_KEY";
    private String USER_DOCTOR_ID;
    private static UserManager _instance;
    private SharedPreferences sharedPreferences;
    private UserBean _currentUserEntity;

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

    public void setUserInfo(UserBean userEntity) {
        try {
            _currentUserEntity = userEntity;
            // 保存对象
            SharedPreferences.Editor sharedata = sharedPreferences.edit();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(bos);
            //将对象序列化写入byte缓存
            os.writeObject(userEntity);
            String bytesToHexString = StringUtil.bytesToHexString(bos.toByteArray());
            sharedata.putString(USER_INFO_KEY, bytesToHexString);
            sharedata.apply();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public UserBean getUserInfo() {
        if (_currentUserEntity == null) {
            try {
                if (sharedPreferences.contains(USER_INFO_KEY)) {
                    String localData = sharedPreferences.getString(USER_INFO_KEY, "");
                    if (!StringUtil.isEmpty(localData)) {
                        byte[] stringToBytes = StringUtil.StringToBytes(localData);
                        ByteArrayInputStream bis = new ByteArrayInputStream(stringToBytes);
                        ObjectInputStream is = new ObjectInputStream(bis);
                        //返回反序列化得到的对象
                        Object localObject = is.readObject();
                        UserBean localInfo = (UserBean) localObject;
                        _currentUserEntity = localInfo;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return _currentUserEntity;
    }

    public void updateDepartCode(String newCode) {
        UserBean userInfo = getUserInfo();
        userInfo.DepartCode = newCode;
        setUserInfo(userInfo);
    }

//    public void updateRealName(String newName) {
//        UserBean userInfo = getUserInfo();
//        userInfo.RealName = newName;
//        setUserInfo(userInfo);
//    }

    public void setDoctorID(String doctorID) {
        USER_DOCTOR_ID = doctorID;
    }

    public String getDoctorID() {
        return USER_DOCTOR_ID;
    }

    public boolean exist() {
//        return true;
        return getUserInfo() != null;
    }

    public void clear() {
        sharedPreferences.edit().remove(USER_INFO_KEY).commit();
        _instance = null;
    }
}
