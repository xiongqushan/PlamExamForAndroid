package com.ihaozuo.plamexam.view.news;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import com.ihaozuo.plamexam.R;
import com.ihaozuo.plamexam.bean.NewsBean;
import com.ihaozuo.plamexam.contract.NewsContract;
import com.ihaozuo.plamexam.presenter.IBasePresenter;
import com.ihaozuo.plamexam.view.base.AbstractView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NewsListFragment extends AbstractView implements NewsContract.INewsListView {

    @Bind(R.id.NewsList)
    ListView mListView;
    @Bind(R.id.swipe_Layout)
    SwipeRefreshLayout swipeLayout;

    private View rootView;
    private NewsContract.INewsListPresenter mPresenter;
    private Context mContext;
    private NewsListAdapter adapter;
    private boolean canLoadMore;
    private TextView footView;

    public NewsListFragment() {
        // Required empty public constructor
    }

    public static NewsListFragment newInstance() {
        return new NewsListFragment();
    }

    @Override
    protected IBasePresenter getPresenter() {
        return mPresenter;
    }

    @Override
    protected View getRootView() {
        return rootView;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            mContext = getContext();
            rootView = inflater.inflate(R.layout.news_list_frag, container, false);
            setCustomerTitle(rootView, getString(R.string.daily_news));
            ButterKnife.bind(this, rootView);
            initView();
            mPresenter.start();
        }
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    private void initView() {
        canLoadMore = true;
        adapter = new NewsListAdapter(mContext);
        mListView.setAdapter(adapter);
        footView = (TextView) LayoutInflater.from(mContext).inflate(R.layout.newslist_foot_layout,null);
        mListView.addFooterView(footView);
        swipeLayout.setProgressBackgroundColor(R.color.main_color_blue);
//        swipeLayout.setColorSchemeResources(R.color.white);
        swipeLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.start();
            }
        });
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState){
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    if (view.getLastVisiblePosition() == view.getCount() - 1) {
                        if (canLoadMore){
                            canLoadMore = false;
                            footView.setText("正在加载...");
                            mPresenter.getNewsList(false);
                        }
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }

        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void setPresenter(NewsContract.INewsListPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void refreshNewsList(List<NewsBean> newsList) {
        if (footView.getVisibility() == View.GONE){
            footView.setVisibility(View.VISIBLE);
        }
        if (swipeLayout.isRefreshing()){
            swipeLayout.setRefreshing(false);
        }
        adapter.refreshList(newsList);
    }

    @Override
    public void loadMoreList(List<NewsBean> newsList) {

        canLoadMore = true;
        footView.setText("加载更多...");
        if (newsList.size()<10){
            canLoadMore = false;
            footView.setText("已无更多");
        }

        adapter.loadMoreList(newsList);
    }




}
