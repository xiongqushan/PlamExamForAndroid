package com.ihaozuo.plamexam.contract;


import com.ihaozuo.plamexam.bean.UpdateInfoBean;
import com.ihaozuo.plamexam.presenter.IBasePresenter;
import com.ihaozuo.plamexam.view.base.IBaseView;

/**
 * Created by zhangzhongyao on 2016/10/09.
 */
public interface SplashContract {

    interface ISplashView extends IBaseView<ISplashPresenter> {
        void updateInfo(UpdateInfoBean bean);

        void turnNextAty();
    }

    interface ISplashPresenter extends IBasePresenter {
    }
}
