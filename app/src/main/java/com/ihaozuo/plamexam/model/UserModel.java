package com.ihaozuo.plamexam.model;

import android.support.annotation.NonNull;

import com.ihaozuo.plamexam.bean.DoctorInfoBean;
import com.ihaozuo.plamexam.bean.RestResult;
import com.ihaozuo.plamexam.bean.UserBean;
import com.ihaozuo.plamexam.framework.SysConfig;
import com.ihaozuo.plamexam.listener.OnHandlerResultListener;
import com.ihaozuo.plamexam.service.IUserService;
import com.ihaozuo.plamexam.util.HZUtils;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by hzguest3 on 2016/10/10.
 */
public class UserModel extends AbstractModel {

    private static final String BASIC_SIGN_SECRET = SysConfig.BASE_API[2];

    IUserService mIUserService;

    @Inject
    public UserModel(@NonNull IUserService userService) {
        mIUserService = userService;
    }

    public void getAuthCode(String mobile, final OnHandlerResultListener<RestResult<Boolean>> callbackListener) {
        Subscriber subscriber = getSubscriber(callbackListener);
        Map<String, Object> params = HZUtils.initParamsMap();
        params.put("Mobile", mobile);
        params.put("timespan", System.currentTimeMillis() / 1000L);
        params.put("paramSecret", BASIC_SIGN_SECRET);
        mIUserService.getAuthCode(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    public void register(String mobile, String validCode, final OnHandlerResultListener<RestResult<UserBean>> callbackListener) {
        Subscriber subscriber = getSubscriber(callbackListener);
        Map<String, Object> params = HZUtils.initParamsMap();
        params.put("Mobile", mobile);
        params.put("ValidCode", validCode);
        params.put("OS", SysConfig.LOCAL_OPERATION_SYSTEM);
        mIUserService.register(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void getDoctorId(String accountId, final OnHandlerResultListener<RestResult<String>> callbackListener) {
        Subscriber subscriber = getSubscriber(callbackListener);
        Map<String,Object> params = HZUtils.initParamsMap();
        params.put("AccountId", accountId);
        mIUserService.getDoctorId(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void getDoctorList(final OnHandlerResultListener<RestResult<List<DoctorInfoBean>>> callbackListener) {
        Subscriber subscriber = getSubscriber(callbackListener);
        Map<String,Object> params = HZUtils.initParamsMap();
        mIUserService.getDoctorList(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    public void modifyUserInfo(String accountId,String realName, final OnHandlerResultListener<RestResult<List<DoctorInfoBean>>> callbackListener) {
        Subscriber subscriber = getSubscriber(callbackListener);
        Map<String,Object> params = HZUtils.initParamsMap();
        params.put("AccountId",accountId);
        params.put("RealName",realName);
        mIUserService.modifyUserInfo(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
