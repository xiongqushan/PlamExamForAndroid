package com.ihaozuo.plamexam.contract;

import com.ihaozuo.plamexam.presenter.IBasePresenter;
import com.ihaozuo.plamexam.view.base.IBaseView;

/**
 * Created by hzguest3 on 2016/10/10.
 */
public interface LoginContract {
    interface ILoginView extends IBaseView<ILoginPresenter>{

        void gotoMainPage();

    }

    interface ILoginPresenter extends IBasePresenter{

        void getAuthCode(String mobile);

        void register(String mobile,String validCode);

//        void getDoctorID();
//
//        void getDoctorList();

    }
}

