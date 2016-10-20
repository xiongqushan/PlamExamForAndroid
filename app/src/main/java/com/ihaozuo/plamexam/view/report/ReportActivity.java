package com.ihaozuo.plamexam.view.report;

import android.os.Bundle;

import com.ihaozuo.plamexam.R;
import com.ihaozuo.plamexam.contract.ReportContract;
import com.ihaozuo.plamexam.ioc.DaggerReportDetailComponent;
import com.ihaozuo.plamexam.ioc.ReportModule;
import com.ihaozuo.plamexam.presenter.ReportDetailPresenter;
import com.ihaozuo.plamexam.util.ActivityUtils;
import com.ihaozuo.plamexam.view.base.BaseActivity;

import javax.inject.Inject;

public class ReportActivity extends BaseActivity {
    public static final String INTENTKEY_WORKNO = "INTENTKEY_WORKNO_REPORTDETAIL";
    public static final String INTENTKEY_CHECKUNITCODE = "INTENTKEY_CHECKUNITCODE_REPORTDETAIL";


    @Inject
    ReportDetailPresenter mPresenter;
    @Inject
    ReportContract.IReportDetailView mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_act);
        String workNo = getIntent().getStringExtra(ReportActivity.INTENTKEY_WORKNO);
        String checkUnitCode = getIntent().getStringExtra(ReportActivity.INTENTKEY_CHECKUNITCODE);
        DaggerReportDetailComponent.builder().appComponent(getAppComponent())
                .reportModule(new ReportModule(workNo, checkUnitCode))
                .build().inject(this);
        ReportFragment fragment = (ReportFragment) getSupportFragmentManager().findFragmentById(R.id.frameContent);
        if (fragment == null) {
            fragment = (ReportFragment) mView;
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.frameContent);
        }
    }
}
