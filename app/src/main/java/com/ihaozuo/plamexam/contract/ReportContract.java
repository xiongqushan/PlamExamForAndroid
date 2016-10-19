package com.ihaozuo.plamexam.contract;

import com.ihaozuo.plamexam.bean.ReportItemBean;
import com.ihaozuo.plamexam.presenter.IBasePresenter;
import com.ihaozuo.plamexam.view.base.IBaseView;

import java.util.List;

/**
 * Created by zhangzhongyao on 2016/10/18.
 */
public interface ReportContract {
    interface ReportGetView extends IBaseView<ReportGetPresenter> {
    }

    interface ReportGetPresenter extends IBasePresenter {
    }

    interface ReportListView extends IBaseView<ReportListPresenter> {
        void showAddBtn();

        void showReportList(List<ReportItemBean> dataList);
    }

    interface ReportListPresenter extends IBasePresenter {
    }


    interface ReportDetailView extends IBaseView<ReportDetailPresenter> {
    }

    interface ReportDetailPresenter extends IBasePresenter {

    }

}
