package com.ihaozuo.plamexam.view.splash;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.WindowManager;

import com.ihaozuo.plamexam.R;
import com.ihaozuo.plamexam.contract.SplashContract;
import com.ihaozuo.plamexam.ioc.DaggerSplashComponent;
import com.ihaozuo.plamexam.ioc.SplashModule;
import com.ihaozuo.plamexam.presenter.SplashPresenter;
import com.ihaozuo.plamexam.util.ActivityUtils;
import com.ihaozuo.plamexam.view.base.BaseActivity;

import javax.inject.Inject;

public class SplashActivity extends BaseActivity {
    @Inject
    SplashPresenter mPresenter;
    @Inject
    SplashContract.ISplashView mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.content_act);
        DaggerSplashComponent.builder()
                .appComponent(getAppComponent())
                .splashModule(new SplashModule())
                .build().inject(this);

        SplashFragment fragment = (SplashFragment) getSupportFragmentManager().findFragmentById(R.id.frameContent);
        if (fragment == null) {
            fragment = (SplashFragment) mView;
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.frameContent);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }


}
