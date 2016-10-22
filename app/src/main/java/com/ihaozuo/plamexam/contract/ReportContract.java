package com.ihaozuo.plamexam.contract;

import com.ihaozuo.plamexam.bean.ConsultDetailBean;
import com.ihaozuo.plamexam.bean.ReportDetailBean;
import com.ihaozuo.plamexam.bean.ReportItemBean;
import com.ihaozuo.plamexam.presenter.IBasePresenter;
import com.ihaozuo.plamexam.view.base.IBaseView;

import java.util.List;

/**
 * Created by zhangzhongyao on 2016/10/18.
 */
public interface ReportContract {
    interface IReportGetView extends IBaseView<IReportGetPresenter> {
        void showReportList();
    }

    interface IReportGetPresenter extends IBasePresenter {
        void getReport(String mobile, String realName);
    }

    interface IReportListView extends IBaseView<IReportListPresenter> {
        void showAddBtn();

        void toggleRetryLayer(boolean show);

        void showReportList(List<ReportItemBean> dataList);
    }

    interface IReportListPresenter extends IBasePresenter {
        void getReportList();
    }


    interface IReportDetailView extends IBaseView<IReportDetailPresenter> {

        void updateFragment(ReportDetailBean reportDetailBean);

        void toggleRetryLayer(boolean show);

        void turnConsultDetail(List<ConsultDetailBean> data);
    }

    interface IReportDetailPresenter extends IBasePresenter {
        void getReportDetail(String workNo, String checkUnitCode);

        void sendMsgForReport(ReportDetailBean bean, String content);
    }

}
