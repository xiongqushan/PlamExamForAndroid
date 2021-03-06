package com.ihaozuo.plamexam.contract;

import com.ihaozuo.plamexam.bean.BannerBean;
import com.ihaozuo.plamexam.database.newsdbutils.NewsDBPojo;
import com.ihaozuo.plamexam.presenter.IBasePresenter;
import com.ihaozuo.plamexam.view.base.IBaseView;

import java.util.List;

/**
 * Created by zhangzhongyao on 2016/10/10.
 */
public interface HomeContract {
    interface IHomeView extends IBaseView<IHomePresenter> {
        void initBanner(List<BannerBean> bannerList);

        void stopRefreshing();

        void showUnreadMark();

        void refreshNewsList(List<NewsDBPojo> newsList);

    }

    interface IHomePresenter extends IBasePresenter {

        void getBanner(String departCode);

        void getUnreadMartState(String accountId);

//        void removeUnreadMark(String accountId);

        void getNewsList(int pageIndex, int pageSize);
    }
}
