package com.ihaozuo.plamexam.ioc;

import com.ihaozuo.plamexam.contract.ConsultGradeContract;
import com.ihaozuo.plamexam.view.consult.ConsultGradeFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by hzguest3 on 2016/10/18.
 */
@Module
public class ConsultGradeModule {

    public ConsultGradeModule(){}

    @ScopeType.ConsultScope
    @Provides
    ConsultGradeContract.IConsultGradeView provideConsultGradeFragment(){
        return ConsultGradeFragment.newInstance();
    }

}


