package com.ihaozuo.plamexam.ioc;

import com.ihaozuo.plamexam.view.mine.settings.SysSetActivity;

import dagger.Component;

/**
 * Created by zhangzhongyao on 2016/10/25.
 */
@ScopeType.ActivityScope
@Component(modules = SysSetModule.class, dependencies = AppComponent.class)
public interface SysSetComponent {
    void inject(SysSetActivity activity);
}
