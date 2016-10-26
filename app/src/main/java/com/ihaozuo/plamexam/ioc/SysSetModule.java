package com.ihaozuo.plamexam.ioc;

import com.ihaozuo.plamexam.contract.SysSetContract;
import com.ihaozuo.plamexam.view.mine.settings.SysSetFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by zhangzhongyao on 2016/10/25.
 */
@Module
public class SysSetModule {


    public SysSetModule() {
    }


    @Provides
    @ScopeType.ActivityScope
    SysSetContract.ISysSetView provideSysSetView() {
        return SysSetFragment.newInstance();
    }


}
