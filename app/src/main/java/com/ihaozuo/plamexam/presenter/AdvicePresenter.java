package com.ihaozuo.plamexam.presenter;

import android.support.annotation.NonNull;

import com.ihaozuo.plamexam.bean.BannerBean;
import com.ihaozuo.plamexam.bean.RestResult;
import com.ihaozuo.plamexam.bean.UserBean;
import com.ihaozuo.plamexam.contract.AdviceContract;
import com.ihaozuo.plamexam.listener.OnHandlerResultListener;
import com.ihaozuo.plamexam.manager.UserManager;
import com.ihaozuo.plamexam.model.HomeModel;
import com.ihaozuo.plamexam.model.IBaseModel;
import com.ihaozuo.plamexam.view.base.IBaseView;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by hzguest3 on 2016/10/20.
 */
public class AdvicePresenter extends AbstractPresenter implements AdviceContract.IAdvicePresenter{

    private AdviceContract.IAdviceView mAdviceView;
    private HomeModel mHomeModel;
    private UserBean userBean;


    @Inject
    public AdvicePresenter(@NonNull AdviceContract.IAdviceView iAdviceView, @NonNull HomeModel homeModel) {
        mAdviceView = iAdviceView;
        mHomeModel = homeModel;
        mAdviceView.setPresenter(this);
        userBean = UserManager.getInstance().getUserInfo();
    }


    @Override
    public void start() {

    }

    @Override
    public IBaseView getBaseView() {
        return null;
    }

    @Override
    public IBaseModel[] getBaseModelList() {
        return new IBaseModel[]{mHomeModel};
    }

    @Override
    public void addFeedback(String content){
        mAdviceView.showDialog();
        mHomeModel.addFeedback(userBean.DepartName, userBean.RealName, userBean.Mobile, content, new OnHandlerResultListener<RestResult<List<BannerBean>>>() {
            @Override
            public void handlerResultSuccess(RestResult<List<BannerBean>> resultData) {
                mAdviceView.hideDialog();
                mAdviceView.showSuccessDialog();
            }

            @Override
            public void handlerResultError(String message) {
                mAdviceView.hideDialog(message);
            }
        });
    }
}
