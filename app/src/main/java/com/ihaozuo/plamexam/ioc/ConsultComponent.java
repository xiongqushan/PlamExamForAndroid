package com.ihaozuo.plamexam.ioc;

import com.ihaozuo.plamexam.view.consult.ConsultDetailActivity;

import dagger.Component;

/**
 * Created by hzguest3 on 2016/10/13.
 */
@ScopeType.ActivityScope
@Component(modules = {ConsultModule.class}, dependencies = {AppComponent.class})
public interface ConsultComponent {

    void inject(ConsultDetailActivity consultDetailActivity);

}
