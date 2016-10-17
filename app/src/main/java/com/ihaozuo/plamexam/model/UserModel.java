package com.ihaozuo.plamexam.model;

import android.support.annotation.NonNull;

import com.ihaozuo.plamexam.bean.RestResult;
import com.ihaozuo.plamexam.framework.SysConfig;
import com.ihaozuo.plamexam.listener.OnHandlerResultListener;
import com.ihaozuo.plamexam.service.IUserService;

import java.util.Map;
import java.util.TreeMap;

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

    public void GetSMSCode(String mobile, final OnHandlerResultListener<RestResult<Boolean>> callbackListener) {
        Subscriber subscriber = getSubscriber(callbackListener);
        Map<String, Object> params = new TreeMap<>();
        params.put("Mobile", mobile);
        params.put("timespan", System.currentTimeMillis() / 1000L);
        params.put("paramSecret", BASIC_SIGN_SECRET);
        mIUserService.testPost(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}
