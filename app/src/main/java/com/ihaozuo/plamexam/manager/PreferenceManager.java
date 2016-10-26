package com.ihaozuo.plamexam.manager;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {
    /**
     * 保存Preference的name
     */
    public static final String PREFERENCE_NAME = "HZ_PLAMEXAM_SP";
    private static SharedPreferences mSharedPreferences;
    private static PreferenceManager mPreferencemManager;
    private static SharedPreferences.Editor editor;

    private static String SHARED_KEY_LOGINPHONE = "SHARED_KEY_LOGIN_PHONE";
    private static String SHARED_KEY_DEPARTCODE = "SHARED_KEY_DEPARTCODE";
    private static String SHARED_KEY_JPUSH = "SHARED_KEY_JPUSH";
    private static String SHARED_KEY_INITNEWS = "SHARED_KEY_INITNEWS";

    private PreferenceManager(Context context) {
        mSharedPreferences = context.getSharedPreferences(PREFERENCE_NAME,
                Context.MODE_PRIVATE);
        editor = mSharedPreferences.edit();
    }

    public static synchronized void init(Context context) {
        if (mPreferencemManager == null) {
            mPreferencemManager = new PreferenceManager(context);
        }
    }

    /**
     * 单例模式，获取instance实例
     */
    public synchronized static PreferenceManager getInstance() {
        if (mPreferencemManager == null) {
            throw new RuntimeException("please init PreferenceManager first!");
        }
        return mPreferencemManager;
    }


    public boolean readJpush() {
        return mSharedPreferences.getBoolean(SHARED_KEY_JPUSH, true);
    }

    public void writeJpush(boolean state) {
        editor.putBoolean(SHARED_KEY_JPUSH, state);
        editor.apply();
    }


    public String readLoginPhone() {
        return mSharedPreferences.getString(SHARED_KEY_LOGINPHONE, "");
    }

    public void writeLoginPhone(String phone) {
        editor.putString(SHARED_KEY_LOGINPHONE, phone);
        editor.apply();
    }

    public boolean readNewsState() {
        return mSharedPreferences.getBoolean(SHARED_KEY_INITNEWS, true);
    }

    public void writeNewsState(boolean state) {
        editor.putBoolean(SHARED_KEY_INITNEWS, state);
        editor.apply();
    }


}
