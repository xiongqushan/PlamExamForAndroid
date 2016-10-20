package com.ihaozuo.plamexam.presenter;

import android.support.annotation.NonNull;

import com.ihaozuo.plamexam.bean.RestResult;
import com.ihaozuo.plamexam.bean.UserBean;
import com.ihaozuo.plamexam.contract.ConsultGradeContract;
import com.ihaozuo.plamexam.listener.OnHandlerResultListener;
import com.ihaozuo.plamexam.manager.UserManager;
import com.ihaozuo.plamexam.model.ConsultModel;
import com.ihaozuo.plamexam.model.IBaseModel;
import com.ihaozuo.plamexam.view.base.IBaseView;

import javax.inject.Inject;

/**
 * Created by hzguest3 on 2016/10/18.
 */
public class ConsultGradePresenter extends AbstractPresenter implements ConsultGradeContract.IConsultGradePresenter {

    private ConsultGradeContract.IConsultGradeView mIConsultView;
    private ConsultModel mConsultModel;
    private UserBean mUserInfo;

    @Inject
    public ConsultGradePresenter(@NonNull ConsultGradeContract.IConsultGradeView iConsultGradeView, @NonNull ConsultModel consultModel) {
        mIConsultView = iConsultGradeView;
        mConsultModel = consultModel;
        mIConsultView.setPresenter(this);
        mUserInfo = UserManager.getInstance().getUserInfo();
    }

    @Override
    public IBaseView getBaseView() {
        return mIConsultView;
    }

    @Override
    public IBaseModel[] getBaseModelList() {
        return new IBaseModel[]{mConsultModel};
    }

    @Override
    public void start() {

    }

    @Override
    public void sendGrade(int score, String content) {
        mIConsultView.showDialog();
        mConsultModel.sendGrade(mUserInfo.AccountId, score, content, new OnHandlerResultListener<RestResult<Boolean>>() {
            @Override
            public void handlerResultSuccess(RestResult<Boolean> resultData) {
                mIConsultView.hideDialog();
                mIConsultView.finishView();
            }

            @Override
            public void handlerResultError(String message) {
                mIConsultView.hideDialog(message);

            }

        });
    }
}
