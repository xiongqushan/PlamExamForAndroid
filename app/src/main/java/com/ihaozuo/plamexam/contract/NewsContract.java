package com.ihaozuo.plamexam.contract;

import com.ihaozuo.plamexam.bean.NewsBean;
import com.ihaozuo.plamexam.presenter.IBasePresenter;
import com.ihaozuo.plamexam.view.base.IBaseView;

import java.util.List;

/**
 * Created by hzguest3 on 2016/10/25.
 */

public interface NewsContract {

    interface INewsListView extends IBaseView<INewsListPresenter> {

        void refreshNewsList(List<NewsBean> newsList);

        void loadMoreList(List<NewsBean> newsList);

        void stopRefreshing();

    }

    interface INewsListPresenter extends IBasePresenter {

//        void getNewsList();

        void getNewsList(Boolean isRefresh);

    }

}