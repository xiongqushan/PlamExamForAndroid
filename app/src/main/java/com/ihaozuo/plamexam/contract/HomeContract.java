package com.ihaozuo.plamexam.contract;

import com.ihaozuo.plamexam.presenter.IBasePresenter;
import com.ihaozuo.plamexam.view.base.IBaseView;

/**
 * Created by zhangzhongyao on 2016/10/10.
 */
public interface HomeContract {
    interface IHomeView extends IBaseView<IHomePresenter> {
    }

    interface IHomePresenter extends IBasePresenter {

        void getBanner(int departId);

    }
}
