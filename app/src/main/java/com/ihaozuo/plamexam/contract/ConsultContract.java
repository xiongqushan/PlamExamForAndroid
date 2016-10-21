package com.ihaozuo.plamexam.contract;

import com.ihaozuo.plamexam.bean.ConsultDetailBean;
import com.ihaozuo.plamexam.presenter.IBasePresenter;
import com.ihaozuo.plamexam.view.base.IBaseView;

import java.util.List;

/**
 * Created by hzguest3 on 2016/10/13.
 */
public interface ConsultContract {
    interface IConsultView extends IBaseView<IConsultPresenter>{

        void refreshConsultList(List<ConsultDetailBean> list);

        void setDoctorInfo();

        void addReply(ConsultDetailBean replyContent);

        void removeUnreadMark();

    }

    interface IConsultPresenter extends IBasePresenter{

        void getConsultDetail();

        void sendMessage(int type, String consultContent);

    }

}
