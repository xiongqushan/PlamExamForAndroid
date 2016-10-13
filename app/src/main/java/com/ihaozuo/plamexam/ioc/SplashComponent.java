package com.ihaozuo.plamexam.ioc;

import com.ihaozuo.plamexam.view.splash.SplashActivity;
import dagger.Component;

/**
 * Created by zhangzhongyao on 2016/10/12.
 */
@ScopeType.ActivityScope
@Component(modules = SplashModule.class, dependencies = AppComponent.class)
public interface SplashComponent {
    void inject(SplashActivity activity);
}
