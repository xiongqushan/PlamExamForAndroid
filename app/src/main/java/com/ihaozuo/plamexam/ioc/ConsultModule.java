package com.ihaozuo.plamexam.ioc;

import com.ihaozuo.plamexam.contract.ConsultContract;
import com.ihaozuo.plamexam.view.consult.ConsultDetailFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by hzguest3 on 2016/10/13.
 */
@Module
public class ConsultModule {


    public ConsultModule(){
    }

    @ScopeType.ActivityScope
    @Provides
    ConsultContract.IConsultView provideConsultFragment(){
        return ConsultDetailFragment.newInstance();
    }

}
