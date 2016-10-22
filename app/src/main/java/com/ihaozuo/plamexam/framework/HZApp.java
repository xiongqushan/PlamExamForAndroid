package com.ihaozuo.plamexam.framework;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.frogermcs.dagger2metrics.Dagger2Metrics;
import com.iflytek.cloud.Setting;
import com.iflytek.cloud.SpeechUtility;
import com.ihaozuo.plamexam.BuildConfig;
import com.ihaozuo.plamexam.R;
import com.ihaozuo.plamexam.ioc.AppComponent;
import com.ihaozuo.plamexam.ioc.AppModule;
import com.ihaozuo.plamexam.ioc.DaggerAppComponent;
import com.ihaozuo.plamexam.manager.PreferenceManager;
import com.ihaozuo.plamexam.util.ImageLoadUtils;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
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
    private RefWatcher mRefWatcher;
    private AppComponent mAppComponent;

    public static HZApp shareApplication() {
        return application;
    }

    public RefWatcher getRefWatcher() {
        return mRefWatcher;
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;

        PreferenceManager.init(this);
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);

        //fresco
        Fresco.initialize(this, ImageLoadUtils.getInstance(this)
                .CustomConfig(this));

        //极光
        JPushInterface.setDebugMode(BuildConfig.DEBUG);    // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);
        // JPushInterface.setLatestNotificationNumber(this, 3);//限制保留的通知条数。默认为保留最近 5 条通知。

        //讯飞
        SpeechUtility.createUtility(this, "appid=" + getString(R.string.xunfei_app_id));
        Setting.setShowLog(BuildConfig.DEBUG);

        //leakcanary
        mRefWatcher = LeakCanary.install(this);

        //dagger2注入检查工具
        if (BuildConfig.DEBUG) {
            Dagger2Metrics.enableCapturing(this);
        }


        UMShareAPI.get(this);

        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
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


    {
        //微信 wx12342956d1cab4f9,a5ae111de7d9ea137e88a5e02c07c94d
        PlatformConfig.setWeixin("wxdc1e388c3822c80b", "3baf1193c85774b3fd9d18447d76cab0");
        //豆瓣RENREN平台目前只能在服务器端配置
        //新浪微博
        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad");
        //易信
        PlatformConfig.setYixin("yxc0614e80c9304c11b0391514d09f13bf");
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
        PlatformConfig.setTwitter("3aIN7fuF685MuZ7jtXkQxalyi", "MK6FEYG63eWcpDFgRYw4w9puJhzDl0tyuqWjZ3M7XJuuG7mMbO");
        PlatformConfig.setAlipay("2015111700822536");
        PlatformConfig.setLaiwang("laiwangd497e70d4", "d497e70d4c3e4efeab1381476bac4c5e");
        PlatformConfig.setPinterest("1439206");

        Config.REDIRECT_URL = "http://sns.whalecloud.com/sina2/callback";

    }

}
