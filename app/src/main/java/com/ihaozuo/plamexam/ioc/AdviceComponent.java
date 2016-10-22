package com.ihaozuo.plamexam.ioc;

import com.ihaozuo.plamexam.view.mine.settings.AdviceActivity;

import dagger.Component;

/**
 * Created by hzguest3 on 2016/10/20.
 */

@ScopeType.ActivityScope
@Component(modules = {AdviceModule.class}, dependencies = {AppComponent.class})
public interface AdviceComponent {
   void inject(AdviceActivity adviceActivity);
}