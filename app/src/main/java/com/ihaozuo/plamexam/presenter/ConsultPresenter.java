package com.ihaozuo.plamexam.presenter;

import android.support.annotation.NonNull;

import com.ihaozuo.plamexam.bean.ConsultDetailBean;
import com.ihaozuo.plamexam.bean.RestResult;
import com.ihaozuo.plamexam.bean.UserBean;
import com.ihaozuo.plamexam.contract.ConsultContract;
import com.ihaozuo.plamexam.listener.OnHandlerResultListener;
import com.ihaozuo.plamexam.manager.UserManager;
import com.ihaozuo.plamexam.model.ConsultModel;
import com.ihaozuo.plamexam.model.IBaseModel;
import com.ihaozuo.plamexam.view.base.IBaseView;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by hzguest3 on 2016/10/13.
 */
public class ConsultPresenter extends AbstractPresenter implements ConsultContract.IConsultPresenter {

    private ConsultContract.IConsultView mIConsultView;
    private ConsultModel mConsultModel;
    private UserBean mUserInfo;

    @Inject
    public ConsultPresenter(@NonNull ConsultContract.IConsultView iConsultView, @NonNull ConsultModel consultModel){
        mIConsultView = iConsultView;
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
        getConsultDetail();
    }

    @Override
    public void getConsultDetail(){
        mIConsultView.showDialog();
        mConsultModel.getConsultDetail(mUserInfo.AccountId, new OnHandlerResultListener<RestResult<List<ConsultDetailBean>>>() {
            @Override
            public void handlerResult(RestResult<List<ConsultDetailBean>> resultData) {
                if (resultData.LogicSuccess){
                    mIConsultView.refreshConsultList(resultData.Data);
                    mIConsultView.hideDialog();
                }else {
                    mIConsultView.hideDialog(resultData.Message);
                }
            }
        });
    }


    @Override
    public void sendMessage(int type, String consultContent){
        mIConsultView.showDialog();
        mConsultModel.sendMessage(mUserInfo.AccountId, type, consultContent, new OnHandlerResultListener<RestResult<Boolean>>() {
            @Override
            public void handlerResult(RestResult<Boolean> resultData) {
                if (resultData.LogicSuccess){
                    mIConsultView.hideDialog();
                }else {
                    mIConsultView.hideDialog(resultData.Message);
                }
            }
        });
    }

}
