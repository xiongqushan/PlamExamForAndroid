package com.ihaozuo.plamexam.ioc;

import com.ihaozuo.plamexam.contract.AdviceContract;
import com.ihaozuo.plamexam.view.mine.settings.AdviceFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by hzguest3 on 2016/10/20.
 */
@Module
public class AdviceModule {
    public AdviceModule(){
    }

    @ScopeType.ActivityScope
    @Provides
    AdviceContract.IAdviceView provideIAdviceView(){
        return AdviceFragment.newInstance();
    }
}