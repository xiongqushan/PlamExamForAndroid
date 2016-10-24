package com.ihaozuo.plamexam.model;

import com.ihaozuo.plamexam.bean.ConsultDetailBean;
import com.ihaozuo.plamexam.bean.RestResult;
import com.ihaozuo.plamexam.bean.UnreadMarkBean;
import com.ihaozuo.plamexam.listener.OnHandlerResultListener;
import com.ihaozuo.plamexam.service.IConsultService;
import com.ihaozuo.plamexam.util.HZUtils;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by hzguest3 on 2016/10/13.
 */
public class ConsultModel extends AbstractModel implements IBaseModel {

    IConsultService mIConsultService;

    @Inject
    public ConsultModel(IConsultService consultService) {
        mIConsultService = consultService;
    }


    public void getConsultDetail(String accountId, final OnHandlerResultListener<RestResult<List<ConsultDetailBean>>> callbackListener) {
        Subscriber subscriber = getSubscriber(callbackListener);
        Map<String, Object> params = HZUtils.initParamsMap();
        params.put("AccountId", accountId);
        mIConsultService.getConsultDetail(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void sendMessage(String accountId, int type, String consultContent, final OnHandlerResultListener<RestResult<Boolean>> callbackListener) {
        Subscriber subscriber = getSubscriber(callbackListener);
        Map<String, Object> params = HZUtils.initParamsMap();
        params.put("AccountId", accountId);
        params.put("Type", type);//1,3尚未约定
        params.put("ConsultContent", consultContent);
        params.put("AppendInfo", "");//暂时为空
        mIConsultService.sendMessage(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void sendGrade(String accountId, int score, String content, final OnHandlerResultListener<RestResult<Boolean>> callbackListener) {
        Subscriber subscriber = getSubscriber(callbackListener);
        Map<String, Object> params = HZUtils.initParamsMap();
        params.put("AccountId", accountId);
        params.put("Score", score);
        params.put("Content", content);
        mIConsultService.sendGrade(params);
    }

    public void getUnreadMarkState(String accountId, final OnHandlerResultListener<RestResult<List<UnreadMarkBean>>> callbackListener) {
        Subscriber subscriber = getSubscriber(callbackListener);
        Map<String, Object> params = HZUtils.initParamsMap();
        params.put("AccountId", accountId);
        mIConsultService.getUnreadMarkState(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void removeUnreadMark(String accountId, final OnHandlerResultListener<RestResult<Boolean>> callbackListener) {
        Subscriber subscriber = getSubscriber(callbackListener);
        Map<String, Object> params = HZUtils.initParamsMap();
        params.put("AccountId", accountId);
        mIConsultService.RemoveUnreadMark(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void sendMsgForReport(String accountId, String type, String content, String code,
                                 String no, String name, String date,
                                 final OnHandlerResultListener<RestResult<List<ConsultDetailBean>>> callbackListener) {
        Subscriber subscriber = getSubscriber(callbackListener);
        Map<String, Object> params = HZUtils.initParamsMap();
        params.put("AccountId", accountId);
        params.put("Type", type);
        params.put("ConsultContent", content);
        params.put("CheckUnitCode", code);
        params.put("WorkNo", no);
        params.put("CheckUnitName", name);
        params.put("ReportDate", date);
        mIConsultService.sendMsgForReport(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

}
