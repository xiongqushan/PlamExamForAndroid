package com.ihaozuo.plamexam.ioc;

import com.ihaozuo.plamexam.framework.HZApp;
import com.ihaozuo.plamexam.service.IUserService;
import com.squareup.okhttp.OkHttpClient;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by xiongwei1 on 2016/8/5.
 */

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    HZApp getApplication();

    IUserService getIUserService();

    OkHttpClient getOkHttpClient();
}
