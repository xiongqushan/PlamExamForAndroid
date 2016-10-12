package com.ihaozuo.plamexam.ioc;

import com.ihaozuo.plamexam.view.home.HomeFragment;

import dagger.Component;

/**
 * Created by xiongwei1 on 2016/8/5.
 */

@ScopeType.ActivityScope
@Component(modules = {HomeModule.class}, dependencies = {AppComponent.class})
public interface HomeComponent {
    void inject(HomeFragment fragment);
}
