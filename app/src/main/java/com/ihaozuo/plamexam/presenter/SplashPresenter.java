package com.ihaozuo.plamexam.presenter;

import android.support.annotation.NonNull;

import com.ihaozuo.plamexam.bean.RestResult;
import com.ihaozuo.plamexam.bean.VersionInfoBean;
import com.ihaozuo.plamexam.contract.SplashContract;
import com.ihaozuo.plamexam.listener.OnHandlerResultListener;
import com.ihaozuo.plamexam.model.HomeModel;
import com.ihaozuo.plamexam.model.IBaseModel;
import com.ihaozuo.plamexam.view.base.IBaseView;

import javax.inject.Inject;

/**
 * Created by zhangzhongyao on 2016/10/12.
 */
public class SplashPresenter extends AbstractPresenter implements SplashContract.ISplashPresenter {
    private SplashContract.ISplashView mView;
    private HomeModel mHomeModel;

    @Inject
    public SplashPresenter(@NonNull SplashContract.ISplashView iSplashView,
                           @NonNull HomeModel homeModel) {
        mHomeModel = homeModel;
        mView = iSplashView;
        mView.setPresenter(this);
    }

    @Override
    public IBaseView getBaseView() {
        return mView;
    }

    @Override
    public IBaseModel[] getBaseModelList() {
        return new IBaseModel[]{mHomeModel};
    }

    @Override
    public void start() {
        mHomeModel.getVersion(new OnHandlerResultListener<RestResult<VersionInfoBean>>() {
            @Override
            public void handlerResultSuccess(RestResult<VersionInfoBean> resultData) {
                mView.updateInfo(resultData.Data);
            }

            @Override
            public void handlerResultError(String message) {
                mView.turnNextAty();
            }
        });
    }
}
