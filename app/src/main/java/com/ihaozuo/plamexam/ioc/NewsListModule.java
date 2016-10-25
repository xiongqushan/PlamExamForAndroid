package com.ihaozuo.plamexam.ioc;

import com.ihaozuo.plamexam.contract.NewsContract;
import com.ihaozuo.plamexam.view.news.NewsListFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by hzguest3 on 2016/10/25.
 */

@Module
public class NewsListModule {

    public NewsListModule(){
    }

    @Provides
    @ScopeType.ActivityScope
    NewsContract.INewsListView provideNewsListView(){
        return NewsListFragment.newInstance();
    }


}
