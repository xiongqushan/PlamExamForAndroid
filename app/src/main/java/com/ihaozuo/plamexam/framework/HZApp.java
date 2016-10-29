package com.ihaozuo.plamexam.framework;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import com.activeandroid.ActiveAndroid;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.iflytek.cloud.Setting;
import com.iflytek.cloud.SpeechUtility;
import com.ihaozuo.plamexam.BuildConfig;
import com.ihaozuo.plamexam.R;
import com.ihaozuo.plamexam.ioc.AppComponent;
import com.ihaozuo.plamexam.ioc.AppModule;
import com.ihaozuo.plamexam.ioc.DaggerAppComponent;
import com.ihaozuo.plamexam.manager.PreferenceManager;
import com.ihaozuo.plamexam.util.ImageLoadUtils;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import cn.jpush.android.api.JPushInterface;


/**
 * Created by zhangzhongyao on 16/10/09.
 */
public class HZApp extends Application {
    private static HZApp application;
//    private RefWatcher mRefWatcher;
    private AppComponent mAppComponent;

    public static HZApp shareApplication() {
        return application;
    }

//    public RefWatcher getRefWatcher() {
//        return mRefWatcher;
//    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ActiveAndroid.dispose();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;

        ActiveAndroid.initialize(this);

        PreferenceManager.init(this);
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);


        //fresco
        Fresco.initialize(this, ImageLoadUtils.getInstance()
                .CustomConfig(this));

        //讯飞
        SpeechUtility.createUtility(this, "appid=" + getString(R.string.xunfei_app_id));
        Setting.setShowLog(BuildConfig.DEBUG);

//        leakcanary
//        mRefWatcher = LeakCanary.install(this);

        //dagger2注入检查工具
//        if (BuildConfig.DEBUG) {
//            Dagger2Metrics.enableCapturing(this);
//        }

        UMShareAPI.get(this);

        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
//        }

        //极光
        JPushInterface.setDebugMode(BuildConfig.DEBUG);    // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);
        // JPushInterface.setLatestNotificationNumber(this, 3);//限制保留的通知条数。默认为保留最近 5 条通知。


        PlatformConfig.setSinaWeibo(getString(R.string.SINA_APP_ID), getString(R.string.SINA_APP_KEY));
        PlatformConfig.setQQZone(getString(R.string.QQ_APP_ID), getString(R.string.QQ_APP_KEY));
        PlatformConfig.setWeixin(getString(R.string.WEIXIN_APP_ID), getString(R.string.WEIXIN_APP_KEY));
        Config.REDIRECT_URL = getString(R.string.SINA_OAUTH_URL);
        

    }


    String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager mActivityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager
                .getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }

}
