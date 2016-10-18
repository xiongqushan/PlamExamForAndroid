package com.ihaozuo.plamexam.presenter;

import android.support.annotation.NonNull;

import com.ihaozuo.plamexam.contract.ReportContract;
import com.ihaozuo.plamexam.model.IBaseModel;
import com.ihaozuo.plamexam.model.ReportModel;
import com.ihaozuo.plamexam.view.base.IBaseView;

import javax.inject.Inject;

/**
 * Created by zhangzhongyao on 2016/10/18
 */

public class ReportListPresenter extends AbstractPresenter implements ReportContract.ReportListPresenter {

    private ReportContract.ReportListView mView;
    private ReportModel mReportModel;

    @Inject
    public ReportListPresenter(@NonNull ReportContract.ReportListView reportListView, @NonNull ReportModel reportModel) {
        mView = reportListView;
        mReportModel = reportModel;
        mView.setPresenter(this);
    }


    @Override
    public IBaseView getBaseView() {
        return null;
    }

    @Override
    public IBaseModel[] getBaseModelList() {
        return new IBaseModel[]{};
    }

    @Override
    public void start() {

    }
}