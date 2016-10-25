package com.ihaozuo.plamexam.contract;

import com.ihaozuo.plamexam.bean.NewsBean;
import com.ihaozuo.plamexam.presenter.IBasePresenter;
import com.ihaozuo.plamexam.view.base.IBaseView;

import java.util.List;

public interface NewsDetailContract {

    interface INewsDetailView extends IBaseView<INewsDetailPresenter> {

        void refreshNewsDetail(List<NewsBean> newsList);

    }

    interface INewsDetailPresenter extends IBasePresenter {

        void getNewsDetail(int ID);

    }

}