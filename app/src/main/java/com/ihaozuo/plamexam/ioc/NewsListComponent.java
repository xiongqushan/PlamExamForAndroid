package com.ihaozuo.plamexam.ioc;

import com.ihaozuo.plamexam.view.news.NewsListActivity;

import dagger.Component;

/**
 * Created by hzguest3 on 2016/10/25.
*/
@ScopeType.ActivityScope
@Component(modules = {NewsListModule.class}, dependencies = {AppComponent.class})
public interface NewsListComponent {
    void inject(NewsListActivity newsListActivity);
}
