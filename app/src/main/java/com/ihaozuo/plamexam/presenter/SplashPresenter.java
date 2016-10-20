package com.ihaozuo.plamexam.presenter;

import android.support.annotation.NonNull;

import com.ihaozuo.plamexam.contract.SplashContract;
import com.ihaozuo.plamexam.model.IBaseModel;
import com.ihaozuo.plamexam.view.base.IBaseView;

import javax.inject.Inject;

/**
 * Created by zhangzhongyao on 2016/10/12.
 */
public class SplashPresenter extends AbstractPresenter implements SplashContract.ISplashPresenter {
    private SplashContract.ISplashView mView;

    @Inject
    public SplashPresenter(@NonNull SplashContract.ISplashView iSplashView) {
        mView = iSplashView;
        mView.setPresenter(this);
    }

    @Override
    public IBaseView getBaseView() {
        return null;
    }

    @Override
    public IBaseModel[] getBaseModelList() {
        return new IBaseModel[]{};
    }

    @Override
    public void start() {

    }
}
