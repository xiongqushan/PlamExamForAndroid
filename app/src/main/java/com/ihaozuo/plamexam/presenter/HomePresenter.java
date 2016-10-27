package com.ihaozuo.plamexam.presenter;

import android.support.annotation.NonNull;

import com.ihaozuo.plamexam.bean.BannerBean;
import com.ihaozuo.plamexam.bean.NewsBean;
import com.ihaozuo.plamexam.bean.RestResult;
import com.ihaozuo.plamexam.bean.UnreadMarkBean;
import com.ihaozuo.plamexam.bean.UserBean;
import com.ihaozuo.plamexam.contract.HomeContract;
import com.ihaozuo.plamexam.database.newsdbutils.NewsDBManager;
import com.ihaozuo.plamexam.database.newsdbutils.NewsDBPojo;
import com.ihaozuo.plamexam.listener.OnHandlerResultListener;
import com.ihaozuo.plamexam.manager.UserManager;
import com.ihaozuo.plamexam.model.ConsultModel;
import com.ihaozuo.plamexam.model.HomeModel;
import com.ihaozuo.plamexam.model.IBaseModel;
import com.ihaozuo.plamexam.view.base.IBaseView;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by zhangzhongyao on 2016/10/10.
 */
public class HomePresenter extends AbstractPresenter implements HomeContract.IHomePresenter {
    HomeContract.IHomeView mHomeView;
    private HomeModel mHomeModel;
    private ConsultModel mConsultModel;
    private UserBean mUserbean;

    @Inject
    public HomePresenter(@NonNull HomeContract.IHomeView view, @NonNull HomeModel homeModel, @NonNull ConsultModel consultModel) {
        mHomeView = view;
        mHomeModel = homeModel;
        mConsultModel = consultModel;
        mUserbean = UserManager.getInstance().getUserInfo();
        mHomeView.setPresenter(this);
    }

    @Override
    public IBaseView getBaseView() {
        return mHomeView;
    }

    @Override
    public IBaseModel[] getBaseModelList() {
        return new IBaseModel[]{mHomeModel};
    }


    @Override
    public void start() {
        getNewsList(1, 4);
        getUnreadMartState(mUserbean.AccountId);
        mHomeModel.getBaner(UserManager.getInstance().getUserInfo().DepartCode, new OnHandlerResultListener<RestResult<List<BannerBean>>>() {
            @Override
            public void handlerResultSuccess(RestResult<List<BannerBean>> resultData) {
                mHomeView.stopRefreshing();
                mHomeView.initBanner(resultData.Data);
            }

            @Override
            public void handlerResultError(String message) {
                mHomeView.stopRefreshing();
                mHomeView.hideDialog(message);
            }
        });
    }


    @Override
    public void getBanner(String departId) {
        mHomeView.showDialog();
        mHomeModel.getBaner(departId, new OnHandlerResultListener<RestResult<List<BannerBean>>>() {
            @Override
            public void handlerResultSuccess(RestResult<List<BannerBean>> resultData) {
                mHomeView.initBanner(resultData.Data);
                mHomeView.hideDialog();
            }

            @Override
            public void handlerResultError(String message) {
                mHomeView.stopRefreshing();
                mHomeView.hideDialog(message);
            }

        });
    }

    @Override
    public void getUnreadMartState(String accountId) {
        mConsultModel.getUnreadMarkState(accountId, new OnHandlerResultListener<RestResult<List<UnreadMarkBean>>>() {
            @Override
            public void handlerResultSuccess(RestResult<List<UnreadMarkBean>> resultData) {
                if (resultData.Data != null) {
                    for (UnreadMarkBean unreadMarkBean : resultData.Data) {
                        if (unreadMarkBean != null && unreadMarkBean.Type == 1) {
                            mHomeView.showUnreadMark();
                            return;
                        }
                    }
                }
            }

            @Override
            public void handlerResultError(String message) {
            }
        });
    }

    @Override
    public void getNewsList(int pageIndex, int pageSize) {
        mHomeModel.getNewsList(pageIndex, pageSize, new OnHandlerResultListener<RestResult<List<NewsBean>>>() {
            @Override
            public void handlerResultSuccess(final RestResult<List<NewsBean>> resultData) {
                if (resultData.Data != null) {
                    List<NewsDBPojo> list = NewsDBManager.repalceNews(resultData.Data);
                    mHomeView.refreshNewsList(list);
                }
            }

            @Override
            public void handlerResultError(String message) {
            }
        });
    }

}
