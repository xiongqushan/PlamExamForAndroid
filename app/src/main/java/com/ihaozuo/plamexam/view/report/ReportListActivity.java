package com.ihaozuo.plamexam.view.report;

import android.os.Bundle;

import com.ihaozuo.plamexam.R;
import com.ihaozuo.plamexam.contract.ReportContract;
import com.ihaozuo.plamexam.ioc.DaggerReportListComponent;
import com.ihaozuo.plamexam.ioc.ReportModule;
import com.ihaozuo.plamexam.presenter.ReportListPresenter;
import com.ihaozuo.plamexam.util.ActivityUtils;
import com.ihaozuo.plamexam.view.base.BaseActivity;

import javax.inject.Inject;

public class ReportListActivity extends BaseActivity {

    @Inject
    ReportListPresenter mPresenter;
    @Inject
    ReportContract.IReportListView mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_act);
        DaggerReportListComponent.builder().appComponent(getAppComponent()).reportModule(new ReportModule()).build().inject(this);
        ReportListFragment fragment = (ReportListFragment) getSupportFragmentManager().findFragmentById(R.id.frameContent);
        if (fragment == null) {
            fragment = (ReportListFragment) mView;
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.frameContent);
        }
    }
}
