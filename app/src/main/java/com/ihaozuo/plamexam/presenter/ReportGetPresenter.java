package com.ihaozuo.plamexam.presenter;

import android.support.annotation.NonNull;

import com.ihaozuo.plamexam.bean.ReportItemBean;
import com.ihaozuo.plamexam.bean.RestResult;
import com.ihaozuo.plamexam.contract.ReportContract;
import com.ihaozuo.plamexam.listener.OnHandlerResultListener;
import com.ihaozuo.plamexam.model.IBaseModel;
import com.ihaozuo.plamexam.model.ReportModel;
import com.ihaozuo.plamexam.view.base.IBaseView;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by zhangzhongyao on 2016/10/18
 */

public class ReportGetPresenter extends AbstractPresenter implements ReportContract.IReportGetPresenter {

    private ReportContract.IReportGetView mView;
    private ReportModel mReportModel;

    @Inject
    public ReportGetPresenter(@NonNull ReportContract.IReportGetView reportGetView, @NonNull ReportModel reportModel) {
        mView = reportGetView;
        mReportModel = reportModel;
        mView.setPresenter(this);
    }


    @Override
    public IBaseView getBaseView() {
        return mView;
    }

    @Override
    public IBaseModel[] getBaseModelList() {
        return new IBaseModel[]{mReportModel};
    }

    @Override
    public void start() {
    }

    @Override
    public void getReport(String mobile, String realName) {
        mView.showDialog();
        mReportModel.addReportList(mobile, realName, new OnHandlerResultListener<RestResult<List<ReportItemBean>>>() {
            @Override
            public void handlerResultSuccess(RestResult<List<ReportItemBean>> resultData) {
                mView.showReportList(resultData.Data);
            }

            @Override
            public void handlerResultError(String message) {
                mView.hideDialog(message);
            }

        });
    }
}