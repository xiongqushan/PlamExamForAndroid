package com.ihaozuo.plamexam.model;

import android.support.annotation.NonNull;

import com.ihaozuo.plamexam.bean.RestResult;
import com.ihaozuo.plamexam.bean.testBean;
import com.ihaozuo.plamexam.listener.OnHandlerResultListener;
import com.ihaozuo.plamexam.service.IValuesService;
import com.ihaozuo.plamexam.util.HZUtils;

import java.util.Map;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by hzguest3 on 2016/10/12.
 */

public class ValuesModel extends AbstractModel {
//    private static final String BASIC_SIGN_SECRET = SysConfig.BASE_API[2];

    IValuesService mIValuesService;

    @Inject
    public ValuesModel(@NonNull IValuesService valuesService){
        super();
        mIValuesService = valuesService;
    }

    public void APITest(final OnHandlerResultListener<RestResult<testBean>> callbackListener) {
        Subscriber subscriber = getSubscriber(callbackListener);
        Map<String,Object> params = HZUtils.initParamsMap();
        params.put("mobile", "13818724007");
//        mIValuesService.testPost(params)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(subscriber);
        mIValuesService.getCode(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}