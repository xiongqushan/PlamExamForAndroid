package com.ihaozuo.plamexam.presenter;

import android.support.annotation.NonNull;

import com.ihaozuo.plamexam.bean.NewsBean;
import com.ihaozuo.plamexam.bean.RestResult;
import com.ihaozuo.plamexam.contract.NewsContract;
import com.ihaozuo.plamexam.listener.OnHandlerResultListener;
import com.ihaozuo.plamexam.model.HomeModel;
import com.ihaozuo.plamexam.model.IBaseModel;
import com.ihaozuo.plamexam.view.base.IBaseView;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by hzguest3 on 2016/10/25.
 */
public class NewsListPresenter extends AbstractPresenter implements NewsContract.INewsListPresenter{

    private NewsContract.INewsListView mNewsListView;
    private HomeModel mHomeModel;
    private int mPageIndex;
    private int mPageSize;


    @Inject
    public NewsListPresenter(@NonNull NewsContract.INewsListView newsListView, @NonNull HomeModel homeModel) {
        mNewsListView = newsListView;
        mHomeModel = homeModel;
        mNewsListView.setPresenter(this);
        mPageIndex = 1;
        mPageSize = 10;
    }

    @Override
    public IBaseView getBaseView() {
        return null;
    }

    @Override
    public IBaseModel[] getBaseModelList() {
        return new IBaseModel[]{mHomeModel};
    }

    @Override
    public void start() {
        mPageIndex = 1;
        mPageSize = 10;
        getNewsList();
    }

    @Override
    public void getNewsList() {
        mHomeModel.getNewsList(mPageIndex, mPageSize, new OnHandlerResultListener<RestResult<List<NewsBean>>>() {
            @Override
            public void handlerResultSuccess(RestResult<List<NewsBean>> resultData) {
                if (resultData.Data!=null){
                    mNewsListView.refreshNewsList(resultData.Data);
                    mPageIndex ++;
                }
            }

            @Override
            public void handlerResultError(String message) {
                mNewsListView.hideDialog(message);
            }
        });
    }
}
