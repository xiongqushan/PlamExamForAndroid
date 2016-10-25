package com.ihaozuo.plamexam.contract;

import com.ihaozuo.plamexam.bean.VersionInfoBean;
import com.ihaozuo.plamexam.presenter.IBasePresenter;
import com.ihaozuo.plamexam.view.base.IBaseView;

/**
 * Created by zhangzhongyao on 2016/10/25.
 */
public interface SysSetContract {

    interface ISysSetView extends IBaseView<ISysSetPresenter> {
        void showUpdateInfo(VersionInfoBean bean);
    }

    interface ISysSetPresenter extends IBasePresenter {
        void getVersion();
    }
}