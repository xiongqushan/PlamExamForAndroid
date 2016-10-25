package com.ihaozuo.plamexam.view.news;

import android.os.Bundle;

import com.ihaozuo.plamexam.R;
import com.ihaozuo.plamexam.contract.NewsContract;
import com.ihaozuo.plamexam.framework.HZApp;
import com.ihaozuo.plamexam.ioc.DaggerNewsListComponent;
import com.ihaozuo.plamexam.ioc.NewsListModule;
import com.ihaozuo.plamexam.presenter.NewsListPresenter;
import com.ihaozuo.plamexam.util.ActivityUtils;
import com.ihaozuo.plamexam.view.base.BaseActivity;

import javax.inject.Inject;

public class NewsListActivity extends BaseActivity {

    @Inject
    NewsContract.INewsListView mNewsListView;
    @Inject
    NewsListPresenter mLoginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_act);

        DaggerNewsListComponent.builder()
                .appComponent(HZApp.shareApplication().getAppComponent())
                .newsListModule(new NewsListModule())
                .build()
                .inject(this);

        NewsListFragment fragment = (NewsListFragment) getSupportFragmentManager().findFragmentById(R.id.frameContent);
        if (fragment == null) {
            fragment = (NewsListFragment) mNewsListView;
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.frameContent);
        }
    }
}
