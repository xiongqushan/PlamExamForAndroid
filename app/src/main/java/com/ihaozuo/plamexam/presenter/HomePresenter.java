package com.ihaozuo.plamexam.presenter;

import android.support.annotation.NonNull;

import com.ihaozuo.plamexam.contract.HomeContract;
import com.ihaozuo.plamexam.model.IBaseModel;
import com.ihaozuo.plamexam.view.base.IBaseView;

import javax.inject.Inject;

/**
 * Created by zhangzhongyao on 2016/10/10.
 */
public class HomePresenter extends AbstractPresenter implements HomeContract.IHomePresenter {
    HomeContract.IHomeView mHomeView;

    @Inject
    public HomePresenter(@NonNull HomeContract.IHomeView view) {
        mHomeView = view;
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

    }
}
