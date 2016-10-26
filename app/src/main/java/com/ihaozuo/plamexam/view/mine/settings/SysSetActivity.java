package com.ihaozuo.plamexam.view.mine.settings;

import android.os.Bundle;

import com.ihaozuo.plamexam.R;
import com.ihaozuo.plamexam.contract.SysSetContract;
import com.ihaozuo.plamexam.ioc.DaggerSysSetComponent;
import com.ihaozuo.plamexam.ioc.SysSetModule;
import com.ihaozuo.plamexam.presenter.SysSetPresenter;
import com.ihaozuo.plamexam.util.ActivityUtils;
import com.ihaozuo.plamexam.view.base.BaseActivity;

import javax.inject.Inject;

public class SysSetActivity extends BaseActivity {
    @Inject
    SysSetPresenter mPresenter;

    @Inject
    SysSetContract.ISysSetView mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_act);
        DaggerSysSetComponent.builder()
                .appComponent(getAppComponent())
                .sysSetModule(new SysSetModule())
                .build().inject(this);
        SysSetFragment fragment = (SysSetFragment) getSupportFragmentManager().findFragmentById(R.id.frameContent);
        if (fragment == null) {
            fragment = (SysSetFragment) mView;
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.frameContent);
        }
    }
}
