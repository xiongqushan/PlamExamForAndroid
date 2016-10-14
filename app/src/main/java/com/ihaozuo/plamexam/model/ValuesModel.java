package com.ihaozuo.plamexam.model;

import android.support.annotation.NonNull;
import android.util.Log;

import com.ihaozuo.plamexam.bean.BaseBean;
import com.ihaozuo.plamexam.bean.ObjectA;
import com.ihaozuo.plamexam.bean.ObjectB;
import com.ihaozuo.plamexam.bean.RestResult;
import com.ihaozuo.plamexam.bean.testBean;
import com.ihaozuo.plamexam.listener.OnHandlerResultListener;
import com.ihaozuo.plamexam.service.IValuesService;
import com.ihaozuo.plamexam.util.HZUtils;

import java.util.Map;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by hzguest3 on 2016/10/12.
 */

public class ValuesModel extends AbstractModel {
//    private static final String BASIC_SIGN_SECRET = SysConfig.BASE_API[2];

    IValuesService mIValuesService;

    @Inject
    public ValuesModel(@NonNull IValuesService valuesService) {
        super();
        mIValuesService = valuesService;
    }

    public void APITest(final OnHandlerResultListener<RestResult<testBean>> callbackListener) {
        Subscriber subscriber = getSubscriber(callbackListener);
        Map<String, Object> params = HZUtils.initParamsMap();
        params.put("mobile", "13818724007");
//        mIValuesService.testPost(params)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(subscriber);
        mIValuesService.getCode(params)
                .compose(applyAsySchedulers())
                .subscribe(subscriber);
    }

    public void APITestMultiPost(final OnHandlerResultListener<BaseBean> callbackListener) {
//        Subscriber subscriber = getSubscriber(callbackListener);
        Map<String, Object> params = HZUtils.initParamsMap();
        Observable<BaseBean<ObjectA>> Observable1 = mIValuesService.ParamObjectA(params);
        Observable<BaseBean<ObjectB>> Observable2 = mIValuesService.ParamObjectB(params);
        Observable<BaseBean<?>> observable = Observable.merge(Observable1, Observable2);
        Subscriber subscriber = new Subscriber<BaseBean<?>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Log.e("onError", "onError");
            }

            @Override
            public void onNext(BaseBean<?> baseBean) {
                Log.e("onNext", "onNext");
                Log.e("baseBean", baseBean.toString());
            }
        };
        observable.compose(applyAsySchedulers()).subscribe(subscriber);
    }
}