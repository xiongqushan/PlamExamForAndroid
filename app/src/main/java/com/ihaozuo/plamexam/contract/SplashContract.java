package com.ihaozuo.plamexam.contract;


import com.ihaozuo.plamexam.presenter.IBasePresenter;
import com.ihaozuo.plamexam.view.base.IBaseView;

/**
 * Created by zhangzhongyao on 2016/10/09.
 */
public interface SplashContract {

    interface IWelcomeView extends IBaseView<ISplashPresenter> {
        void updateInfo( );

        void turnNextAty();
    }

    interface ISplashPresenter extends IBasePresenter {
    }
}
