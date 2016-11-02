package com.ihaozuo.plamexam.model;

import android.support.annotation.NonNull;
import android.util.Log;

import com.ihaozuo.plamexam.bean.BannerBean;
import com.ihaozuo.plamexam.bean.BaseBean;
import com.ihaozuo.plamexam.bean.NewsBean;
import com.ihaozuo.plamexam.bean.RestResult;
import com.ihaozuo.plamexam.bean.VersionInfoBean;
import com.ihaozuo.plamexam.common.Constants;
import com.ihaozuo.plamexam.listener.OnHandlerResultListener;
import com.ihaozuo.plamexam.listener.OnHandlerResultWithCompletedListener;
import com.ihaozuo.plamexam.service.IHomeService;
import com.ihaozuo.plamexam.util.HZUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * Created by zhangzhongyao on 2016/10/17.
 */
public class HomeModel extends AbstractModel {
    private IHomeService mIHomeService;

    @Inject
    public HomeModel(@NonNull IHomeService iHomeService) {
        mIHomeService = iHomeService;
    }

    public void getBaner(String departCode, final OnHandlerResultListener<RestResult<List<BannerBean>>> callbackListener) {
        Subscriber subscriber = getSubscriber(callbackListener);
        Map<String, Object> params = HZUtils.initParamsMap();
        params.put("DepartCode", departCode);
        mIHomeService.getBanner(params)
                .compose(applyAsySchedulers())
                .subscribe(subscriber);

    }

    public void addFeedback(String departName, String realName, String mobile, String content, final OnHandlerResultListener<RestResult<List<BannerBean>>> callbackListener) {
        Subscriber subscriber = getSubscriber(callbackListener);
        Map<String, Object> params = HZUtils.initParamsMap();
        params.put("DepartName", departName);
        params.put("RealName", realName);
        params.put("Mobile", mobile);
        params.put("FeedbackContent", content);
        mIHomeService.addFeedback(params)
                .compose(applyAsySchedulers())
                .subscribe(subscriber);

    }

    public void getNewsList(int pageIndex, int pageSize, final OnHandlerResultListener<RestResult<List<NewsBean>>> callbackListener) {
        Subscriber subscriber = getSubscriber(callbackListener);
        Map<String, Object> params = HZUtils.initParamsMap();
        params.put("PageIndex", pageIndex);
        params.put("PageSize", pageSize);
        mIHomeService.getNewsList(params)
                .compose(applyAsySchedulers())
                .subscribe(subscriber);

    }

    public void getVersion(final OnHandlerResultListener<RestResult<VersionInfoBean>> callbackListener) {
        Subscriber subscriber = getSubscriber(callbackListener);
        Map<String, Object> params = HZUtils.initParamsMap();
//        params.put("VersionCode", HZUtils.getCurrVersion(HZApp.shareApplication()));
        params.put("VersionCode", 3.0);
        params.put("OSType", Constants.OSTYPE);
        mIHomeService.getVersion(params)
                .compose(applyAsySchedulers())
                .subscribe(subscriber);

    }

    public void initData(final OnHandlerResultWithCompletedListener<RestResult> callback) {
        final Observable<BaseBean<BannerBean>> observable1 = Observable.just(new BaseBean<BannerBean>(new BannerBean()));
        final Observable<BaseBean<NewsBean>> observable2 = Observable.just(new BaseBean<NewsBean>(new NewsBean()));
        Observable<BaseBean<? extends Serializable>> merge = Observable.merge(observable1, observable2);
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


