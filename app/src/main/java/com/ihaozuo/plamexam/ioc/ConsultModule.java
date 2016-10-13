package com.ihaozuo.plamexam.ioc;

import com.ihaozuo.plamexam.view.consult.ConsultFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by hzguest3 on 2016/10/13.
 */
@Module
public class ConsultModule {
    public ConsultModule(){
    }

    @Provides
    ConsultFragment provideConsultFragment(){
        return ConsultFragment.newInstance();
    }
}
