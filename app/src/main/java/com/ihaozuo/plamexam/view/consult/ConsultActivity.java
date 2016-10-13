package com.ihaozuo.plamexam.view.consult;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.ihaozuo.plamexam.R;
import com.ihaozuo.plamexam.framework.HZApp;
import com.ihaozuo.plamexam.ioc.ConsultModule;
import com.ihaozuo.plamexam.ioc.DaggerConsultComponent;
import com.ihaozuo.plamexam.util.ActivityUtils;
import com.ihaozuo.plamexam.view.base.BaseActivity;

import javax.inject.Inject;

/**
 * Created by hzguest3 on 2016/10/13.
 */
public class ConsultActivity extends BaseActivity {

    @Inject
    ConsultFragment mConsultView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_act);

        DaggerConsultComponent.builder()
                .appComponent(HZApp.shareApplication()
                .getAppComponent())
                .consultModule(new ConsultModule())
                .build()
                .inject(this);

        FragmentManager fragmentManager = getSupportFragmentManager();
        ConsultFragment fragment = (ConsultFragment) fragmentManager.findFragmentById(R.id.frameContent);
        if (fragment == null) {
            fragment = (ConsultFragment) mConsultView;
            ActivityUtils.addFragmentToActivity(fragmentManager, fragment, R.id.frameContent);
        }

    }
}
