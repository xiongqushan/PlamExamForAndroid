package com.ihaozuo.plamexam.model;

import android.support.annotation.NonNull;

import com.ihaozuo.plamexam.bean.RestResult;
import com.ihaozuo.plamexam.bean.testBean;
import com.ihaozuo.plamexam.framework.SysConfig;
import com.ihaozuo.plamexam.listener.OnHandlerResultListener;
import com.ihaozuo.plamexam.service.IValuesService;
import com.ihaozuo.plamexam.util.HZUtils;

import java.util.Map;
import java.util.TreeMap;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by hzguest3 on 2016/10/12.
 */

public class ValuesModel extends AbstractModel {
    private static final String BASIC_SIGN_SECRET = SysConfig.BASE_API[2];

    IValuesService mIValuesService;

    @Inject
    public ValuesModel(@NonNull IValuesService valuesService){
        super();
        mIValuesService = valuesService;
    }

    public void APITest(final OnHandlerResultListener<RestResult<testBean>> callbackListener) {
        Subscriber subscriber = getSubscriber(callbackListener);
        Map<String,Object> params = HZUtils.initParamsMap();
        Map<String,Object> param = new TreeMap<>();
        String[] arr = new String[]{"@abc@wZZHO中\"<?>!@#$%^&*(_+213","@@abc@wZZHO中\"<?>!@#$%^&*(_+213@wZZHO中d","tes@abc@wZZHO中\"<?>!@#$%^&*(_+213"};
        param.put("ID",1);
        param.put("Arrs",arr);
        params.put("ID", 1);
        params.put("Name", "abc@wZZHO中\"<?>!@#$%^&*(_+213");
        params.put("AAA", param);
//        mIValuesService.testPost(params)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(subscriber);
        mIValuesService.testPost2(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
}