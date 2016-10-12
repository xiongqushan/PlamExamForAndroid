package com.ihaozuo.plamexam.presenter;

import android.support.annotation.NonNull;

import com.ihaozuo.plamexam.contract.LoginContract;
import com.ihaozuo.plamexam.model.IBaseModel;
import com.ihaozuo.plamexam.model.UserModel;
import com.ihaozuo.plamexam.view.base.IBaseView;

import javax.inject.Inject;

/**
 * Created by hzguest3 on 2016/10/10.
 */
public class LoginPresenter extends AbstractPresenter implements LoginContract.ILoginPresenter{

    private LoginContract.ILoginView mLoginView;
    private UserModel mUserModel;

    @Inject
    public LoginPresenter(@NonNull LoginContract.ILoginView loginView,@NonNull UserModel userModel){
        mLoginView = loginView;
        mUserModel = userModel;
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
//        mLoginView.showDialog();
//        mUserModel.GetSMSCode("12345", new OnHandlerResultListener<RestResult<Boolean>>() {
//            @Override
//            public void handlerResult(RestResult<Boolean> resultData) {
//                if (resultData.LogicSuccess){
//                    mLoginView.hideDialog();
//                }else {
//                    mLoginView.hideDialog(resultData.Message);
//                }
//            }
//        });
    }
}
