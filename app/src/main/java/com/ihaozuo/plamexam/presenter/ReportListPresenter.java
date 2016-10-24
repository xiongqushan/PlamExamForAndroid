package com.ihaozuo.plamexam.presenter;

import android.support.annotation.NonNull;

import com.ihaozuo.plamexam.bean.ReportItemBean;
import com.ihaozuo.plamexam.bean.RestResult;
import com.ihaozuo.plamexam.contract.ReportContract;
import com.ihaozuo.plamexam.listener.OnHandlerResultListener;
import com.ihaozuo.plamexam.manager.ReportManager;
import com.ihaozuo.plamexam.manager.UserManager;
import com.ihaozuo.plamexam.model.IBaseModel;
import com.ihaozuo.plamexam.model.ReportModel;
import com.ihaozuo.plamexam.view.base.IBaseView;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by zhangzhongyao on 2016/10/18
 */

public class ReportListPresenter extends AbstractPresenter implements ReportContract.IReportListPresenter {

    private ReportContract.IReportListView mView;
    private ReportModel mReportModel;

    @Inject
    public ReportListPresenter(@NonNull ReportContract.IReportListView reportListView, @NonNull ReportModel reportModel) {
        mView = reportListView;
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
        getReportList();
    }


    @Override
    public void getReportList() {
        mView.showDialog();
        String accountId = UserManager.getInstance().getUserInfo().AccountId;
        mReportModel.getReportList(accountId, new OnHandlerResultListener<RestResult<List<ReportItemBean>>>() {
            @Override
            public void handlerResultSuccess(RestResult<List<ReportItemBean>> resultData) {
//                if (resultData.Data != null && resultData.Data.size() > 0) {
                mView.showReportList(resultData.Data);
                mView.hideDialog();
//                } else {
//                    mView.showAddBtn();
//                    mView.hideDialog("暂无报告");
//                }
                mView.toggleRetryLayer(false);
                ReportManager.getInstance().setFirstRequest(false);
                ReportManager.getInstance().setReportList(resultData.Data);
            }

            @Override
            public void handlerResultError(String message) {
                mView.toggleRetryLayer(true);
                mView.hideDialog(message);
            }
        });
    }
}