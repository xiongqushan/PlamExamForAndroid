package com.ihaozuo.plamexam.presenter;

import android.support.annotation.NonNull;

import com.ihaozuo.plamexam.bean.RestResult;
import com.ihaozuo.plamexam.bean.VersionInfoBean;
import com.ihaozuo.plamexam.contract.SysSetContract;
import com.ihaozuo.plamexam.listener.OnHandlerResultListener;
import com.ihaozuo.plamexam.model.HomeModel;
import com.ihaozuo.plamexam.model.IBaseModel;
import com.ihaozuo.plamexam.view.base.IBaseView;

import javax.inject.Inject;

/**
 * Created by zhangzhongyao on 2016/10/25.
 */
public class SysSetPresenter extends AbstractPresenter implements SysSetContract.ISysSetPresenter {
    private SysSetContract.ISysSetView mView;
    private HomeModel mHomeModel;

    @Inject
    public SysSetPresenter(@NonNull SysSetContract.ISysSetView iSysSetView, @NonNull HomeModel homeModel) {
        mView = iSysSetView;
        mHomeModel = homeModel;
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
    }

    @Override
    public void getVersion() {
        mView.showDialog();
        mHomeModel.getVersion(new OnHandlerResultListener<RestResult<VersionInfoBean>>() {

            @Override
            public void handlerResultSuccess(RestResult<VersionInfoBean> resultData) {
                mView.hideDialog();
                mView.showUpdateInfo(resultData.Data);
            }

            @Override
            public void handlerResultError(String message) {
                mView.hideDialog(message);
            }
        });
    }
}
