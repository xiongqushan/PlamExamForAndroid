package com.ihaozuo.plamexam.contract;

import com.ihaozuo.plamexam.presenter.IBasePresenter;
import com.ihaozuo.plamexam.view.base.IBaseView;

/**
 * Created by hzguest3 on 2016/10/18.
 */
public interface ConsultGradeContract {

    interface IConsultGradeView extends IBaseView<IConsultGradePresenter> {
        void finishView();
    }

    interface IConsultGradePresenter extends IBasePresenter {

        void sendGrade(int score, String content);

    }
}
