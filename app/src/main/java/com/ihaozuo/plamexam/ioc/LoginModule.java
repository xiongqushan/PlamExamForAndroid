package com.ihaozuo.plamexam.ioc;

import com.ihaozuo.plamexam.contract.LoginContract;
import com.ihaozuo.plamexam.view.login.LoginFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by hzguest3 on 2016/10/10.
 */

@Module
public class LoginModule {

    public LoginModule(){
    }

    @Provides
    @ScopeType.LoginScope
    public LoginContract.ILoginView provideLoginView(){
        return LoginFragment.newInstance();
    }


}
