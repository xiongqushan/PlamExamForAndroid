package com.ihaozuo.plamexam.view.login;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.WindowManager;

import com.ihaozuo.plamexam.R;
import com.ihaozuo.plamexam.contract.LoginContract;
import com.ihaozuo.plamexam.framework.HZApp;
import com.ihaozuo.plamexam.ioc.DaggerLoginComponent;
import com.ihaozuo.plamexam.ioc.LoginModule;
import com.ihaozuo.plamexam.presenter.LoginPresenter;
import com.ihaozuo.plamexam.util.ActivityUtils;
import com.ihaozuo.plamexam.view.base.BaseActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;


public class LoginActivity extends BaseActivity {

    @Inject
    LoginPresenter mLoginPresenter;
    @Inject
    LoginContract.ILoginView mLoginView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.content_act);
        ButterKnife.bind(this);

        DaggerLoginComponent.builder()
                .loginModule(new LoginModule())
                .appComponent(HZApp.shareApplication().getAppComponent())
                .build()
                .inject(this);

        FragmentManager fragmentManager = getSupportFragmentManager();
        LoginFragment fragment = (LoginFragment) fragmentManager.findFragmentById(R.id.frameContent);
        if (fragment == null) {
            fragment = (LoginFragment) mLoginView;
            ActivityUtils.addFragmentToActivity(fragmentManager, fragment, R.id.frameContent);
        }



    }

}

