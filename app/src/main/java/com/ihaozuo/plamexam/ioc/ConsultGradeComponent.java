package com.ihaozuo.plamexam.ioc;

import com.ihaozuo.plamexam.view.consult.ConsultGradeActivity;

import dagger.Component;

/**
 * Created by hzguest3 on 2016/10/18.
 */
@ScopeType.ConsultScope
@Component(modules = {ConsultGradeModule.class}, dependencies = {AppComponent.class})
public interface ConsultGradeComponent {

    void inject(ConsultGradeActivity consultGradeActivity);

}