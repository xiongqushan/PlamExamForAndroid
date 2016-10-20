package com.ihaozuo.plamexam.presenter;

import android.support.annotation.NonNull;

import com.ihaozuo.plamexam.bean.ReportDetailBean;
import com.ihaozuo.plamexam.bean.RestResult;
import com.ihaozuo.plamexam.contract.ReportContract;
import com.ihaozuo.plamexam.listener.OnHandlerResultListener;
import com.ihaozuo.plamexam.model.IBaseModel;
import com.ihaozuo.plamexam.model.ReportModel;
import com.ihaozuo.plamexam.view.base.IBaseView;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by zhangzhongyao on 2016/10/18
 */

public class ReportDetailPresenter extends AbstractPresenter implements ReportContract.IReportDetailPresenter {
    private ReportContract.IReportDetailView mView;
    private ReportModel mReportModel;
    private String workNo;
    private String checkUnitCode;

    @Inject
    public ReportDetailPresenter(@NonNull ReportContract.IReportDetailView reportDetailView, @NonNull ReportModel reportModel,
                                 @NonNull @Named("WorkNo") String workNo, @NonNull @Named("CheckUnitCode") String checkUnitCode) {
        mView = reportDetailView;
        mReportModel = reportModel;
        mView.setPresenter(this);
        this.workNo = workNo;
        this.checkUnitCode = checkUnitCode;
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
        getReportDetail(workNo, checkUnitCode);
    }

    @Override
    public void getReportDetail(String workNo, String checkUnitCode) {
        mView.showDialog();
        mReportModel.getReportDetail(workNo, checkUnitCode, new OnHandlerResultListener<RestResult<ReportDetailBean>>() {
            @Override
            public void handlerResultSuccess(RestResult<ReportDetailBean> resultData) {
                mView.updateFragment(resultData.Data);
                mView.toggleRetryLayer(false);
                mView.hideDialog();
            }

            @Override
            public void handlerResultError(String message) {
                mView.toggleRetryLayer(true);
                mView.hideDialog(message);
            }
        });
    }
}