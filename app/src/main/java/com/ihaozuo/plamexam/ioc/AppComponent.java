package com.ihaozuo.plamexam.ioc;

import com.ihaozuo.plamexam.framework.HZApp;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by xiongwei1 on 2016/8/5.
 */

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    HZApp getApplication();
}
