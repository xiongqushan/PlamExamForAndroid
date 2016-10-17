package com.ihaozuo.plamexam.model;

import android.util.Log;

import com.ihaozuo.plamexam.bean.BannerBean;
import com.ihaozuo.plamexam.bean.BaseBean;
import com.ihaozuo.plamexam.bean.NewsBean;
import com.ihaozuo.plamexam.bean.RestResult;
import com.ihaozuo.plamexam.listener.OnHandlerResultWithCompletedListener;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by zhangzhongyao on 2016/10/17.
 */
public class HomeModel extends AbstractModel {
    @Inject
    public HomeModel() {
    }

    public void initData(final OnHandlerResultWithCompletedListener<RestResult> callback) {
        final Observable<BaseBean<BannerBean>> observable1 = Observable.just(new BaseBean<BannerBean>(new BannerBean()));
        final Observable<BaseBean<NewsBean>> observable2 = Observable.just(new BaseBean<NewsBean>(new NewsBean()));
        Observable<BaseBean<?>> merge = Observable.merge(observable1, observable2);
        merge.subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<BaseBean<?>>() {
                    @Override
                    public void onCompleted() {
                        Log.e("onCompleted", "onCompleted");
                        callback.onCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        RestResult entity = new RestResult(e.getMessage());
                        callback.handlerResult(entity);
                    }

                    @Override
                    public void onNext(BaseBean<?> bean) {
                        RestResult entity;
                        if (bean.Code > 0) {
                            entity = new RestResult(bean.Data);
                        } else {
                            entity = new RestResult(bean.Message);
                        }
                        callback.handlerResult(entity);

                    }
                });

    }
}


