package com.ihaozuo.plamexam.model;

import android.support.annotation.NonNull;

import com.ihaozuo.plamexam.bean.ReportDetailBean;
import com.ihaozuo.plamexam.bean.ReportItemBean;
import com.ihaozuo.plamexam.bean.RestResult;
import com.ihaozuo.plamexam.listener.OnHandlerResultListener;
import com.ihaozuo.plamexam.service.IReportService;
import com.ihaozuo.plamexam.util.HZUtils;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import rx.Subscriber;

/**
 * Created by zhangzhongyao on 2016/10/18.
 */
public class ReportModel extends AbstractModel {
    private IReportService mIReportService;

    @Inject
    public ReportModel(@NonNull IReportService iReportService) {
        mIReportService = iReportService;
    }

    public void getReportList(final OnHandlerResultListener<RestResult<List<ReportItemBean>>> callbackListener) {
        Subscriber subscriber = getSubscriber(callbackListener);
        Map<String, Object> params = HZUtils.initParamsMap();
        mIReportService.getReportList(params)
                .compose(applyAsySchedulers())
                .subscribe(subscriber);
    }

    public void getReportDetail(final OnHandlerResultListener<RestResult<ReportDetailBean>> callbackListener) {
        Subscriber subscriber = getSubscriber(callbackListener);
        Map<String, Object> params = HZUtils.initParamsMap();
        mIReportService.getReportDetail(params)
                .compose(applyAsySchedulers())
                .subscribe(subscriber);
    }

}
