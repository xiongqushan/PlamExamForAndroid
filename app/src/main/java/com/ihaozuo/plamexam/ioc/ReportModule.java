package com.ihaozuo.plamexam.ioc;

import com.ihaozuo.plamexam.contract.ReportContract;
import com.ihaozuo.plamexam.view.report.ReportFragment;
import com.ihaozuo.plamexam.view.report.ReportGetFragment;
import com.ihaozuo.plamexam.view.report.ReportListFragment;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by zhangzhongyao on 2016/10/19.
 */
@Module
public class ReportModule {


    private String workNo;
    private String checkUnitCode;

    public ReportModule() {
    }

    public ReportModule(String workNo, String checkUnitCode) {
        this.workNo = workNo;
        this.checkUnitCode = checkUnitCode;
    }

    @ScopeType.ActivityScope
    @Provides
    ReportContract.IReportDetailView provideDetailView() {
        return ReportFragment.newInstance();
    }

    @ScopeType.ActivityScope
    @Provides
    ReportContract.IReportListView provideListView() {
        return ReportListFragment.newInstance();
    }

    @ScopeType.ActivityScope
    @Provides
    ReportContract.IReportGetView provideGetView() {
        return ReportGetFragment.newInstance();
    }

    @ScopeType.ActivityScope
    @Provides
    @Named("WorkNo")
    String provideWorkNo() {
        return workNo;
    }

    @ScopeType.ActivityScope
    @Provides
    @Named("CheckUnitCode")
    String provideCheckUnitCode() {
        return checkUnitCode;
    }
}
