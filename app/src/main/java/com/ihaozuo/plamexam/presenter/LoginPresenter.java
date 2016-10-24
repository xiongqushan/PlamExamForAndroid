package com.ihaozuo.plamexam.presenter;

import android.support.annotation.NonNull;

import com.ihaozuo.plamexam.bean.RestResult;
import com.ihaozuo.plamexam.bean.UserBean;
import com.ihaozuo.plamexam.contract.LoginContract;
import com.ihaozuo.plamexam.listener.OnHandlerResultListener;
import com.ihaozuo.plamexam.manager.PreferenceManager;
import com.ihaozuo.plamexam.manager.UserManager;
import com.ihaozuo.plamexam.model.IBaseModel;
import com.ihaozuo.plamexam.model.UserModel;
import com.ihaozuo.plamexam.view.base.IBaseView;

import javax.inject.Inject;

/**
 * Created by hzguest3 on 2016/10/10.
 */
public class LoginPresenter extends AbstractPresenter implements LoginContract.ILoginPresenter {

    private LoginContract.ILoginView mLoginView;
    private UserModel mUserModel;


    @Inject
    public LoginPresenter(@NonNull LoginContract.ILoginView loginView, @NonNull UserModel usermodel) {
        mLoginView = loginView;
        mUserModel = usermodel;
        loginView.setPresenter(this);
    }

    @Override
    public IBaseView getBaseView() {
        return mLoginView;
    }

    @Override
    public IBaseModel[] getBaseModelList() {
        return new IBaseModel[]{mUserModel};
    }

    @Override
    public void start() {

    }

    @Override
    public void getAuthCode(String mobile) {
        mLoginView.showDialog();
        mUserModel.getAuthCode(mobile, new OnHandlerResultListener<RestResult<Boolean>>() {
            @Override
            public void handlerResultSuccess(RestResult<Boolean> resultData) {
                mLoginView.hideDialog();
            }

            @Override
            public void handlerResultError(String message) {
                mLoginView.hideDialog(message);
            }

        });
    }

    @Override
    public void register(String mobile, String validCode) {
        mLoginView.showDialog();
        mUserModel.register(mobile, validCode, new OnHandlerResultListener<RestResult<UserBean>>() {
            @Override
            public void handlerResultSuccess(RestResult<UserBean> resultData) {
                UserManager.getInstance().setUserInfo(resultData.Data);
                UserManager.getInstance().updateDepartCode(resultData.Data.DepartCode);
                PreferenceManager.getInstance().writeLoginPhone(resultData.Data.Mobile);
                mLoginView.hideDialog();
                mLoginView.gotoMainPage();
            }

            @Override
            public void handlerResultError(String message) {
                mLoginView.hideDialog(message);
            }

        });
    }


}
