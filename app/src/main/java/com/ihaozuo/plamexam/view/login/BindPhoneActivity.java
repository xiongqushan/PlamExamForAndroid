package com.ihaozuo.plamexam.view.login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ihaozuo.plamexam.R;
import com.ihaozuo.plamexam.util.ActivityUtils;
import com.ihaozuo.plamexam.view.splash.SplashFragment;

public class BindPhoneActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_act);

        BindPhoneFragment fragment = (BindPhoneFragment) getSupportFragmentManager().findFragmentById(R.id.frameContent);
        if (fragment == null) {
            fragment = new BindPhoneFragment();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.frameContent);
        }
    }
}
