package com.ihaozuo.plamexam.ioc;

import com.ihaozuo.plamexam.bean.ConsultDetailBean;
import com.ihaozuo.plamexam.contract.ConsultContract;
import com.ihaozuo.plamexam.view.consult.ConsultDetailFragment;

import java.util.List;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by hzguest3 on 2016/10/13.
 */
@Module
public class ConsultModule {

    private List<ConsultDetailBean> mConsultDetailList;

    public ConsultModule(List<ConsultDetailBean> consultDetailList){
        mConsultDetailList = consultDetailList;
    }

    @ScopeType.ActivityScope
    @Provides
    ConsultContract.IConsultView provideConsultFragment(){
        return ConsultDetailFragment.newInstance();
    }

    @ScopeType.ActivityScope
    @Provides
    @Named("CONSULT_DETAIL_LIST")
    List<ConsultDetailBean> provideConsultDetailList(){
        return mConsultDetailList;
    }
}
