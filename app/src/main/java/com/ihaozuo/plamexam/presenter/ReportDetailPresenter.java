package com.ihaozuo.plamexam.presenter;

import android.support.annotation.NonNull;

import com.ihaozuo.plamexam.bean.ConsultDetailBean;
import com.ihaozuo.plamexam.bean.ReportDetailBean;
import com.ihaozuo.plamexam.bean.RestResult;
import com.ihaozuo.plamexam.common.Constants;
import com.ihaozuo.plamexam.contract.ReportContract;
import com.ihaozuo.plamexam.listener.OnHandlerResultListener;
import com.ihaozuo.plamexam.manager.UserManager;
import com.ihaozuo.plamexam.model.ConsultModel;
import com.ihaozuo.plamexam.model.IBaseModel;
import com.ihaozuo.plamexam.model.ReportModel;
import com.ihaozuo.plamexam.view.base.IBaseView;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by zhangzhongyao on 2016/10/18
 */

public class ReportDetailPresenter extends AbstractPresenter implements ReportContract.IReportDetailPresenter {
    private ReportContract.IReportDetailView mView;
    private ReportModel mReportModel;
    private ConsultModel mConsultModel;
    private String workNo;
    private String checkUnitCode;

    @Inject
    public ReportDetailPresenter(@NonNull ReportContract.IReportDetailView reportDetailView, @NonNull ReportModel reportModel, @NonNull ConsultModel consultModel,
                                 @NonNull @Named("WorkNo") String workNo, @NonNull @Named("CheckUnitCode") String checkUnitCode) {
        mView = reportDetailView;
        mReportModel = reportModel;
        mConsultModel = consultModel;
        mView.setPresenter(this);
        this.workNo = workNo;
        this.checkUnitCode = checkUnitCode;
    }

    @Override
    public IBaseView getBaseView() {
        return mView;
    }

    @Override
    public IBaseModel[] getBaseModelList() {
        return new IBaseModel[]{mConsultModel, mReportModel};
    }

    @Override
    public void start() {
        getReportDetail(workNo, checkUnitCode);
    }

    @Override
    public void getReportDetail(final String workNo, String checkUnitCode) {
        mView.showDialog();
        mReportModel.getReportDetail(workNo, checkUnitCode, new OnHandlerResultListener<RestResult<ReportDetailBean>>() {
            @Override
            public void handlerResultSuccess(RestResult<ReportDetailBean> resultData) {
                resultData.Data.WorkNo = workNo;
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

//    @Override
//    public void sendMsgForReport(ReportDetailBean bean, String content) {
//        mView.showDialog();
//        mConsultModel.sendMsgForReport(UserManager.getInstance().getUserInfo().AccountId, Constants.TYPE_SENDFORREPORT,
//                content, bean.CheckUnitCode, bean.WorkNo, bean.CheckUnitName, bean.ReportDateFormat,
//                new OnHandlerResultListener<RestResult<List<ConsultDetailBean>>>() {
//                    @Override
//                    public void handlerResultSuccess(RestResult<List<ConsultDetailBean>> resultData) {
//                        mView.turnConsultDetail(resultData.Data);
//                        mView.hideDialog();
//                    }
//
//                    @Override
//                    public void handlerResultError(String message) {
//                        mView.hideDialog(message);
//                    }
//                });
//    }

    @Override
    public void sendReport(ReportDetailBean bean, String content) {
        mView.showDialog();
        mConsultModel.SendReport(UserManager.getInstance().getUserInfo().AccountId, Constants.TYPE_SENDFORREPORT,
                content, bean.CheckUnitCode, bean.WorkNo, bean.CheckUnitName, bean.ReportDateFormat,
                new OnHandlerResultListener<RestResult<List<ConsultDetailBean>>>() {
                    @Override
                    public void handlerResultSuccess(RestResult<List<ConsultDetailBean>> resultData) {
                        mView.turnConsultDetail();
                        mView.hideDialog();
                    }

                    @Override
                    public void handlerResultError(String message) {
                        mView.hideDialog(message);
                    }
                });
    }
}