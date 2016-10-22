package com.ihaozuo.plamexam.manager;

import android.content.SharedPreferences;

import com.ihaozuo.plamexam.bean.ReportItemBean;
import com.ihaozuo.plamexam.framework.HZApp;
import com.ihaozuo.plamexam.util.StringUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 * Created by hzguest3 on 2016/10/11.
 */
public class ReportManager {
    private static final String SP_NAME = "REPORT_LIST";
    private static final String KEY_REPORT_lIST = "SHARED_KEY_REPORT_lIST";
    private static final String KEY_REPORT_FIRST_REQUEST = "SHARED_KEY_REPORT_LIST_FIRST_REQUEST";
    private static ReportManager _instance;
    private SharedPreferences sharedPreferences;
    private List<ReportItemBean> _currentEntity;

    private ReportManager() {
        if (sharedPreferences == null) {
            sharedPreferences = HZApp.shareApplication().getSharedPreferences(SP_NAME, HZApp.shareApplication().MODE_PRIVATE);
        }
    }

    public static ReportManager getInstance() {
        if (_instance == null) {
            _instance = new ReportManager();
        }
        return _instance;
    }

    public void setReportList(List<ReportItemBean> data) {
        try {
            _currentEntity = data;
            // 保存对象
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(bos);
            //将对象序列化写入byte缓存
            os.writeObject(data);
            String bytesToHexString = StringUtil.bytesToHexString(bos.toByteArray());
            sharedPreferences.edit().putString(KEY_REPORT_lIST, bytesToHexString).commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<ReportItemBean> getReportList() {
        if (_currentEntity == null) {
            try {
                String localData = sharedPreferences.getString(KEY_REPORT_lIST, "");
                if (!StringUtil.isEmpty(localData)) {
                    byte[] stringToBytes = StringUtil.StringToBytes(localData);
                    ByteArrayInputStream bis = new ByteArrayInputStream(stringToBytes);
                    ObjectInputStream is = new ObjectInputStream(bis);
                    //返回反序列化得到的对象
                    Object localObject = is.readObject();
                    List<ReportItemBean> localInfo = (List<ReportItemBean>) localObject;
                    _currentEntity = localInfo;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return _currentEntity;
    }

    public void setFirstRequest(boolean b) {
        sharedPreferences.edit().putBoolean(KEY_REPORT_FIRST_REQUEST, b).commit();
    }

    public boolean getFirstRequest() {
        return sharedPreferences.getBoolean(KEY_REPORT_FIRST_REQUEST, true);
    }


    public void clear() {
        sharedPreferences.edit().remove(KEY_REPORT_lIST).commit();
        sharedPreferences.edit().remove(KEY_REPORT_FIRST_REQUEST).commit();
        _instance = null;
    }
}
