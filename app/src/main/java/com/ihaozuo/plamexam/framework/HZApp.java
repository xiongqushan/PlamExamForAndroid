package com.ihaozuo.plamexam.framework;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.frogermcs.dagger2metrics.Dagger2Metrics;
import com.ihaozuo.plamexam.BuildConfig;
import com.ihaozuo.plamexam.ioc.AppComponent;
import com.ihaozuo.plamexam.ioc.AppModule;
import com.ihaozuo.plamexam.ioc.DaggerAppComponent;
import com.ihaozuo.plamexam.manager.PreferenceManager;
import com.ihaozuo.plamexam.util.ImageLoadUtils;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.umeng.analytics.MobclickAgent;

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
        JPushInterface.setDebugMode(BuildConfig.DEBUG);    // 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);
        // JPushInterface.setLatestNotificationNumber(this, 3);//限制保留的通知条数。默认为保留最近 5 条通知。
        PreferenceManager.init(this);
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);

        Fresco.initialize(this, ImageLoadUtils.getInstance(this)
                .CustomConfig(this));

        //讯飞语音转换
//        SpeechUtility.createUtility(this, "appid=" + getString(R.string.app_id));
        // 以下语句用于设置日志开关（默认开启），设置成false时关闭语音云SDK日志打印
//        Setting.setShowLog(BuildConfig.DEBUG);

        //leakcanary
        mRefWatcher = LeakCanary.install(this);

        //dagger2注入检查工具
        if (BuildConfig.DEBUG) {
            Dagger2Metrics.enableCapturing(this);
        }

        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }


}
