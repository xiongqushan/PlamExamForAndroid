package com.ihaozuo.plamexam.ioc;

import com.ihaozuo.plamexam.view.report.ReportActivity;

import dagger.Component;

/**
 * Created by zhangzhongyao on 2016/10/19.
 */

@ScopeType.ActivityScope
@Component(modules = {ReportModule.class}, dependencies = {AppComponent.class})
public interface ReportDetailComponent {
    void inject(ReportActivity activity);
}
