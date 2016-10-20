package com.ihaozuo.plamexam.view.report;

import android.os.Bundle;

import com.ihaozuo.plamexam.R;
import com.ihaozuo.plamexam.contract.ReportContract;
import com.ihaozuo.plamexam.ioc.DaggerReportGetComponent;
import com.ihaozuo.plamexam.ioc.ReportModule;
import com.ihaozuo.plamexam.presenter.ReportGetPresenter;
import com.ihaozuo.plamexam.util.ActivityUtils;
import com.ihaozuo.plamexam.view.base.BaseActivity;

import javax.inject.Inject;

public class ReportGetActivity extends BaseActivity {
    @Inject
    ReportGetPresenter mPresenter;
    @Inject
    ReportContract.IReportGetView mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_act);
        DaggerReportGetComponent.builder().appComponent(getAppComponent()).reportModule(new ReportModule()).build().inject(this);
        ReportGetFragment fragment = (ReportGetFragment) getSupportFragmentManager().findFragmentById(R.id.frameContent);
        if (fragment == null) {
            fragment = (ReportGetFragment) mView;
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.frameContent);
        }
    }
}
