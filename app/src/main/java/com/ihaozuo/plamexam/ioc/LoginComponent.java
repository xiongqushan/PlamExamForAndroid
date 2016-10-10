package com.ihaozuo.plamexam.ioc;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by hzguest3 on 2016/10/10.
 */

@Singleton
@Component(modules = {LoginModule.class})
public class LoginComponent {
}
