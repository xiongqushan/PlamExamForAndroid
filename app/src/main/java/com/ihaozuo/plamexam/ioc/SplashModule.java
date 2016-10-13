package com.ihaozuo.plamexam.ioc;

import com.ihaozuo.plamexam.contract.SplashContract;
import com.ihaozuo.plamexam.view.splash.SplashFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by zhangzhongyao on 2016/10/12.
 */
@Module
public class SplashModule {

    int currVersion;

    public SplashModule() {
    }

    public SplashModule(int currVersion) {
        this.currVersion = currVersion;
    }


    @Provides
    @ScopeType.ActivityScope
    SplashContract.ISplashView provideSplashView() {
        return SplashFragment.newInstance();
    }


    @Provides
    @ScopeType.ActivityScope
    int provideCurrVersion() {
        return currVersion;
    }
}
