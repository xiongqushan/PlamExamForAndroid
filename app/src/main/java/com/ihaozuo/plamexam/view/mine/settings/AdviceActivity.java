package com.ihaozuo.plamexam.view.mine.settings;

import android.os.Bundle;

import com.ihaozuo.plamexam.R;
import com.ihaozuo.plamexam.contract.AdviceContract;
import com.ihaozuo.plamexam.framework.HZApp;
import com.ihaozuo.plamexam.ioc.AdviceModule;
import com.ihaozuo.plamexam.ioc.DaggerAdviceComponent;
import com.ihaozuo.plamexam.presenter.AdvicePresenter;
import com.ihaozuo.plamexam.util.ActivityUtils;
import com.ihaozuo.plamexam.view.base.BaseActivity;

import javax.inject.Inject;

public class AdviceActivity extends BaseActivity {

    @Inject
    AdvicePresenter mAdvicePresenter;
    @Inject
    AdviceContract.IAdviceView mAdviceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_act);

        DaggerAdviceComponent.builder()
                .appComponent(HZApp.shareApplication().getAppComponent())
                .adviceModule(new AdviceModule())
                .build()
                .inject(this);

        AdviceFragment fragment = (AdviceFragment) getSupportFragmentManager().findFragmentById(R.id.frameContent);
        if (fragment == null) {
            fragment = (AdviceFragment) mAdviceView;
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.frameContent);
        }
    }
}
