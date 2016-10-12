package com.ihaozuo.plamexam.ioc;

import com.ihaozuo.plamexam.view.login.LoginActivity;

import dagger.Component;

/**
 * Created by hzguest3 on 2016/10/10.
 */

@ScopeType.LoginScope
@Component(modules = {LoginModule.class},dependencies = {AppComponent.class})
public interface LoginComponent {
    void inject(LoginActivity loginActivity);
}
