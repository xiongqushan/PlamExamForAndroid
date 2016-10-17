package com.ihaozuo.plamexam.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.ihaozuo.plamexam.bean.BannerBean;
import com.ihaozuo.plamexam.bean.NewsBean;
import com.ihaozuo.plamexam.bean.RestResult;
import com.ihaozuo.plamexam.contract.HomeContract;
import com.ihaozuo.plamexam.listener.OnHandlerResultWithCompletedListener;
import com.ihaozuo.plamexam.model.HomeModel;
import com.ihaozuo.plamexam.model.IBaseModel;
import com.ihaozuo.plamexam.view.base.IBaseView;

import javax.inject.Inject;

/**
 * Created by zhangzhongyao on 2016/10/10.
 */
public class HomePresenter extends AbstractPresenter implements HomeContract.IHomePresenter {
    HomeContract.IHomeView mHomeView;
    private HomeModel mHomeModel;

    @Inject
    public HomePresenter(@NonNull HomeContract.IHomeView view, @NonNull HomeModel homeModel) {
        mHomeView = view;
        mHomeModel = homeModel;
        mHomeView.setPresenter(this);
    }

    @Override
    public IBaseView getBaseView() {
        return mHomeView;
    }

    @Override
    public IBaseModel[] getBaseModelList() {
        return new IBaseModel[]{};
    }


    @Override
    public void start() {
        new HomeModel().initData(new OnHandlerResultWithCompletedListener<RestResult>() {
            @Override
            public void handlerResult(RestResult bean) {
                if (bean.Data instanceof BannerBean) {
                    Log.e("BannerBean", "BannerBean");
                } else if (bean.Data instanceof NewsBean) {
                    Log.e("NewsBean", "NewsBean");
                }
            }

            @Override
            public void onCompleted() {
                Log.e("Listener-nCompleted", "Listener-Completed");
            }
        });
    }
}
