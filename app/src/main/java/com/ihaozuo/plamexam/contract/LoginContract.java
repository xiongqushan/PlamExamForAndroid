package com.ihaozuo.plamexam.contract;


import com.ihaozuo.plamexam.presenter.IBasePresenter;
import com.ihaozuo.plamexam.view.base.IBaseView;

/**
 * Created by xiongwei1 on 2016/7/4.
 */
public interface LoginContract {

    interface ILoginView extends IBaseView<ILoginPresenter> {

        void turnMainActivity();
    }

    interface ILoginPresenter extends IBasePresenter {
    }
}
