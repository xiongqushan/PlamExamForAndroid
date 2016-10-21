package com.ihaozuo.plamexam.contract;

import com.ihaozuo.plamexam.presenter.IBasePresenter;
import com.ihaozuo.plamexam.view.base.IBaseView;

/**
 * Created by hzguest3 on 2016/10/20.
 */
public interface AdviceContract {

    interface IAdviceView extends IBaseView<IAdvicePresenter> {
        void showSuccessDialog();
    }

    interface IAdvicePresenter extends IBasePresenter {

        void addFeedback(String content);

    }
}
