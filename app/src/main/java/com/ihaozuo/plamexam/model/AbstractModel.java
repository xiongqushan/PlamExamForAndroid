package com.ihaozuo.plamexam.model;

import android.support.annotation.NonNull;
import android.util.Log;

import com.ihaozuo.plamexam.bean.BaseBean;
import com.ihaozuo.plamexam.bean.RestResult;
import com.ihaozuo.plamexam.listener.OnHandlerResultListener;
import com.squareup.okhttp.OkHttpClient;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by xiongwei1 on 2016/7/27.
 */
public abstract class AbstractModel implements IBaseModel {
    //    OkHttpClient mOkHttpClient;
    List<Subscriber> subscriberList;


    public AbstractModel(@NonNull OkHttpClient okHttpClient) {
//        mOkHttpClient=okHttpClient;
        subscriberList = new ArrayList<>();
        Log.e("subscriberList", subscriberList.toString() + "");
    }

    public static <T> Observable.Transformer<T, T> applyAsySchedulers() {
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    protected <T> Subscriber<BaseBean<T>> getSubscriber(@NonNull final OnHandlerResultListener<RestResult<T>> callbackListener) {
        Subscriber subscriber = new Subscriber<BaseBean<T>>() {

            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                RestResult<T> entity = new RestResult<T>(e.getMessage());
                callbackListener.handlerResult(entity);
            }

            @Override
            public void onNext(BaseBean<T> resultBean) {
                RestResult<T> entity = null;
                if (resultBean.state > 0) {
                    T result = resultBean.Data;
                    entity = new RestResult<T>(result);
                } else {
                    entity = new RestResult<T>(resultBean.message);
                }
                callbackListener.handlerResult(entity);
            }
        };
        subscriberList.add(subscriber);
        return subscriber;
    }

    @Override
    public void cancelRequest() {
        for (Subscriber subscriber : subscriberList) {
            if (!subscriber.isUnsubscribed()) {
                subscriber.unsubscribe();
            }
        }
        subscriberList.clear();
    }
}
