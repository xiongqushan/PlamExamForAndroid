package com.ihaozuo.plamexam.ioc;

import com.ihaozuo.plamexam.contract.HomeContract;
import com.ihaozuo.plamexam.view.home.HomeFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by zhangzhongyao on 2016/10/11.
 */
@Module
public class HomeModule {
    private HomeContract.IHomeView view;

    public HomeModule(HomeFragment fragment) {
        view = fragment;
    }

    @ScopeType.ActivityScope
    @Provides
    HomeContract.IHomeView provideHomeView() {
        return view;
    }
}
