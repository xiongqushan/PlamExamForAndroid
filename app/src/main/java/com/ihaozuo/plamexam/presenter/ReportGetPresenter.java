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

public class ReportGetPresenter extends AbstractPresenter implements ReportContract.ReportGetPresenter {

    private ReportContract.ReportGetView mView;
    private ReportModel mReportModel;

    @Inject
    public ReportGetPresenter(@NonNull ReportContract.ReportGetView reportGetView, @NonNull ReportModel reportModel) {
        mView = reportGetView;
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