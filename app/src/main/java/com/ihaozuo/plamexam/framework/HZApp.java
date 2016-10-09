package com.ihaozuo.plamexam.framework;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.decoder.ProgressiveJpegConfig;
import com.facebook.imagepipeline.image.ImmutableQualityInfo;
import com.facebook.imagepipeline.image.QualityInfo;
import com.ihaozuo.plamexam.BuildConfig;
import com.ihaozuo.plamexam.ioc.AppComponent;
import com.ihaozuo.plamexam.ioc.AppModule;
import com.ihaozuo.plamexam.ioc.DaggerAppComponent;
import com.ihaozuo.plamexam.manager.PreferenceManager;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.umeng.analytics.MobclickAgent;

import cn.jpush.android.api.JPushInterface;


/**
 * Created by zhangzhongyao on 16/10/09.
 */
public class HZApp extends Application {
    private static HZApp applictaion;
    private RefWatcher mRefWatcher;
    private AppComponent mAppComponent;

    public static HZApp shareApplication() {
        return applictaion;
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
        applictaion = this;
        JPushInterface.setDebugMode(BuildConfig.DEBUG);    // 设置开启日志,发布时请关闭日志
        JPushInterface.init(applictaion);
        // JPushInterface.setLatestNotificationNumber(this, 3);//限制保留的通知条数。默认为保留最近 5 条通知。
        PreferenceManager.init(applictaion);
        MobclickAgent.setScenarioType(applictaion, MobclickAgent.EScenarioType.E_UM_NORMAL);

        //FRESCO 配置渐进式加载JPEG图片
        ProgressiveJpegConfig pjpegConfig = new ProgressiveJpegConfig() {
            @Override
            public int getNextScanNumberToDecode(int scanNumber) {
                return scanNumber + 2;
            }

            public QualityInfo getQualityInfo(int scanNumber) {
                boolean isGoodEnough = (scanNumber >= 5);
                return ImmutableQualityInfo.of(scanNumber, isGoodEnough, false);
            }
        };
        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(applictaion)
                .setProgressiveJpegConfig(pjpegConfig)
                .setDownsampleEnabled(true) //图片代替resizeoption 向下采样  支持PNG和WebP
                .build();
        Fresco.initialize(applictaion, config);


        //讯飞语音转换
//        SpeechUtility.createUtility(this, "appid=" + getString(R.string.app_id));
        // 以下语句用于设置日志开关（默认开启），设置成false时关闭语音云SDK日志打印
//        Setting.setShowLog(BuildConfig.DEBUG);


        //leakcanary
        mRefWatcher = LeakCanary.install(this);

        //dagger2注入检查工具
//        if (BuildConfig.DEBUG){
//            Dagger2Metrics.enableCapturing(this);
//        }

        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }


}
